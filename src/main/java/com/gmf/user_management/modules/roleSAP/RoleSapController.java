//package com.gmf.user_management.modules.roleSAP;
//
//import com.gmf.user_management.core.dto.HttpResponseDTO;
//import com.gmf.user_management.modules.roleSAP.dto.RoleSapDto;
//import com.gmf.user_management.modules.roleSAP.dto.RoleSapResponDto;
//import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainDTO;
//import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainResponDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/v1/roleSap")
//@Validated
//@RequiredArgsConstructor
//@Slf4j
//public class RoleSapController {
//    private final RoleSapServiceImpl roleSapService;
//
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpResponseDTO<RoleSapResponDto>> createUserAccessDomain(
//            @RequestBody @Valid RoleSapDto request
//    ){
//        log.info("rekk {}", request);
//        RoleSapResponDto respone = roleSapService.createRoleSap(request);
//        return new HttpResponseDTO<>(respone, HttpStatus.CREATED)
//                .setResponseHeaders("respone", respone)
//                .toResponse();
//    }
//}
