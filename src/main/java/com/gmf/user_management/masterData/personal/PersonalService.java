package com.gmf.user_management.masterData.personal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmf.user_management.core.storage.StorageService;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.licenseType.repository.LicenseTypeRespository;
import com.gmf.user_management.masterData.partner.PartnerRepository;
import com.gmf.user_management.masterData.partner.entities.PartnerEntity;
import com.gmf.user_management.masterData.personal.dto.ApplicationFileDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.personal.repository.PersonalRepository;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PersonalService {
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
    private PersonalResponDTO personalRespon(PersonalEntity personalEntity)throws JsonProcessingException{
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

    public PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException {
        List<ApplicationFileDTO> personalPicture = uploadImage(request.getPersonalPicture());
        PersonalEntity personal = new PersonalEntity();
        PersonalEntity payload = personalPayload(request, personal, personalPicture);
        personalRepository.save(payload);
        return personalRespon(payload);

    }

    public PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
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

    public Boolean deletePersonal(Long idPersonal) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        PersonalEntity data = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + idPersonal +" not found"));

        // Remove old image
        List<ApplicationFileDTO> imgs = objectMapper.readValue(data.getPersonalPicture(), new TypeReference<ArrayList<ApplicationFileDTO>>() {});
        List<String> imgList = imgs.stream().map(ApplicationFileDTO::getFilename).toList();
        if (!imgList.isEmpty()) {
            storageService.deleteAllFileS3(imgList);
        }
        personalRepository.deleteById(idPersonal);
        return true;
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
