package com.gmf.user_management.masterData.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.storage.StorageService;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.licenseType.repository.LicenseTypeRespository;
import com.gmf.user_management.masterData.partner.PartnerRepository;
import com.gmf.user_management.masterData.partner.entities.PartnerEntity;
import com.gmf.user_management.masterData.personal.dto.ApplicationFileDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.personal.repository.PersonalRepository;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Service
public class PersonalServiceImpl implements PersonalService{
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private LicenseTypeRespository licenseTypeRespository;
    private final Date date = new Date();
    private final Long time = date.getTime();


    //Respone

    private PersonalResponDTO personalRespon(PersonalEntity personalEntity)throws JsonProcessingException {
        List<ApplicationFileDTO> img = objectMapper.readValue(personalEntity.getPersonalPicture(), new TypeReference<>(){});
        return PersonalResponDTO.builder()
                .idPersonal(personalEntity.getIdPersonal())
                .partnerList(personalEntity.getPartnerList())
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

            List<ApplicationFileDTO> personalPicture = uploadImage(request.getPersonalPicture());
            PersonalEntity personal = new PersonalEntity();
            PersonalEntity payload = personalPayload(request, personal, personalPicture);
            personalRepository.save(payload);
            return personalRespon(payload);


    }

    @Override
    public PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException {
        return null;
    }

    @Override
    public PaginationUtil<PersonalEntity, PersonalResponDTO> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO) {
        return null;
    }

    @Override
    public PersonalResponDTO getPersonalById(Long idPersonal) throws NotFoundException {
        return null;
    }

    @Override
    public PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws NotFoundException {
        return null;
    }

    @Override
    public PersonalResponDTO getPersonalByPartnerId(String partnerId) throws NotFoundException {
        return null;
    }

    @Override
    public PersonalResponDTO getPersonalByDinas(String dinas) throws NotFoundException {
        return null;
    }

    @Override
    public PersonalResponDTO getPersonalAsPartnerPIC(Integer parntnerId) throws NotFoundException {
        return null;
    }
    @Override
    public Long countUIDByDinas(String dinas) {
        return null;
    }


    //payload
    private PersonalEntity personalPayload(PersonalDTO personalDTO, PersonalEntity personalEntity, List<ApplicationFileDTO> personalPicture) throws JsonProcessingException {
        List<PartnerEntity> allPartner = partnerRepository.findByIdPartnerIsIn(personalDTO.getPartnerList());
//        if (allPartner.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Partner not found");

        List<LicenseTypeEntity> allLicenseType = licenseTypeRespository.findByIdLicenseTypeIsIn(personalDTO.getLicenseTypeList());
        if (allLicenseType.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data License Type not found");

        personalEntity.setPartnerList(allPartner);
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

