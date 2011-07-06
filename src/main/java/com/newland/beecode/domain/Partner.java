package com.newland.beecode.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Partner {
	
	public static final int PARTNER_NO_LENGTH=16;
	public static final Long ALL_LONG=new Long(99999);

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column
    private String partnerNo;
    @Column
    private String partnerName;
    
    @ManyToOne(fetch=FetchType.LAZY)
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
