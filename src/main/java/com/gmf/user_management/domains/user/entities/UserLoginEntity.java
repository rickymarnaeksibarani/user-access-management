package com.gmf.user_management.domains.user.entities;

import com.gmf.user_management.core.abstractions.TimestampEntityAbstraction;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "active_gmf_user", schema = "public")
public class UserLoginEntity extends TimestampEntityAbstraction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "active_gmf_user_id_active_user_seq")
    @SequenceGenerator(name = "active_gmf_user_id_active_user_seq", sequenceName = "public.active_gmf_user_id_active_user_seq", allocationSize = 1)
    @Column(name = "idActiveUser", nullable = false)
    private Long idActiveUser;

    @Column(name = "username", nullable = false, length = 40, unique = true)
    private String username;

    @Column(name = "alias_personal_number", nullable = false, length = 20, unique = true)
    private String personalNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pass_card_number", nullable = false, length = 40)
    private String passCardNumber;

    @Column(name = "active_status", nullable = false)
    private String activeStatus;

    @Column(name = "work_station", nullable = false, length = 20)
    private String workStation;

    @Column(name = "user_details_information_id", nullable = false)
    private Long userDetailId;

}
