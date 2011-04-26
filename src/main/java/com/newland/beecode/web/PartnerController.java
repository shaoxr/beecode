package com.newland.beecode.web;

import com.newland.beecode.dao.PartnerCatalogDao;
import com.newland.beecode.dao.PartnerDao;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.utils.PaginationHelper;
import javax.annotation.Resource;

//@RooWebScaffold(path = "partners", formBackingObject = Partner.class, create=true,update=false)
@RequestMapping("/partners")
@Controller
public class PartnerController {
	
    @Resource(name = "partnerDao")
    private PartnerDao partnerDao;
    
    @Resource(name = "partnerCatalogDao")
    private PartnerCatalogDao partnerCatalogDao;
    
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		   model.addAttribute("partner", new Partner());
		return "partners/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Partner partner,Model model){
		//partner.persist();
            partnerDao.save(partner);
		return "redirect:/partners";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		model.addAttribute("partner", partnerDao.findUniqueByProperty("id", id));
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
		model.addAttribute("partners", partnerDao.findPartnerEntries((page.intValue() - 1) * size, size));
		int maxPages = PaginationHelper.calcMaxPages(size,partnerDao.countPartners());
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		return "partners/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
		//Partner.findPartner(id).remove();
            partnerDao.delete(id);
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
        return "redirect:/partners";
    }
	@ModelAttribute("partnercatalogs")
    public Collection<PartnerCatalog> populatePartnerCatalogs() {
        //return PartnerCatalog.findAllPartnerCatalogs();
            
        return partnerCatalogDao.findAll();    
    }
	
}
