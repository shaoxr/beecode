package com.newland.beecode.web;

import com.newland.beecode.domain.Roles;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
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
public class RolesController {
    
        @RequestMapping(method = RequestMethod.POST)
    public String RolesController.create(@Valid Roles roles, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roles);
            return "roleses/create";
        }
        roles.persist();
        return "redirect:/roleses/" + encodeUrlPathSegment(roles.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String RolesController.createForm(Model model) {
        model.addAttribute("roles", new Roles());
        return "roleses/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String RolesController.show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", Roles.findRoles(id));
        model.addAttribute("itemId", id);
        return "roleses/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String RolesController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("roleses", Roles.findRolesEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Roles.countRoleses() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("roleses", Roles.findAllRoleses());
        }
        return "roleses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String RolesController.update(@Valid Roles roles, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roles);
            return "roleses/update";
        }
        roles.merge();
        return "redirect:/roleses/" + encodeUrlPathSegment(roles.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String RolesController.updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", Roles.findRoles(id));
        return "roleses/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String RolesController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        Roles.findRoles(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/roleses?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    String RolesController.encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
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
