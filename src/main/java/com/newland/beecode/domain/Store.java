package com.newland.beecode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author shaoxr:
 * @version 2011-6-24 下午01:13:19
 * 
 */
@Entity
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
    private Long id;
	@Column
	private String name;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="partner_catalog")
    private PartnerCatalog partnerCatalog;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public PartnerCatalog getPartnerCatalog() {
		return partnerCatalog;
	}

	public void setPartnerCatalog(PartnerCatalog partnerCatalog) {
		this.partnerCatalog = partnerCatalog;
	}

}
