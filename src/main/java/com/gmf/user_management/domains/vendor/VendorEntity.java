package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.abstractions.TimestampEntityAbstraction;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Immutable
@Table(name = "user_source", schema = "public")
public class VendorEntity extends TimestampEntityAbstraction implements Serializable {

    @Id
    @Column(name= " id_user_source", nullable = false)
    private Long idUserSource;

    @Column(name= " company_name", nullable = false)
    private String companyName;

    @Column(name= " company_address")
    private String companyAddress;

    @Column(name= " pic_name", nullable = false)
    private String picName;

    @Column(name= " pic_contact_number", nullable = false)
    private String picContactNumber;

    @Column(name= " email", nullable = false)
    private String email;

    @Column(name= " active_status", nullable = false)
    private String activeStatus;

    @Column(name= " changed_by", nullable = false)
    private String changedBy;
}
