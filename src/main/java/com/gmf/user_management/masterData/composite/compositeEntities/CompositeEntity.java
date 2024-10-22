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
    private Long id_composite_role;

    @ManyToOne @JoinColumn(name = "job_code_id")
    private JobCodeEntity jobCodeEntityList;

    @Column(name = "composite_role")
    private String composite_role;

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
