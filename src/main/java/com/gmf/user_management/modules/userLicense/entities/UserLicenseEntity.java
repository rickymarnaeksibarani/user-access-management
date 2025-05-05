package com.gmf.user_management.modules.userLicense.entities;

import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.modules.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_user_license")
public class UserLicenseEntity {
    @Id
    @SequenceGenerator(
            name = "user_license_seq",
            sequenceName = "user_license_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_license_seq")
    @Column(name = "id_user_license")
    private Long idUserLicense;

    @ManyToMany
    @JoinTable(
            name = "user_license_application_license",
            joinColumns = @JoinColumn(name = "id_user_license"),
            inverseJoinColumns = @JoinColumn(name = "application_license_id")
    )
    private List<ApplicationLicenseEntity> applicationLicenseList;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_license_personal",
            joinColumns = @JoinColumn(name = "id_user_license"),
            inverseJoinColumns = @JoinColumn(name = "personal_id")
    )
    private List<PersonalEntity> personalList;

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
