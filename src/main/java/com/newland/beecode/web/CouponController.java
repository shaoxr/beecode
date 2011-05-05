package com.newland.beecode.web;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.DictViewFormatter;
import java.util.Collection;
import java.util.List;
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

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MarketingCatalogService;
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
	
	@RequestMapping(value = "/{couponId}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "couponId") Long couponId,
			Model model) {
		try {
			model.addAttribute("coupon", this.couponService.findByCoupon(couponId));
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
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
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/coupons/" + coupon.getCouponId();
	}
	@RequestMapping(params = { "find=ByCondition", "form" }, method = RequestMethod.GET)
	public String findMarketingActsByConditionForm(Model model) {
		addDateTimeFormatPatterns(model);
		return "coupon/findcouponsbyCondition";
	}

	@RequestMapping(params = { "find=ByCondition" }, method = RequestMethod.GET)
	public String findMarketingActsByCondition(
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
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "coupon/findcouponsbyCondition";
	}

	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return this.marketingCatalogService.findAll();
	}
        
	@ModelAttribute("cuoponstatusList")
	public Collection<Dictionary> populateCuoponstatusList() {
		//return DictView.getListByKeyName(Coupon.DICT_KEY_NAME);
            return dictView.getSelectModelCollection(Coupon.DICT_KEY_NAME);
	}
        
	@ModelAttribute("mmsstatusList")
	public Collection<Dictionary> populateMmsstatusList() {
		//return DictView.getListByKeyName(Coupon.MMS_SMS_STATUS_KEY_NAME);
            return dictView.getSelectModelCollection(Coupon.MMS_SMS_STATUS_KEY_NAME);
	}
}
