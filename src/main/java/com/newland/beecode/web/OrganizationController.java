package com.newland.beecode.web;

import com.newland.beecode.domain.Organization;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "organizations", formBackingObject = Organization.class)
@RequestMapping("/organizations")
@Controller
public class OrganizationController {
}
