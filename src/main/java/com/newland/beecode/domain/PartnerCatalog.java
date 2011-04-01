package com.newland.beecode.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPartnerCatalogsByCatalogName" })
public class PartnerCatalog {

    @NotNull
    @Size(max = 32)
    private String catalogName;
    
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
    private Date createTime;
    
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
    private Date updateTime;
    
}
