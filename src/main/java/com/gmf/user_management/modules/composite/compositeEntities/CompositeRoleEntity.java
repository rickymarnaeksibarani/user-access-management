package com.gmf.user_management.modules.composite.compositeEntities;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.licenseType.entities.LicenseTypeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_composite_role")
public class CompositeRoleEntity {
    @Id
    @SequenceGenerator(
            name = "composite_role_seq",
            sequenceName = "composite_role_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "composite_role_seq")
    @Column(name = "id_composite_role")
    private Long idCompositeRole;

    @ManyToMany
    @JoinTable(
            name = "composite_job_code",
            joinColumns = @JoinColumn(name = "id_composite"),
            inverseJoinColumns = @JoinColumn(name = "job_code_id")
    )
    private List<JobCodeEntity> jobCodeList;

    @Column(name = "composite_role")
    private String compositeRole;

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
