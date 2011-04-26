package com.newland.beecode.web;

import com.newland.beecode.dao.OrganizationDao;
import com.newland.beecode.domain.Organization;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

//@RooWebScaffold(path = "organizations", formBackingObject = Organization.class)
@RequestMapping("/organizations")
@Controller
public class OrganizationController {
    
    @Resource(name = "organizationDao")
    private OrganizationDao organizationDao;
    
       @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Organization organization, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            return "organizations/create";
        }
        //organization.persist();
        organizationDao.save(organization);
        return "redirect:/organizations/" + encodeUrlPathSegment(organization.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("organization", new Organization());
        return "organizations/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("organization", organizationDao.get(id));
        model.addAttribute("itemId", id);
        return "organizations/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("organizations", organizationDao.findOrganizationEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) organizationDao.countOrganizations() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("organizations", organizationDao.findAll());
        }
        return "organizations/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Organization organization, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            return "organizations/update";
        }
        //organization.merge();
        organizationDao.update(organization);
        return "redirect:/organizations/" + encodeUrlPathSegment(organization.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("organization", organizationDao.get(id));
        return "organizations/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        //Organization.findOrganization(id).remove();
        organizationDao.delete(id);
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/organizations?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
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
