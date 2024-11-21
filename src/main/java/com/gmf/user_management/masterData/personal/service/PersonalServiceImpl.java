package com.gmf.user_management.masterData.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmf.user_management.config.MultipleDataSourceConfiguration.DataSourceService;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.storage.StorageService;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.licenseType.repository.LicenseTypeRespository;
import com.gmf.user_management.masterData.personal.dto.ApplicationFileDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
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
import java.util.function.Predicate;
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
    private final Date date = new Date();
    private final Long time = date.getTime();


    //Respone

    private PersonalResponDTO personalRespon(PersonalEntity personalEntity)throws JsonProcessingException {
        List<ApplicationFileDTO> img = objectMapper.readValue(personalEntity.getPersonalPicture(), new TypeReference<>(){});
        Map<String, Object> contractDetails = null;
        if (personalEntity.getPartnerId() != null) {
            try {
                contractDetails = dataSourceService.getContractById(personalEntity.getPartnerId());
            } catch (ResponseStatusException e) {
                contractDetails = Map.of("error", Objects.requireNonNull(e.getReason()));
            }
        }
        return PersonalResponDTO.builder()
                .idPersonal(personalEntity.getIdPersonal())
                .contractDetails(contractDetails)
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
    public PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException {
        try {
            List<ApplicationFileDTO> personalPicture = uploadImage(request.getPersonalPicture());
            PersonalEntity personal = new PersonalEntity();
            PersonalEntity payload = personalPayload(request, personal, personalPicture);
            personalRepository.save(payload);
            return personalRespon(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException {
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
        return personalRespon(payload);
    }

    @Transactional(readOnly = true)
    public PaginationUtil<PersonalEntity, PersonalEntity> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO) {
        Pageable paging = PageRequest.of(page-1, size);
        Specification<PersonalEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(requestDTO.getSearchTerm())) {
                predicates.add(
                        (Predicate) builder.or(
                                builder.like(builder.upper(root.get("personalName")), "%" + requestDTO.getSearchTerm().toUpperCase() + "%"),
                                builder.like(builder.upper(root.get("personalNumber")), requestDTO.getSearchTerm().toUpperCase())
                        )
                );
            }
            return query.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{})).getRestriction();
        };
        Page<PersonalEntity> mobileApp = personalRepository.findAll(specification, paging);
        return new PaginationUtil<>(mobileApp, PersonalEntity.class);
    }

    public PersonalResponDTO getPersonalById(Long id_personal) throws NotFoundException, JsonProcessingException {
        PersonalEntity personal = personalRepository.findById(id_personal).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return personalRespon(personal);
    }

    @Override
    public PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws NotFoundException, JsonProcessingException {
        PersonalEntity personal = (PersonalEntity) personalRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal with number " + personalNumber + " not found"));
        return personalRespon(personal);
    }

    @Override
    public PersonalResponDTO getPersonalByPartnerId(String partnerId) throws NotFoundException {
        return null;
    }

    @Transactional(readOnly = true)
    public List<PersonalResponDTO> getPersonalByDinas(String dinas) throws JsonProcessingException {
        if (dinas == null || dinas.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dinas not found");
        List<PersonalEntity> personalEntities = personalRepository.findAllByDinas(dinas);
        return personalEntities.stream()
                .map(personalEntity -> {
                    try {
                        return personalRespon(personalEntity);
                    } catch (JsonProcessingException e) {
                        // Log the error or handle as appropriate
                        throw new RuntimeException("Error processing JSON for personal entity with ID: " + personalEntity.getIdPersonal(), e);
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    public PersonalResponDTO getPersonalAsPartnerPIC(Integer parntnerId) throws NotFoundException {
        return null;

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
        if (personalDTO.getPartnerId() != null) {
            personalEntity.setPartnerId(personalDTO.getPartnerId());
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

