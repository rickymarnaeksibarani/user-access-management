package com.gmf.user_management.masterData.unitJobCode.entities;

import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "tb_unit_job_code")
public class UnitJobCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unit_job_code")
    private Long idUnitJobCode;

    //table unit
    @ManyToMany
    @JoinColumn(name = "unit_id")
    private List<UnitEntity> unitList;

    //table jobCode
    @ManyToMany
    @JoinColumn(name = "job_code_id")
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
