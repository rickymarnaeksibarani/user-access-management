package com.gmf.user_management.masterData.usersAccessDomain.entities;

import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import jdk.dynalink.linker.LinkerServices;
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
@Table(name = "tb_user_access_domain")
public class UserAccessDomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_access_domain")
    private Long idUserAccessDomain;

    @ManyToMany @JoinColumn(name = "personal_id")
    private List<PersonalEntity> personalList;

    @Column(name = "is_network_access")
    private Boolean isNetworkAccess = false;

    @Column(name = "is_domain_access")
    private Boolean isDomainAccess = false;

    @Column(name ="username")
    private String username;

    @Column(name = "password")
    private String password;

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
