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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partner")
    private Long id_partner;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "key_number")
    private String key_number;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "expired_date")
    private Date expired_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private Status active_status;

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
