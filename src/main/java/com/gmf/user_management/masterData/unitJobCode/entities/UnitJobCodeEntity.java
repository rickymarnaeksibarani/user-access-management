package com.gmf.user_management.masterData.unitJobCode.entities;

import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name = "tb_unit_job_code_entity")
public class UnitJobCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unit_job_code")
    private Long id_unit_job_code;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unitEntityList;

    @ManyToOne
    @JoinColumn(name = "job_code_id")
    private JobCodeEntity jobCodeEntityList;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "created_by")
    private String created_by;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "updated_by")
    private String updated_by;
}
