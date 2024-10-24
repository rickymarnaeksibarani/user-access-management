package com.gmf.user_management.config.MultipleDataSourceConfiguration.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "contracts")
public class SecondaryPartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private Byte type;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "intern_office_receipt_date")
    private Date internOfficeReceiptDate;

    @Column(name = "agreement_period")
    private String agreementPeriod;

    @Column(name = "number")
    private String number;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "status")
    private Long status;

    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
