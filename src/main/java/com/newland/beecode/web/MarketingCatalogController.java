package com.newland.beecode.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;

import com.newland.beecode.domain.MarketingCatalog;
import com.newland.utils.PaginationHelper;
//@RooWebScaffold(path = "marketingCatalog", formBackingObject = MarketingCatalog.class, create=true,update=false)
@RequestMapping("/marketingCatalog")
@Controller
public class MarketingCatalogController {
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("marketingCatalog", new MarketingCatalog());
		return "marketingCatalog/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid MarketingCatalog marketingCatalog,Model model){
		marketingCatalog.setCreateTime(new Date());
		marketingCatalog.setUpdateTime(new Date());
		marketingCatalog.persist();
		model.addAttribute("marketingCatalog", MarketingCatalog.findAllMarketingCatalogs());
		return "redirect:/marketingCatalog";
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, Model model,HttpServletRequest request){
		Map<String, String> queryParams = PaginationHelper.makeParameters(
		request.getParameterMap(), "");
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		model.addAttribute("marketingCatalogs", MarketingCatalog.findMarketingCatalogEntries((page.intValue() - 1) * size, size));
		int maxPages = PaginationHelper.calcMaxPages(size, MarketingCatalog.countMarketingCatalogs());
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		return "marketingCatalog/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
		MarketingCatalog.findMarketingCatalog(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
        return "redirect:/marketingCatalog";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		model.addAttribute("marketingCatalog", MarketingCatalog.findMarketingCatalog(id));
		return "marketingCatalog/show";
	}
}