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
import org.springframework.beans.factory.annotation.Autowired;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.MarketingCatalogService;
import com.newland.utils.PaginationHelper;

@RequestMapping("/marketingCatalog")
@Controller
public class MarketingCatalogController extends BaseController{
    
	@Autowired
	private MarketingCatalogService marketingCatalogService;
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("marketingCatalog", new MarketingCatalog());
		return "marketingCatalog/create";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid MarketingCatalog marketingCatalog,Model model){
        
		try {
			if(this.marketingCatalogService.findMarketingCatalogsByCatalogName(marketingCatalog.getCatalogName())!=null){
				throw new AppException(ErrorsCode.BIZ_MARKINGCATALOG_NAME_EXITS,"");
			}
			this.marketingCatalogService.save(marketingCatalog);
			model.addAttribute("marketingCatalog", this.marketingCatalogService.findAll());
     
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		
		return "redirect:/marketingCatalog";
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
			model.addAttribute("marketingCatalogs", this.marketingCatalogService.findMarketingCatalogEntries((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size, this.marketingCatalogService.countMarketingCatalogs());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingCatalog/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
            try {
				this.marketingCatalogService.delete(id);
				 model.addAttribute("page", (page == null) ? "1" : page.toString());
			     model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				this.logger.error(this.getMessage(e), e);
				return "prompt";
			}
        return "redirect:/marketingCatalog";
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		model.addAttribute("marketingCatalog", this.marketingCatalogService.findById(id));
		return "marketingCatalog/show";
	}
	
	@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model)throws Exception{
		
		try {
			MarketingCatalog marketingCatalog=this.marketingCatalogService.findById(id);
			marketingCatalog.setUpdateTime(new Date());
			model.addAttribute("marketingCatalog", marketingCatalog);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingCatalog/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit(@Valid MarketingCatalog marketingCatalog , Model model){
		
		try {
			if(this.marketingCatalogService.findMarketingCatalogsByCatalogName(marketingCatalog.getCatalogName())!=null){
				throw new AppException(ErrorsCode.BIZ_MARKINGCATALOG_NAME_EXITS,"");
			}
            this.marketingCatalogService.update(marketingCatalog);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		
		return "redirect:/marketingCatalog";
	}
	
}
