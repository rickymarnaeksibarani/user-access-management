package com.gmf.user_management.modules.jobCode.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_job_code")
public class JobCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job_code")
    private Long idJobCode;

    @Column(name = "job_code", length = 10)
    private String jobCode;

    @Column(name = "job_position")
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String jobPosition;

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
