//package com.gmf.user_management.modules.roleSAP;
//
//import com.gmf.user_management.modules.applicationLicense.repository.ApplicationLicenseRepository;
//import com.gmf.user_management.modules.businessUnitCode.repositories.BusinessUnitCodeRepository;
//import com.gmf.user_management.modules.personal.repository.PersonalRepository;
//import com.gmf.user_management.modules.roleSAP.dto.*;
//import com.gmf.user_management.modules.sapLoginType.repository.SapLoginTypeRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RoleSapServiceImpl implements RoleSapService{
//
//    private final RoleSapRepository roleSapRepository;
//    private final PersonalRepository personalRepository;
//    private final BusinessUnitCodeRepository businessUnitCodeRepository;
//    private final SapLoginTypeRepository sapLoginTypeRepository;
//    private final ApplicationLicenseRepository applicationLicenseRepository;
//
//    private RoleSapResponDto roleSapResponDto(RoleSapEntity roleSapEntity){
//
//        List<PersonalToSapDto> personalDTOList = Optional.ofNullable(roleSapEntity.getRoleSapUIDPersonal())
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(personal -> new PersonalToSapDto(
//                        personal.getIdPersonal(),
//                        personal.getUid()
//                ))
//                .toList();
//
//
//        List<BusinessToSapDto> business = roleSapEntity.getRoleSapbusiness().stream()
//                .map(businessDto -> new BusinessToSapDto(
//                        businessDto.getIdBusinessUnitCode(),
//                        businessDto.getBusinessUnitCode(),
//                        businessDto.getDescription()
//
//                ))
//                .toList();
//
//
//        List<LoginTypeToSapDto> loginTypeToSapDto = roleSapEntity.getRoleSapLoginType().stream()
//                .map(loginDto -> new LoginTypeToSapDto(
//                        loginDto.getIdSapLoginType(),
//                        loginDto.getLoginType()
//
//                ))
//                .toList();
//
//        List<ApplicationToSapDto> applicationToSapDto = roleSapEntity.getRoleSapLicenseType().stream()
//                .map(applicationDto -> new ApplicationToSapDto(
//                        applicationDto.getIdApplicationLicense(),
//                        applicationDto.getLicenseType()
//
//                ))
//                .toList();
//        return RoleSapResponDto.builder()
//                .idRoleSap(roleSapEntity.getIdRoleSap())
//                .roleSapUIDPersonal(personalDTOList)
//                .roleSapbusiness(business)
//                .roleSapLoginType(loginTypeToSapDto)
//                .roleSapLicenseType(applicationToSapDto)
//                .createdAt(roleSapEntity.getCreatedAt())
//                .createdBy(roleSapEntity.getCreatedBy())
//                .updatedAt(roleSapEntity.getUpdatedAt())
//                .updatedBy(roleSapEntity.getUpdatedBy())
//                .build();
//    }
//
//    @Override
//    public RoleSapResponDto createRoleSap(RoleSapDto request) {
//        RoleSapEntity uad = new RoleSapEntity();
//        RoleSapEntity payload = roleSapPayload(request, uad);
//        roleSapRepository.save(payload);
//        return roleSapResponDto(payload);
//    }
//
//    private RoleSapEntity roleSapPayload(RoleSapDto roleSapDto, RoleSapEntity roleSapEntity){
//        Optional.of(roleSapDto.getRoleSapUIDPersonal())
//                .ifPresent(personal -> roleSapEntity.setRoleSapUIDPersonal(personalRepository.findByIdPersonalIsIn(personal)));
//        Optional.of(roleSapDto.getRoleSapbusiness())
//                .ifPresent(business -> roleSapEntity.setRoleSapbusiness(businessUnitCodeRepository.findByIdBusinessUnitCodeIsIn(business)));
//        Optional.of(roleSapDto.getRoleSapLoginType())
//                .ifPresent(loginType -> roleSapEntity.setRoleSapLoginType(sapLoginTypeRepository.findByIdSapLoginTypeIsIn(loginType)));
//        Optional.of(roleSapDto.getRoleSapLicenseType())
//                .ifPresent(licenseType -> roleSapEntity.setRoleSapLicenseType(applicationLicenseRepository.findByIdApplicationLicenseIsIn(licenseType)));
//        return roleSapEntity;
//    }
//}
