package com.gmf.user_management.masterData.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmf.user_management.config.MultipleDataSourceConfiguration.DataSourceService;
import com.gmf.user_management.config.MultipleDataSourceConfiguration.repository.ExternalRepository;
import com.gmf.user_management.core.storage.StorageService;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodePredicate;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeRequestDto;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.licenseType.repository.LicenseTypeRespository;
import com.gmf.user_management.masterData.personal.dto.*;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.personal.repository.PersonalRepository;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalServiceImpl implements PersonalService{
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StorageService storageService;
    @Autowired
    private LicenseTypeRespository licenseTypeRespository;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private ExternalRepository externalRepository;
    private final Date date = new Date();
    private final Long time = date.getTime();

    private PersonalResponDTO personalResponse(PersonalEntity personalEntity)throws JsonProcessingException {
        List<ApplicationFileDTO> img = objectMapper.readValue(personalEntity.getPersonalPicture(), new TypeReference<>(){});
        Map<String, Object> partnerExternal = null;
        if (personalEntity.getPartnerExternal() != null){
            try {
                partnerExternal = dataSourceService.getContractById(personalEntity.getPartnerExternal());
            }catch (ResponseStatusException e){
                partnerExternal = Map.of("error", Objects.requireNonNull(e.getReason()));
            }
        }
        return PersonalResponDTO.builder()
                .idPersonal(personalEntity.getIdPersonal())
                .partnerExternal(partnerExternal)
                .licenseTypeList(personalEntity.getLicenseTypeList())
                .personalName(personalEntity.getPersonalName())
                .personalNumber(personalEntity.getPersonalNumber())
                .personalPicture(img)
                .dateOfBirth(personalEntity.getDateOfBirth())
                .contactNumber(personalEntity.getContactNumber())
                .email(personalEntity.getEmail())
                .identityNumber(personalEntity.getIdentityNumber())
                .identityType(personalEntity.getIdentityType())
                .dinas(personalEntity.getDinas())
                .unit(personalEntity.getUnit())
                .uid(personalEntity.getUid())
                .isPic(personalEntity.getIsPic())
                .passCardNumber(personalEntity.getPassCardNumber())
                .activeStatus(personalEntity.getActiveStatus())
                .expiredDate(personalEntity.getExpiredDate())
                .createdAt(personalEntity.getCreatedAt())
                .createdBy(personalEntity.getCreatedBy())
                .updatedAt(personalEntity.getUpdatedAt())
                .updatedBy(personalEntity.getUpdatedBy())
                .build();
    }
    @Override
    public PersonalResponDTO createPersonal(PersonalDTO request){
        try {
            List<ApplicationFileDTO> personalPicture = uploadImage(request.getPersonalPicture());
            Map<String, Object>exPartner = externalRepository.findContractById(request.getPartnerExternal());
            if (exPartner.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner External not found");
            PersonalEntity personal = new PersonalEntity();
            PersonalEntity payload = personalPayload(request, personal, personalPicture);
            payload.setPartnerExternal((Long) exPartner.get("id"));
            payload.setPartnerName((String) exPartner.get("name"));
            personalRepository.save(payload);
            return personalResponse(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws  IOException, NoSuchAlgorithmException, InvalidKeyException {
        PersonalEntity personal = personalRepository.findById(idPersonal).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Id " + idPersonal + " not found"));

        List<ApplicationFileDTO> img = objectMapper.readValue(personal.getPersonalPicture(), new TypeReference<ArrayList<ApplicationFileDTO>>() {});
        List<String> imgPathList = img.stream().map(ApplicationFileDTO::getPath).toList();
        List<String> imgFileName = img.stream().map(ApplicationFileDTO::getFilename).toList();

        boolean isNewImgNameAndOldImgNameEqual = request.getPersonalPicture()!= null
                && Objects.equals(request.getPersonalPicture().stream().map(MultipartFile::getOriginalFilename).toList(), imgFileName);

        if (!imgPathList.isEmpty()&& !isNewImgNameAndOldImgNameEqual){
            storageService.deleteAllFileS3(imgPathList);
        }

        List<ApplicationFileDTO> imagePaths = isNewImgNameAndOldImgNameEqual ? img : new ArrayList<>();
        if (!isNewImgNameAndOldImgNameEqual){
            imagePaths = uploadImage(request.getPersonalPicture());
        }

        PersonalEntity payload = personalPayload(request, personal, imagePaths);
        personalRepository.saveAndFlush(payload);
        return personalResponse(payload);
    }

//    @Transactional(readOnly = true)
//    public PaginationUtil<PersonalEntity, PersonalEntity> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO)
//    {
//        Pageable paging = PageRequest.of(page - 1, size);
//        Specification<PersonalEntity> specs = Specification
//                .where(PersonalPredicate.filterByName(requestDTO.getFilterByName()))
//                .and(PersonalPredicate.filterByStatus(requestDTO.getFilterByStatus()))
//                .and(PersonalPredicate.searchByName(requestDTO.getSearchByName()));
//
//        Page<PersonalEntity> pages = personalRepository.findAll(specs, paging);
//        return new PaginationUtil<>(pages, PersonalEntity.class);
//    }
    @Override
    public Page<PersonalResponDTO> getAllPersonal(Pageable pageable, PersonalRequestDTO requestDTO) {
        try {
            Specification<PersonalEntity> specification = Specification
                    .where(PersonalPredicate.filterByName(requestDTO.getFilterByName()))
                    .and(PersonalPredicate.filterByStatus(requestDTO.getFilterByStatus()))
                    .and(PersonalPredicate.searchByName(requestDTO.getSearchByName()));
            Page<PersonalEntity> personals = personalRepository.findAll(specification, pageable);

            return personals.map(personalEntity -> {
                try {
                    return personalResponse(personalEntity);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing personal response", e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving all Personals", e);
        }
    }
    @Override
    public PersonalResponDTO getPersonalById(Long id_personal) throws JsonProcessingException {
        PersonalEntity personal = personalRepository.findById(id_personal).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return personalResponse(personal);
    }

    @Override
    public PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws JsonProcessingException {
        PersonalEntity personal = (PersonalEntity) personalRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal with number " + personalNumber + " not found"));
        return personalResponse(personal);
    }

    @Override
    public PaginationUtil<PersonalEntity, PersonalEntity> getPersonalByPartnerId(Long partnerExternal, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<PersonalEntity> personalEntitiesPage = personalRepository.findAllByPartnerId(partnerExternal, paging);

        if (personalEntitiesPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No personal records found for partnerId: " + partnerExternal);
        }
        personalEntitiesPage.stream()
                .map(personalEntity -> {
                    try {
                        return personalResponse(personalEntity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error processing personal data", e);
                    }
                })
                .toList();

        return new PaginationUtil<>(personalEntitiesPage, PersonalEntity.class);
    }


    @Transactional(readOnly = true)
    public PaginationUtil<PersonalEntity, PersonalEntity> getPersonalByDinas(String dinas, Integer page, Integer size){
        Pageable paging = PageRequest.of(page - 1, size);
        if (dinas == null || dinas.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dinas not found");
        Page<PersonalEntity> personalEntities = personalRepository.findAllByDinas(dinas, paging);
        personalEntities.stream()
                .map(personalEntity -> {
                    try {
                        return personalResponse(personalEntity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error processing JSON for personal entity with ID: " + personalEntity.getIdPersonal(), e);
                    }
                })
                .toList();
        return new PaginationUtil<>(personalEntities, PersonalEntity.class);

    }

    //Get All Personal Partner if isPic(default = true)
    @Override
    public PaginationUtil<PersonalEntity, PersonalEntity> getPersonalAsPartnerPIC(Long partnerExternal, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<PersonalEntity> personalEntities = personalRepository.findAllPersonalAsPartnerPIC(partnerExternal, paging);
        if (personalEntities.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"partnerExternal: " + partnerExternal + " is not PIC for partner or not record by personal");
        }
        personalEntities.stream()
                .map(personalEntity -> {
                    try {
                        return personalResponse(personalEntity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error processing personal data for PIC", e);
                    }
                })
                .toList();
        return new PaginationUtil<>(personalEntities, PersonalEntity.class);
    }

    public String countUIDByDinas() {
        List<Map<String, Object>> results = personalRepository.countUIDByDinas();
        try {
            return objectMapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting countUIDByDinas result to JSON", e);
        }
    }

    //payload
    private PersonalEntity personalPayload(PersonalDTO personalDTO, PersonalEntity personalEntity, List<ApplicationFileDTO> personalPicture) throws JsonProcessingException {

        List<LicenseTypeEntity> allLicenseType = licenseTypeRespository.findByIdLicenseTypeIsIn(personalDTO.getLicenseTypeList());
        if (allLicenseType.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data License Type not found");
        personalEntity.setLicenseTypeList(allLicenseType);
        personalEntity.setPersonalName(personalDTO.getPersonalName());
        personalEntity.setPersonalNumber(personalDTO.getPersonalNumber());
        personalEntity.setPersonalPicture(objectMapper.writeValueAsString(personalPicture));
        personalEntity.setDateOfBirth(personalDTO.getDateOfBirth());
        personalEntity.setContactNumber(personalDTO.getContactNumber());
        personalEntity.setEmail(personalDTO.getEmail());
        personalEntity.setIdentityNumber(personalDTO.getIdentityNumber());
        personalEntity.setIdentityType(personalDTO.getIdentityType());
        personalEntity.setDinas(personalDTO.getDinas());
        personalEntity.setUnit(personalDTO.getUnit());
        personalEntity.setUid(personalDTO.getUid());
        personalEntity.setIsPic(personalDTO.getIsPic());
        personalEntity.setPassCardNumber(personalDTO.getPassCardNumber());
        personalEntity.setActiveStatus(personalDTO.getActiveStatus());
        personalEntity.setExpiredDate(personalDTO.getExpiredDate());
        personalEntity.setCreatedBy(personalDTO.getCreatedBy());
        personalEntity.setUpdatedBy(personalDTO.getUpdatedBy());
        if (personalDTO.getPartnerExternal() != null) {
            personalEntity.setPartnerExternal(personalDTO.getPartnerExternal());
        }
        return personalEntity;
    }

    //Upload image
    private boolean isValidImageType(String contentType) {
        return "image/png".equals(contentType) || "image/jpeg".equals(contentType) || "image/jpg".equals(contentType);
    }

    private List<ApplicationFileDTO> uploadImage(List<MultipartFile> thumbnail){
        if (thumbnail == null || thumbnail.isEmpty()) return Collections.emptyList();
        List<ApplicationFileDTO> thumbnailPaths = new ArrayList<>();
        String generatedString = genereateRandomString();
        thumbnail.forEach(img ->{
            String contentType = img.getContentType();
            if (!isValidImageType(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image format. Only PNG, JPG, and JPEG are allowed.");
            }
            try {
                String imgFileName = time + generatedString + "_" + Objects.requireNonNull(img.getOriginalFilename()).replace(" ", "_");
                String filePath = LocalDate.now().getYear() + "/img/" + imgFileName;
                ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, img);

                ApplicationFileDTO applicationFileDto = ApplicationFileDTO.builder()
                        .path(objectWriteResponse.object())
                        .filename(img.getOriginalFilename())
                        .size(String.valueOf(img.getSize()))
                        .mimeType(img.getContentType())
                        .build();
                thumbnailPaths.add(applicationFileDto);

            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        });
        return thumbnailPaths;
    }

    private String genereateRandomString(){
        Random random = new Random();
        return random.ints(97, 122+1)
                .limit(11)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

