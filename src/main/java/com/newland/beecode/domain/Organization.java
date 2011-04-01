package com.newland.beecode.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class Organization {

    @NotNull
    @Size(max = 16)
    @Column(unique=true)
    private String organizationDN;

    @NotNull
    @Size(max = 32)
    private String organizationCN;
}
