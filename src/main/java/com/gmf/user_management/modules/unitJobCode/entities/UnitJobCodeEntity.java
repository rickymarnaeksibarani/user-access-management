package com.gmf.user_management.modules.unitJobCode.entities;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
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
@Table(name = "tb_unit_job_code")
public class UnitJobCodeEntity {
    @Id
    @SequenceGenerator(
            name = "unit_job_code_seq",
            sequenceName = "unit_job_code_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_job_code_seq")
    @Column(name = "id_unit_job_code")
    private Long idUnitJobCode;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "unit_job_code_unit_list",
            joinColumns = @JoinColumn(name = "unit_job_code_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private List<UnitEntity> unitList;

    @ManyToMany
    @JoinTable(
            name = "unit_job_code_job_code_list",
            joinColumns = @JoinColumn(name = "unit_job_code_id"),
            inverseJoinColumns = @JoinColumn(name = "job_code_id")
    )
    private List<JobCodeEntity> jobCodeList;


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
