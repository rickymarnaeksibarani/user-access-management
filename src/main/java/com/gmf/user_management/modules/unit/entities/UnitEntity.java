package com.gmf.user_management.modules.unit.entities;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unit")
    private Long idUnit;

    @ManyToMany
    @JoinColumn(name = "business_unit_code_id")
    private List<BusinessUnitCodeEntity> businessUnitCodeList;

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
