package com.newland.beecode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Oper {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
    @GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
    @Column
    private Long operNo;

    @Column
    private String operName;

    @Column
    private String operPwd;

    @Column
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date genTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="oper_roles",joinColumns=@JoinColumn(name="oper"),inverseJoinColumns=@JoinColumn(name="roles"))
    private Set<Roles> roles = new HashSet<Roles>();

    @Version
    @Column(name = "version")
    private Integer version; 
    
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public Long getOperNo() {
        return operNo;
    }

    public void setOperNo(Long operNo) {
        this.operNo = operNo;
    }

    public String getOperPwd() {
        return operPwd;
    }

    public void setOperPwd(String operPwd) {
        this.operPwd = operPwd;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
    
    
}
