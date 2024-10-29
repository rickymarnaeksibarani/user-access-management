package com.gmf.user_management.masterData.partner.entities;

import com.gmf.user_management.core.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_partner")
public class PartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_partner")
    private Long idPartner;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "subject")
    private String subject;

    @Column(name = "key_number")
    private String keyNumber;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private Status activeStatus;

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
