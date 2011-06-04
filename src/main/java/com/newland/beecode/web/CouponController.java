package com.newland.beecode.web;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.DictViewFormatter;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.ExcelException;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.MarketingCatalogService;
import com.newland.beecode.service.SendService;
import com.newland.beecode.task.service.SendInvokeService;
import com.newland.utils.PaginationHelper;

import javax.annotation.Resource;

@RequestMapping("/coupons")
@Controller
public class CouponController extends BaseController{

    @Resource(name = "dictViewService")
    private DictViewFormatter dictView;
    
	@Autowired
	private CouponService couponService;
	@Autowired
	private MarketingCatalogService marketingCatalogService;
	@Autowired
	private SendService sendService;
	@Autowired
	private MarketingActService marketingActService;
	@RequestMapping(value = "/{couponId}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "couponId") Long couponId,
			Model model) {
		try {
			model.addAttribute("coupon", this.couponService.findByCoupon(couponId));
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "coupons/show";
	}

	@RequestMapping(value = "/reversal/{couponId}", method = RequestMethod.POST)
	public String cancelCoupon(@RequestParam String mobile, @PathVariable(value = "couponId") Long couponId, Model model){
		Coupon coupon;
		try {
			if(mobile != null && mobile.trim().length() < 11){
				mobile = null;
			}
			coupon = couponService.reversalCoupon(couponId, mobile);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/coupons/" + coupon.getCouponId();
	}
	@RequestMapping(params = { "find=ByCondition", "form" }, method = RequestMethod.GET)
	public String findMarketingActsByConditionForm(Model model) {
		addDateTimeFormatPatterns(model);
		return "coupons/findcouponsbyCondition";
	}

	@RequestMapping(params = { "find=ByCondition" }, method = RequestMethod.GET)
	public String findCouponsByCondition(
			@RequestParam("mobile") String mobile,
			@RequestParam("couponId") Long couponId,
			@RequestParam("couponStatus") Integer couponStatus,
			@RequestParam("marketingCatalogId") Long marketingCatalogId,
			@RequestParam("actNo") Long actNo, 
			@RequestParam("smsStatus") Integer smsStatus, 
			@RequestParam("mmsStatus") Integer mmsStatus, 
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
					request.getParameterMap(), request.getQueryString());
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
			CouponCondition cc=new CouponCondition();
			cc.setActNo(actNo);
			cc.setCouponId(couponId);
			cc.setCouponStatus(couponStatus);
			cc.setMobile(mobile);
			cc.setMarketingCatalogId(marketingCatalogId);
			cc.setMmsStatus(mmsStatus);
			cc.setSmsStatus(smsStatus);
			List<Coupon> coupons=this.couponService.findByCondition(cc,(page-1)*size,size);
			model.addAttribute("coupons",coupons);
			int maxPages = PaginationHelper.calcMaxPages(size, this.couponService.countByCondition(cc));
			model.addAttribute("maxPages", maxPages);
			model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "coupons/findcouponsbyCondition";
	}
	
	@RequestMapping(value={"updateStatus"},method = RequestMethod.GET)
    public String updateStatusForm(
    		Model model){
		return "coupons/updateStatus";
    	
    }
	
	@RequestMapping(value="updateStatus", method = RequestMethod.POST)
	public String updateStatus(@RequestParam("type") Integer type,
    		@RequestParam(value = "file", required = true) MultipartFile file,
			Model model, HttpServletRequest request){
		try {
			model.addAttribute(ErrorsCode.MESSAGE, this.couponService.updateRespStatus(file, type));
		} catch (Exception e) {
			    if(e instanceof ExcelException){
			    	model.addAttribute(ErrorsCode.MESSAGE, e.getMessage());
			    }else{
			    	model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			    	return "prompt";
			    }
		}
		return "coupons/updateStatus";
	}
	@RequestMapping(value = "/find/send", method = RequestMethod.GET)
	public String findMarketingActSend(Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		        try {
					Map<String, String> queryParams = PaginationHelper.makeParameters(
					request.getParameterMap(), "");
					page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
					size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
					String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
					List<MarketingAct> marketingActs=this.marketingActService.findMarketingActEntriesByActStatus(MarketingAct.STATUS_BEFORE_GIVE, (page-1)*size, size);
                    model.addAttribute("marketingacts",marketingActs);
                    int maxPages = PaginationHelper.calcMaxPages(size, this.marketingActService.countByActStatus(MarketingAct.STATUS_BEFORE_GIVE));
                    model.addAttribute("maxPages",maxPages);
                    model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
                    model.addAttribute(PaginationHelper.PARAM_PAGE, page);
                    model.addAttribute(PaginationHelper.PARAM_SIZE, size);
				} catch (NumberFormatException e) {
					model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
					return "prompt";
				}
		return "coupons/list/send";
	}
	@RequestMapping(value = "/send/{actNo}", method = RequestMethod.GET)
	public String sendMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		try {
			addDateTimeFormatPatterns(model);
			MarketingAct act=this.marketingActService.findByActNo(actNo);
			act.setMsStatus(this.couponService.findMSStatus(actNo));
			model.addAttribute("marketingact", act);
			
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "coupons/send/form";
	}
	@RequestMapping(value = "/sendover", method = RequestMethod.POST)
	public String sendOver(Model model,@RequestParam(value = "actNo", required = true) Long actNo){
		try {
			this.marketingActService.sendOver(actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/coupons/send/"+actNo;
	}
	@RequestMapping(value={"/send"},method = RequestMethod.POST)
    public String Send(@RequestParam(value = "actNo") Long actNo,
    		@RequestParam(value = "msType") Integer msType,
    		Model model){
		  try {
			this.marketingActService.send(actNo, msType);
			if(msType.equals(SendList.MS_TYPE_MMS)){
				model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_MMS_SUBMIT_SUCCESS);
			}
			else{
				model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_SMS_SUBMIT_SUCCESS);
			}
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "coupons/sendTip";
    	
    }
	@RequestMapping(value={"resend/{couponId}"},method = RequestMethod.POST)
    public String mmsResend(@PathVariable(value = "couponId") Long couponId,
    		@RequestParam(value = "msType") Integer msType,
    		Model model){
		  try {
			this.marketingActService.sendOne(couponId, msType);
			if(msType.equals(SendList.MS_TYPE_MMS)){
				model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_MMS_SUBMIT_SUCCESS);
			}
			else{
				model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_SMS_SUBMIT_SUCCESS);
			}
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "coupons/sendTip";
    	
    }
	
	@RequestMapping(value={"sendCount"},method = RequestMethod.GET)
    public String sendCount(
    		Model model){
		try {
			model.addAttribute("MsStatus", this.couponService.findSendCount());
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return "coupons/sendCount";
    	
    }
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return this.marketingCatalogService.findAll();
	}
        
	@ModelAttribute("cuoponstatusList")
	public Collection<Dictionary> populateCuoponstatusList() {
            return dictView.getSelectModelCollection(Coupon.DICT_KEY_NAME);
	}
        
	@ModelAttribute("mmsstatusList")
	public Collection<Dictionary> populateMmsstatusList() {
            return dictView.getSelectModelCollection(Coupon.MMS_SMS_STATUS_KEY_NAME);
	}
	@ModelAttribute("msTypes")
	public Collection<Dictionary> populateMsTypes() {
            return dictView.getSelectModelCollection(SendList.DICT_NAME_MS_TYPE);
	}
}
