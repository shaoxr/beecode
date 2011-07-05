package com.newland.beecode.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.MMSService;
import com.newland.beecode.service.SMSService;


@RequestMapping("/msManager")
@Controller
public class MsManagerController extends BaseController{
	
    
	@Autowired
	private MMSService mmsService;
	@Autowired
	private SMSService smsService;
	
	@RequestMapping(value="/balance", method = RequestMethod.GET)
	public String balance(Model model){
		try {
			String mmsBalance = mmsService.getBalanceByMontnets();
			long smsBalance = this.smsService.getBalanceByMontnets();
				
			model.addAttribute("mmsBalance", mmsBalance);
			model.addAttribute("smsBalance", smsBalance);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "msManager/balance";
	}
	
	

}
