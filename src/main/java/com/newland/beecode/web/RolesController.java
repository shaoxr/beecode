package com.newland.beecode.web;

import com.newland.beecode.domain.Roles;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "roleses", formBackingObject = Roles.class)
@RequestMapping("/roleses")
@Controller
public class RolesController {
}
