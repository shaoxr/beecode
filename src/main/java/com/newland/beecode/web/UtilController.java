package com.newland.beecode.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.ExcelException;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;

@RequestMapping("/util")
@Controller
public class UtilController extends BaseController{
	@Autowired
	private CheckService checkService;
	@Autowired
	private FileService fileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MarketingActService marketingActService;
	@RequestMapping(value={"checkcustomer"},params = "form", method = RequestMethod.GET)
	public String checkCustomerForm(Model model){
		return "util/checkcustomer";
	}
	@RequestMapping(value={"checkcustomer"},method = RequestMethod.POST)
	public String checkCustomer(@RequestParam(value = "file", required = true) MultipartFile file,
			Model model){
		String fileName=String.valueOf(System.currentTimeMillis());
		try {
			model.addAttribute(ErrorsCode.MESSAGE, this.checkService.checkCustomerFile(file,fileName,null,null));
		} catch (Exception e) {
			if(e instanceof ExcelException){
				model.addAttribute(ErrorsCode.MESSAGE,e.getMessage());
			}else{
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
		}
		return "util/checkcustomer";
	}
	@RequestMapping(value={"checkpartner"},params = "form", method = RequestMethod.GET)
	public String checkPartnerForm(Model model){
		return "util/checkpartner";
	}
	@RequestMapping(value={"checkpartner"},method = RequestMethod.POST)
	public String checkPartner(@RequestParam(value = "file", required = true) MultipartFile file,
			Model model){
		String fileName=String.valueOf(System.currentTimeMillis());
		try {
			 Set<Terminal> terminals=this.checkService.partnerCheck(file, fileName);
			 model.addAttribute(ErrorsCode.MESSAGE, messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PASS, new Object[]{terminals.size()}, Locale.CHINA));
		} catch (Exception e) {
			if(e instanceof ExcelException){
				model.addAttribute(ErrorsCode.MESSAGE,e.getMessage());
			}else{
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				return "prompt";
			}
		}
		return "util/checkpartner";
	}
	@RequestMapping(value={"accessDenied"},method = RequestMethod.GET)
	public String accessDenied(Model model){
		model.addAttribute(ErrorsCode.MESSAGE, "security_accessDenied");
		return "prompt";
	}
	@RequestMapping(value={"downloadcustomer"},method = RequestMethod.GET)
	public String downLoad(@RequestParam(value = "newName", required = true) String newName,
			@RequestParam(value = "oldName", required = true) String oldName,
			Model model,HttpServletRequest request, HttpServletResponse response){
		FileInputStream nStream;
		try {
			nStream=new FileInputStream(this.fileService.getCustomerFile(newName));
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+newName);
			byte[] b=new byte[1024];
			while (nStream.read(b)>0){
				response.getOutputStream().write(b);
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				logger.error("close outputStream error", e);
			}
		}
		return null;
		
	}
	@RequestMapping(value={"downloadmmszip"},method = RequestMethod.POST)
	public String downLoadMMSZip(@RequestParam(value = "actNo") Long actNo,
			Model model,HttpServletRequest request, HttpServletResponse response){
		
		try {
			List<Coupon> coupons=this.marketingActService.getCoupon2Send(actNo, SendList.MS_TYPE_MMS);
			MarketingAct act =this.marketingActService.findByActNo(actNo);
			
			response.setHeader("Content-disposition", "attachment; filename="
					+ "mms.zip");
			response
					.setHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0,private, max-age=0");
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			this.fileService.genGetMMSzips(act,coupons,response);
			response.getOutputStream().close();
			
		} catch (Throwable e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			logger.error("", e);
			return "prompt";
		}
		return null;
		
	}
	@RequestMapping(value={"downloadsmszip"},method = RequestMethod.POST)
	public String downLoadSMSZip(@RequestParam(value = "actNo") Long actNo,
			Model model,HttpServletRequest request, HttpServletResponse response){
		
		try {
			List<Coupon> coupons=this.marketingActService.getCoupon2Send(actNo, SendList.MS_TYPE_SMS);
			MarketingAct act =this.marketingActService.findByActNo(actNo);
			response.setHeader("Content-disposition", "attachment; filename="
					+ "sms.zip");
			response
					.setHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0,private, max-age=0");
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			this.fileService.genGetSMSzips(act,coupons,response);
			response.getOutputStream().close();
		} catch (Throwable e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			logger.error("", e);
			return "prompt";
		}
		return null;
	}

}
