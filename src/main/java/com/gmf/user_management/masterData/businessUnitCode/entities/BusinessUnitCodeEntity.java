package com.gmf.user_management.masterData.businessUnitCode.entities;

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
@Table(name = "tb_business_unit_code")
public class BusinessUnitCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_business_unit_code")
    private Long idBusinessUnitCode;

    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "business_unit_code")
    private String businessUnitCode;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "dinas")
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
