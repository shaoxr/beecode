package com.newland.beecode.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Roles {

    @NotNull
    @Size(max = 16)
    private String roleNo;

    @NotNull
    @Size(max = 24)
    private String roleName;
}
