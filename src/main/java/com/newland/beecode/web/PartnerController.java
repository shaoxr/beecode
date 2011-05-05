package com.newland.beecode.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.beecode.service.PartnerService;
import com.newland.utils.PaginationHelper;

@RequestMapping("/partners")
@Controller
public class PartnerController extends BaseController{
	
    
    @Autowired
    private PartnerCatalogService partnerCatalogService;
    @Autowired
    private PartnerService partnerService;
    
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		   model.addAttribute("partner", new Partner());
		return "partners/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Partner partner,Model model){
            try {
            	List<Partner> partners=this.partnerService.findByProperty("partnerNo", partner.getPartnerNo());
            	if(partners.size()>0){
        			throw new AppException(ErrorsCode.BIZ_PARTNER_NO_EXITS,"");
        		}
        		partnerService.save(partner);
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
		return "redirect:/partners";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		try {
			model.addAttribute("partner", this.partnerService.findById(id));
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "partners/show";
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
			model.addAttribute("partners", this.partnerService.findPartnerEntries((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size,this.partnerService.countPartners());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "partners/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
        try {
        	this.partnerService.delete(id);
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
        return "redirect:/partners";
    }
	@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model){
		try {
			Partner partner=this.partnerService.findById(id);
			model.addAttribute("partner", partner);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "partners/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit(@Valid Partner partner,Model model){
		try{
			this.partnerService.update(partner);
		}catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/partners"; 
	}
	@ModelAttribute("partnercatalogs")
    public Collection<PartnerCatalog> populatePartnerCatalogs() {
            
        return this.partnerCatalogService.findAll();    
    }
	
}
