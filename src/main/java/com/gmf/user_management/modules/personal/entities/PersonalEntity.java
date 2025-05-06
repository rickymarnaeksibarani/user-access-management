package com.gmf.user_management.modules.personal.entities;

import com.gmf.user_management.core.enums.IdentityType;
import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.modules.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_personal")
public class PersonalEntity {
    @Id
    @SequenceGenerator(
            name = "personal_seq",
            sequenceName = "personal_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_seq")
    @Column(name = "id_personal")
    private Long idPersonal;

    @Column(name = "partner_external")
    private Long partnerExternal;

    @Column(name = "partner_name")
    private String partnerName;

    @ManyToMany
    @JoinTable(
            name = "personal_license",
            joinColumns = @JoinColumn(name = "id_personal"),
            inverseJoinColumns = @JoinColumn(name = "license_type_id")
    )
    private List<LicenseTypeEntity> licenseTypeList;

    @ManyToMany
    @JoinTable(
            name = "personal_sap_login_type",
            joinColumns = @JoinColumn(name = "id_personal"),
            inverseJoinColumns = @JoinColumn(name = "sap_login_type_id")
    )
    private List<SapLoginTypeEntity> sapLoginTypeList;


    @Column(name = "personal_name", length = 60, nullable = false)
    private String personalName;

    @Column(name = "personal_number", length = 10, unique = true)
    @Pattern(regexp = "^[0-9]+$", message = "Personal number hanya boleh berisi angka")
    private String personalNumber;

    @Column(name = "personal_picture", columnDefinition = "text")
    private String personalPicture;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "contact_number", length = 14)
    @Pattern(regexp = "^[0-9]+$", message = "Contact Number hanya boleh berisi angka")
    private String contactNumber;

    @Column(name = "email", length = 60)
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "identity_number", length = 20)
    @Pattern(regexp = "^[0-9]+$", message = "Identity number hanya boleh berisi angka")
    private String identityNumber;

    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @Column(name = "dinas", length = 50)
    private String dinas;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "uid", length = 10)
    private String uid;

    @Column(name = "is_pic")
    private Boolean isPic;

    @Column(name = "pass_card_number", length = 10)
    @Pattern(regexp = "^[0-9]+$", message = "Pass Card Number hanya boleh berisi angka")
    private String passCardNumber;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private Status activeStatus;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "expired_date")
    private Date expiredDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
