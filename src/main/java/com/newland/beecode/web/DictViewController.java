package com.newland.beecode.web;

import com.newland.beecode.domain.DictView;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "dictviews", formBackingObject = DictView.class)
@RequestMapping("/dictviews")
@Controller
public class DictViewController {
}
