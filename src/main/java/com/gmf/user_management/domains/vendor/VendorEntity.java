package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.abstractions.TimestampEntityAbstraction;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_id_seq")
    @Column(name= "id_user_source")
    @SequenceGenerator(name = "vendor_id_seq", sequenceName = "user_source_id_user_source_seq", allocationSize = 1)
    private Long idUserSource;

    @Column(name= "company_name", nullable = false)
    private String companyName;

    @Column(name= "company_address")
    private String companyAddress;

    @Column(name= "pic_name", nullable = false)
    private String picName;

    @Column(name= "pic_contact_number", nullable = false)
    private String picContactNumber;

    @Column(name= "email", nullable = false)
    private String email;

    @Column(name= "active_status", nullable = false)
    private String activeStatus;

    @Column(name= "changed_by")
    private String changedBy;
}
