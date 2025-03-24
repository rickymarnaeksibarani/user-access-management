//package com.gmf.user_management.modules.roleSAP;
//
//import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
//import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
//import com.gmf.user_management.modules.personal.entities.PersonalEntity;
//import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@EntityListeners({AuditingEntityListener.class})
//@Table(name = "management_role_sap")
//public class RoleSapEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_role_sap")
//    private Long idRoleSap;
//
//    @ManyToMany @JoinColumn(name = "personal_id")
//    private List<PersonalEntity> roleSapUIDPersonal;
//
//    @ManyToMany @JoinColumn(name = "business_id")
//    private List<BusinessUnitCodeEntity> roleSapbusiness;
//
//    @ManyToMany @JoinColumn(name = "loginType_id")
//    private List<SapLoginTypeEntity> roleSapLoginType;
//
//    @ManyToMany @JoinColumn(name = "licenseType_id")
//    private List<ApplicationLicenseEntity> roleSapLicenseType;
//
//    @CreationTimestamp
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "updated_by")
//    private String updatedBy;
//}
