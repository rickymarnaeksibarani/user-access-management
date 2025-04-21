package com.gmf.user_management.modules.usersAccessDomain.entities;

import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_user_access_domain")
public class UserAccessDomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_access_domain")
    private Long idUserAccessDomain;

    @ManyToOne
    @JoinColumn(name = "id_personal",  nullable = false)
    @NotNull
    private PersonalEntity personalId;

    @Column(name = "is_network_access")
    private Boolean isNetworkAccess = false;

    @Column(name = "is_domain_access")
    private Boolean isDomainAccess = false;

    @Column(name ="username", length = 60)
    private String username;

    @Column(name = "password", length = 60)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
