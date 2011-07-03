package com.newland.beecode.web;

import java.util.Date;
import java.util.List;
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
import com.newland.beecode.domain.Store;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.beecode.service.StoreService;
import com.newland.utils.PaginationHelper;

/**
 * @author shaoxr:
 * @version 2011-6-24 下午01:20:30
 * 
 */
@RequestMapping("/store")
@Controller
public class StoreController extends BaseController{
	@Autowired
    private PartnerCatalogService  partnerCatalogService; 
	@Autowired
    private StoreService  storeService; 
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("store", new Store());
		 model.addAttribute("partnercatalogs", this.partnerCatalogService.findAll());
		return "store/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Store store,Model model){
		try {
            this.storeService.save(store);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/store";
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
			model.addAttribute("stores", this.storeService.findStoreEntries((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size, this.storeService.countStore());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (NumberFormatException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "stroe/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
            try {
				this.storeService.delete(id);
				model.addAttribute("page", (page == null) ? "1" : page.toString());
		        model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
        return "redirect:/store";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		try {
			Store store=this.storeService.findById(id);
			model.addAttribute("store", store);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "store/show";
	}
	
	@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model){
		try {
			Store store=this.storeService.findById(id);
			model.addAttribute("store", store);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "store/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit(@Valid Store store,Model model){	
		try {
            this.storeService.update(store);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/store";
	}

}
