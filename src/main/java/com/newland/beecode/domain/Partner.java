package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Transient;


import com.newland.beecode.domain.report.PartnerSummaryItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column
    private String partnerNo;
    @Column
    private String partnerName;
    
    @ManyToOne
    @JoinColumn(name="partner_catalog")
    private PartnerCatalog partnerCatalog;
    
    @Transient
    private String allName;

    public String getAllName() {
        return this.partnerCatalog.getCatalogName() + "-->" + this.getPartnerName();
    }

    public PartnerCatalog getPartnerCatalog() {
        return partnerCatalog;
    }

    public void setPartnerCatalog(PartnerCatalog partnerCatalog) {
        this.partnerCatalog = partnerCatalog;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
