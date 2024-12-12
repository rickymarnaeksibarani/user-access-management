package com.gmf.user_management.masterData.personal.entities;

import com.gmf.user_management.core.enums.IdentityType;
import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personal")
    private Long idPersonal;

    @Column(name = "partner_external")
    private Long partnerExternal;

    @Column(name = "partner_name")
    private String partnerName;

    @ManyToMany @JoinColumn(name = "license_type_id")
    private List<LicenseTypeEntity> licenseTypeList;

    @Column(name = "personal_name")
    private String personalName;

    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "personal_picture", columnDefinition = "text")
    private String personalPicture;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @Column(name = "dinas")
    private String dinas;

    @Column(name = "unit")
    private String unit;

    @Column(name = "uid")
    private String uid;

    @Column(name = "is_pic")
    private Boolean isPic;

    @Column(name = "pass_card_number")
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
