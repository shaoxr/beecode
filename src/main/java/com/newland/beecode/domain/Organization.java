package com.newland.beecode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(unique = true)
    private String organizationDN;
    @Column
    private String organizationCN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationCN() {
        return organizationCN;
    }

    public void setOrganizationCN(String organizationCN) {
        this.organizationCN = organizationCN;
    }

    public String getOrganizationDN() {
        return organizationDN;
    }

    public void setOrganizationDN(String organizationDN) {
        this.organizationDN = organizationDN;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
