package com.gmf.user_management.masterData.composite.compositeEntities;

import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
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
@Table(name = "tb_composite_roke")
public class CompositeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_composite_role")
    private Long idCompositeRole;

    @ManyToOne @JoinColumn(name = "job_code_id")
    private JobCodeEntity jobCodeEntityList;

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
