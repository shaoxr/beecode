package com.newland.beecode.domain;

import com.sun.istack.internal.NotNull;
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
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Oper {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
    @GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "100") })
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

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Roles> roles = new HashSet<Roles>();
}
