package com.newland.beecode.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CheckService;

@RequestMapping("/util")
@Controller
public class UtilController extends BaseController{
	@Autowired
	private CheckService checkService;
	@RequestMapping(value={"checkcustomer"},params = "form", method = RequestMethod.GET)
	public String checkCustomerForm(Model model){
		return "util/checkcustomer";
	}
	@RequestMapping(value={"checkcustomer"},method = RequestMethod.POST)
	public String checkCustomer(@RequestParam(value = "file", required = true) MultipartFile file,
			Model model){
		String fileName=String.valueOf(System.currentTimeMillis());
		try {
			model.addAttribute(ErrorsCode.MESSAGE, this.checkService.customerCheck(file,fileName).getMessage());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		
		return "util/checkcustomer";
	}
	@RequestMapping(value={"accessDenied"},method = RequestMethod.GET)
	public String accessDenied(Model model){
		model.addAttribute(ErrorsCode.MESSAGE, "security_accessDenied");
		return "prompt";
	}

}
