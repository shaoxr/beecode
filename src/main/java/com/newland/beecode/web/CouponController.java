package com.newland.beecode.web;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
import com.newland.beecode.domain.DictView;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.service.CouponService;
import com.newland.utils.PaginationHelper;

@RequestMapping("/coupons")
@Controller
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/{couponId}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "couponId") Long couponId,
			Model model) {
		model.addAttribute("coupon", Coupon.findCoupon(couponId));
		addDateTimeFormatPatterns(model);
		return "coupons/show";
	}

	@RequestMapping(value = "/reversal/{couponId}", method = RequestMethod.POST)
	public String cancelCoupon(@RequestParam String mobile, @PathVariable(value = "couponId") Long couponId, Model model){
		if(mobile != null && mobile.trim().length() < 11){
			mobile = null;
		}
		Coupon coupon = couponService.reversalCoupon(couponId, mobile);
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
			@RequestParam("couponId") Integer couponId,
			@RequestParam("couponStatus") Integer couponStatus,
			@RequestParam("marketingCatalogId") Long marketingCatalogId,
			@RequestParam("actNo") Long actNo, 
			@RequestParam("smsStatus") Integer smsStatus, 
			@RequestParam("mmsStatus") Integer mmsStatus, 
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response) {

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
		cc.setPagination(true);
		cc.setPage(page);
		cc.setSize(size);
		QueryResult qr=Coupon.findCouponsByCondition(cc);
		model.addAttribute("coupons",qr.getResultList());
		int maxPages = PaginationHelper.calcMaxPages(size, qr.getCount());
		model.addAttribute("maxPages", maxPages);
		model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		addDateTimeFormatPatterns(model);
		return "coupon/findcouponsbyCondition";
	}

	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return MarketingCatalog.findAllMarketingCatalogs();
	}
	@ModelAttribute("cuoponstatusList")
	public Collection<DictView> populateCuoponstatusList() {
		return DictView.getListByKeyName(Coupon.DICT_KEY_NAME);
	}
	@ModelAttribute("mmsstatusList")
	public Collection<DictView> populateMmsstatusList() {
		return DictView.getListByKeyName(Coupon.MMS_SMS_STATUS_KEY_NAME);
	}
	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute("date_format", "yyyy-MM-dd");
		model.addAttribute("datetime_format", "yyyy-MM-dd HH:mm");
	}
}
