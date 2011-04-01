package com.newland.beecode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.newland.beecode.domain.Roles;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "operNo", finders = { "findOpersByOperNo" })
public class Oper {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
    @GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "100") })
    private Long operNo;

    @NotNull
    @Size(max = 24)
    private String operName;

    @NotNull
    private String operPwd;

    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date genTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Roles> roles = new HashSet<Roles>();
}
