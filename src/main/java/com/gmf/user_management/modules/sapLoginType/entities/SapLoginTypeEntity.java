package com.gmf.user_management.modules.sapLoginType.entities;

import com.gmf.user_management.modules.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
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
@Table(name = "tb_sap_login_type")
public class SapLoginTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sap_login_type")
    private Long idSapLoginType;

    @Column(name = "login_type", length = 60)
    private String loginType;

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
