// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import java.lang.String;

privileged aspect Organization_Roo_ToString {
    
    public String Organization.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrganizationDN: ").append(getOrganizationDN()).append(", ");
        sb.append("OrganizationCN: ").append(getOrganizationCN());
        return sb.toString();
    }
    
}