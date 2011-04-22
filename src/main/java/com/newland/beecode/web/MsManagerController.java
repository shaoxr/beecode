package com.newland.beecode.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tempuri.service.MMSService;

import com.newland.beecode.domain.DictView;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.exception.ErrorsCode;

@RequestMapping("/msManager")
@Controller
public class MsManagerController extends BaseController{
	
	@Autowired
	private MMSService mmsService;
	
	@RequestMapping(value="/balance",params = "form", method = RequestMethod.GET)
	public String balanceForm(Model model){
		model.addAttribute("msTypes", DictView.getListByKeyName(MarketingAct.MS_TYPE));
		return "msManager/balance";
	}
	@RequestMapping(value="/balance",params = "find=ByCondition", method = RequestMethod.GET)
	public String balance(Model model,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "passWord", required = false) String passWord,
			@RequestParam(value = "msType", required = false) String msType){
		long count=0;
		try {
			count = mmsService.getBalance(userName, passWord, 1);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_MMS_CON_ERROR);
			return "prompt";
		}
		model.addAttribute("msTypes", DictView.getListByKeyName(MarketingAct.MS_TYPE));
		model.addAttribute("count", count);
		model.addAttribute("msType", msType);
		return "msManager/balance";
	}
	
	

}