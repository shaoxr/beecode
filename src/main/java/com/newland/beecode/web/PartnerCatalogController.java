package com.newland.beecode.web;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.utils.PaginationHelper;
@RequestMapping("/partnerCatalog")
@Controller
public class PartnerCatalogController extends BaseController{
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("partnerCatalog", new PartnerCatalog());
		return "partnerCatalog/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid PartnerCatalog partnerCatalog,Model model){
		try {
			if(PartnerCatalog.findPartnerCatalogsByCatalogName(partnerCatalog.getCatalogName())!=null){
				throw new AppException("","");
			}
			partnerCatalog.setCreateTime(new Date());
			partnerCatalog.setUpdateTime(new Date());
			partnerCatalog.persist();
			model.addAttribute("partnercatalogs", PartnerCatalog.findAllPartnerCatalogs());
		} catch (Exception e) {
			model.addAttribute("", this.getMessage(e));
		}
		return "redirect:/partnerCatalog";
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, Model model,HttpServletRequest request){
		Map<String, String> queryParams = PaginationHelper.makeParameters(
		request.getParameterMap(), "");
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		model.addAttribute("partnercatalogs", PartnerCatalog.findPartnerCatalogEntries((page.intValue() - 1) * size, size));
		int maxPages = PaginationHelper.calcMaxPages(size, PartnerCatalog.countPartnerCatalogs());
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		return "partnerCatalog/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
		PartnerCatalog.findPartnerCatalog(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
        return "redirect:/partnerCatalog";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		PartnerCatalog partnerCatalog=PartnerCatalog.findPartnerCatalog(id);
		model.addAttribute("partnerCatalog", partnerCatalog);
		return "partnerCatalog/show";
	}
}
