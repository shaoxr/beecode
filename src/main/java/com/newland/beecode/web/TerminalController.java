package com.newland.beecode.web;

import java.util.Collection;
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
import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerService;
import com.newland.beecode.service.TerminalService;
import com.newland.utils.PaginationHelper;

/**
 * @author shaoxr:
 * @version 2011-7-3 下午02:26:32
 * 
 */
@RequestMapping("/terminal")
@Controller
public class TerminalController extends BaseController{
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private PartnerService partnerService;
	    
		@RequestMapping(params = "form", method = RequestMethod.GET)
		public String create(Model model){
			   model.addAttribute("terminal", new Terminal());
			return "terminal/create";
		}
		@RequestMapping(method = RequestMethod.POST)
		public String create(@Valid Terminal terminal,Model model){
	            try {
	        		this.terminalService.save(terminal);
				} catch (Exception e) {
					model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
					return "prompt";
				}
			return "redirect:/terminal";
		}
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public String show(Model model,@PathVariable("id") Long id){
			try {
				model.addAttribute("terminal", this.terminalService.findById(id));
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
			return "terminal/show";
		}
		@RequestMapping(method = RequestMethod.GET)
		public String list(@RequestParam(value = "page", required = false) Integer page, 
				@RequestParam(value = "size", required = false) Integer size,Model model,HttpServletRequest request){
			try {
				Map<String, String> queryParams = PaginationHelper.makeParameters(
				request.getParameterMap(), "");
				page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
				size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
				model.addAttribute("terminals", this.terminalService.findTerminalEntries((page.intValue() - 1) * size, size));
				int maxPages = PaginationHelper.calcMaxPages(size,this.terminalService.countTerminals());
				model.addAttribute("maxPages",maxPages);
				model.addAttribute(PaginationHelper.PARAM_PAGE, page);
				model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
			return "terminal/list";
		}
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public String delete(@PathVariable("id") Long id,
	    		@RequestParam(value = "page", required = false) Integer page, 
	    		@RequestParam(value = "size", required = false) Integer size, Model model) {
	        try {
	        	this.terminalService.delete(id);
	 			model.addAttribute("page", (page == null) ? "1" : page.toString());
				model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
	        return "redirect:/terminal";
	    }
		@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
		public String update(@PathVariable("id") Long id,Model model){
			try {
				Terminal terminal=this.terminalService.findById(id);
				model.addAttribute("terminal", terminal);
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
			return "terminal/update";
		}
		@RequestMapping(method=RequestMethod.PUT)
		public String updateSubmit(@Valid Terminal terminal,Model model){
			try{
				this.terminalService.update(terminal);
			}catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
			return "redirect:/terminal"; 
		}
		@ModelAttribute("partners")
	    public Collection<Partner> populatePartners() {
	            
	        return this.partnerService.findAll();    
	    }

}
