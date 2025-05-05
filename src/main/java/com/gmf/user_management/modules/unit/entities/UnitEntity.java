package com.gmf.user_management.modules.unit.entities;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
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
@Table(name = "tb_unit")
public class UnitEntity {
    @Id
    @SequenceGenerator(
            name = "unit_seq",
            sequenceName = "unit_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
    @Column(name = "id_unit")
    private Long idUnit;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "unit_business_unit_code",
            joinColumns = @JoinColumn(name = "id_unit"),
            inverseJoinColumns = @JoinColumn(name = "business_unit_code_id", referencedColumnName = "id_business_unit_code")
    )
    private List<BusinessUnitCodeEntity> businessUnitCodeList;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "unit_job_code_job_code_list",
            joinColumns = @JoinColumn(name = "id_unit"),
            inverseJoinColumns = @JoinColumn(name = "job_code_id")
    )
    private List<JobCodeEntity> jobCodeList;

    @Column(name = "unit", length = 50)
    private String unit;

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
