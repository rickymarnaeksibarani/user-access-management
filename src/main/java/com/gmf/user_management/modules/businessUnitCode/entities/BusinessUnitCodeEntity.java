package com.gmf.user_management.modules.businessUnitCode.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
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
@Table(name = "tb_business_unit_code")
public class BusinessUnitCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_business_unit_code")
    private Long idBusinessUnitCode;

    @Column(name = "partner_external")
    @Nullable
    private Long partnerExternal;

    @Column(name = "partner_name")
    @Nullable
    private String partnerName;

    @Column(name = "business_unit_code", length = 20)
    @Nullable
    private String businessUnitCode;

    @Column(name = "description", columnDefinition = "text")
    @Size(max = 255, message ="Must not exceed 255 characters")
    private String description;

    @Column(name = "dinas", length = 50)
    private String dinas;

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
