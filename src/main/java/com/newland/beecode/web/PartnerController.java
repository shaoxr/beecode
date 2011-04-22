package com.newland.beecode.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.report.PartnerSummaryReport;
import com.newland.beecode.service.PartnerService;
import com.newland.utils.PaginationHelper;

//@RooWebScaffold(path = "partners", formBackingObject = Partner.class, create=true,update=false)
@RequestMapping("/partners")
@Controller
public class PartnerController {
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		   model.addAttribute("partner", new Partner());
		return "partners/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Partner partner,Model model){
		partner.persist();
		return "redirect:/partners";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		model.addAttribute("partner", Partner.findPartner(id));
		return "partners/show";
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, Model model,HttpServletRequest request){
		Map<String, String> queryParams = PaginationHelper.makeParameters(
		request.getParameterMap(), "");
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		model.addAttribute("partners", Partner.findPartnerEntries((page.intValue() - 1) * size, size));
		int maxPages = PaginationHelper.calcMaxPages(size,Partner.countPartners());
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		return "partners/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
		Partner.findPartner(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
        return "redirect:/partners";
    }
	@ModelAttribute("partnercatalogs")
    public Collection<PartnerCatalog> populatePartnerCatalogs() {
        return PartnerCatalog.findAllPartnerCatalogs();
    }
	
}