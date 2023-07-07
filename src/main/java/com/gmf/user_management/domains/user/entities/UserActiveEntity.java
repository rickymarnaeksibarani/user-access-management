package com.gmf.user_management.domains.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "view_active_user_details", schema = "public")
public class UserActiveEntity implements Serializable {
    @Id
    @Column(name = "id_user_details")
    private Long idActiveUser;

    @Column(name = "user_source_id")
    private Long sourceId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "alias_personal_number")
    private String aliasPersonalNumber;

    @Column(name = "pass_card_number")
    private String passCardNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "work_station")
    private String workstation;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "active_status")
    private String status;

    @Column(name = "is_pic")
    private Boolean isPic;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
