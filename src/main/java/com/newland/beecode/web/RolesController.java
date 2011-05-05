package com.newland.beecode.web;

import com.newland.beecode.domain.Roles;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.RolesService;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/roleses")
@Controller
public class RolesController extends BaseController {
    
    @Autowired
    private RolesService rolesService;
    
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Roles roles, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roles);
            return "roleses/create";
        }
        try {
			rolesService.save(roles);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        
        return "redirect:/roleses/" + encodeUrlPathSegment(roles.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("roles", new Roles());
        return "roleses/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        try {
			model.addAttribute("roles", rolesService.findUniqueByProperty("id", id));
			model.addAttribute("itemId", id);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        return "roleses/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model model) {
        try {
			if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
			    model.addAttribute("roleses", rolesService.findRolesEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			    float nrOfPages = (float) rolesService.countRoleses() / sizeNo;
			    model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
			} else {
			    model.addAttribute("roleses", rolesService.findAll());
			}
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        return "roleses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Roles roles, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roles);
            return "roleses/update";
        }
        try {
			rolesService.update(roles);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        return "redirect:/roleses/" + encodeUrlPathSegment(roles.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        try {
			model.addAttribute("roles", rolesService.findUniqueByProperty("id", id));
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        return "roleses/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
    	
    	try {
			rolesService.delete(id);
			
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? "10" : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
        return "redirect:/roleses?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    public String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
