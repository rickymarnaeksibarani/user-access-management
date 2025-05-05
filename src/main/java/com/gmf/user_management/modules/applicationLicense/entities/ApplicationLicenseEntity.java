package com.gmf.user_management.modules.applicationLicense.entities;

import com.gmf.user_management.core.enums.LicenseCategory;
import com.gmf.user_management.core.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_application_license")
public class ApplicationLicenseEntity {
    @Id
    @SequenceGenerator(
            name = "application_license_seq",
            sequenceName = "application_license_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_license_seq")
    @Column(name = "id_application_license")
    private Long idApplicationLicense;

    @Column(name = "application_name", length = 60)
    private String applicationName;

    @Column(name = "license_type", length = 20)
    private String licenseType;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "license_category")
    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private Status activeStatus;

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
