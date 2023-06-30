package com.gmf.user_management.domains.user.entities;

import com.gmf.user_management.core.abstractions.TimestampEntityAbstraction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details_information", schema = "public")
public class UserEntity extends TimestampEntityAbstraction implements Serializable {
    @Id
    @Column(name = "id_user_details")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user_seq")
    @SequenceGenerator(name = "id_user_seq", sequenceName = "public.user_details_information_id_user_details_seq", allocationSize = 1)
    private Long idUserDetail;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @Column(name = "identity_number", nullable = false)
    private String identityNumber;

    @Column(name = "identity_type", nullable = false)
    private String identityType;

    @Column(name = "active_status", nullable = false)
    private String activeStatus;

    @Column(name = "user_source_id", nullable = false)
    private Long userSourceId;

    @Column(name = "retired_at", nullable = false)
    private LocalDateTime retiredAt;
}
