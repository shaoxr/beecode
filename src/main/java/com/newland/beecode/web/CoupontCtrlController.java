package com.newland.beecode.web;

import com.newland.beecode.dao.CouponCtrlDao;
import com.newland.beecode.domain.CoupontCtrl;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;



@RequestMapping("/coupontCtrl")
@Controller
public class CoupontCtrlController {
    
    @Resource(name = "couponCtlDao")
    private CouponCtrlDao coupontCtrlDao;
    
    
        @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid CoupontCtrl coupontCtrl, Model model, HttpServletRequest request) {
        coupontCtrlDao.save(coupontCtrl);
        return "redirect:/coupontctrls/" + encodeUrlPathSegment(coupontCtrl.getCouponId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("coupontCtrl", new CoupontCtrl());
        addDateTimeFormatPatterns(model);
        return "coupontctrls/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        addDateTimeFormatPatterns(model);
        model.addAttribute("coupontctrl", coupontCtrlDao.get(id));
        model.addAttribute("itemId", id);
        return "coupontctrls/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("coupontctrls", coupontCtrlDao.findCoupontCtrlEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) coupontCtrlDao.countCoupontCtrls() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("coupontctrls", coupontCtrlDao.findAll());
        }
        addDateTimeFormatPatterns(model);
        return "coupontctrls/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid CoupontCtrl coupontCtrl, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("coupontCtrl", coupontCtrl);
            addDateTimeFormatPatterns(model);
            return "coupontctrls/update";
        }
        //coupontCtrl.merge();
        coupontCtrlDao.update(coupontCtrl);
        return "redirect:/coupontctrls/" + encodeUrlPathSegment(coupontCtrl.getCouponId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("coupontCtrl", coupontCtrlDao.get(id));
        addDateTimeFormatPatterns(model);
        return "coupontctrls/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        //CoupontCtrl.findCoupontCtrl(id).remove();
        coupontCtrlDao.delete(id);
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/coupontctrls?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    void addDateTimeFormatPatterns(Model model) {
        model.addAttribute("coupontCtrl_checkdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        model.addAttribute("coupontCtrl_checkday_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
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
