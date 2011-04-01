package com.newland.beecode.web;

import com.newland.beecode.domain.CoupontCtrl;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "coupontctrls", formBackingObject = CoupontCtrl.class)
@RequestMapping("/coupontctrls")
@Controller
public class CoupontCtrlController {
}
