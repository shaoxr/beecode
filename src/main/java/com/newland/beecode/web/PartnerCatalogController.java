package com.newland.beecode.web;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.utils.PaginationHelper;
@RequestMapping("/partnerCatalog")
@Controller
public class PartnerCatalogController extends BaseController{
    @Autowired
    private PartnerCatalogService  partnerCatalogService; 
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("partnerCatalog", new PartnerCatalog());
		return "partnerCatalog/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid PartnerCatalog partnerCatalog,Model model){
		try {
			if(this.partnerCatalogService.findPartnerCatalogsByCatalogName(partnerCatalog.getCatalogName())!=null){
				throw new AppException(ErrorsCode.BIZ_PARTNERCATALOG_NAME_EXITS,"");
			}
			partnerCatalog.setCreateTime(new Date());
			partnerCatalog.setUpdateTime(new Date());
            this.partnerCatalogService.save(partnerCatalog);
			model.addAttribute("partnercatalogs", this.partnerCatalogService.findAll());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/partnerCatalog";
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, Model model,HttpServletRequest request){
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), "");
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			model.addAttribute("partnercatalogs", this.partnerCatalogService.findPartnerCatalogEntries((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size, this.partnerCatalogService.countPartnerCatalogs());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (NumberFormatException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "partnerCatalog/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
            try {
				this.partnerCatalogService.delete(id);
				model.addAttribute("page", (page == null) ? "1" : page.toString());
		        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				this.logger.error(this.getMessage(e), e);
				return "prompt";
			}
        return "redirect:/partnerCatalog";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		try {
			PartnerCatalog partnerCatalog=this.partnerCatalogService.findById(id);
			model.addAttribute("partnerCatalog", partnerCatalog);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "partnerCatalog/show";
	}
	
	@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model){
		try {
			PartnerCatalog partnerCatalog=this.partnerCatalogService.findById(id);
			partnerCatalog.setUpdateTime(new Date());
			model.addAttribute("partnerCatalog", partnerCatalog);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "partnerCatalog/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit(@Valid PartnerCatalog partnerCatalog,Model model){	
		try {
			if(this.partnerCatalogService.findPartnerCatalogsByCatalogName(partnerCatalog.getCatalogName())!=null){
				throw new AppException(ErrorsCode.BIZ_PARTNERCATALOG_NAME_EXITS,"");
			}
            this.partnerCatalogService.update(partnerCatalog);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/partnerCatalog";
	}
}
