package com.newland.beecode.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.newland.beecode.domain.DictView;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.CouponSumaryReport;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MarketingActService;
import com.newland.utils.PaginationHelper;

@RequestMapping("/marketingacts")
@Controller
public class MarketingActController {

	@Autowired
	private MarketingActService marketingActService;
	
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String append(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "actNo", required = true) Long actNo,
			Model model, HttpServletRequest request) {

		try {
			marketingActService.append(actNo, file);
		} catch (AppException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/marketingacts/find/append";
	}

	@RequestMapping(value = "/append/{actNo}", method = RequestMethod.GET)
	public String appendForm(@PathVariable("actNo") Long actNo, Model model) {
		addDateTimeFormatPatterns(model);
		MarketingAct marketingAct= MarketingAct.findMarketingAct(actNo);
		model.addAttribute("marketingAct",marketingAct);
		if(marketingAct.getBizNo().equals("00")){
			model.addAttribute("fuck", true);
		}
		model.addAttribute("itemId", actNo);
		return "marketingacts/append/form";
	}

	@RequestMapping(value = "/find/append", method = RequestMethod.GET)
	public String findMarketingAct4Append(@RequestParam("bizNo") String bizNo,
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minEndDate,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxEndDate,
			@RequestParam("actName") String actName,
			@RequestParam("actStatus") Integer actStatus, Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request) {
		Map<String, String> queryParams = PaginationHelper.makeParameters(
		request.getParameterMap(), request.getQueryString());
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		MarketingActCondition mac=new MarketingActCondition();
		mac.setBizNo(bizNo);
		mac.setStartGenDate(minEndDate);
		mac.setEndGenDate(maxEndDate);
		mac.setActName(actName);
		mac.setActStatus(actStatus);
		mac.setPage(page);
		mac.setSize(size);
		mac.setPagination(true);
		QueryResult qr=MarketingAct.findMarketingActsByCondition(mac);
		int maxPages = PaginationHelper.calcMaxPages(size, qr.getCount());
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		model.addAttribute("marketingacts",qr.getResultList());
		addDateTimeFormatPatterns(model);
		return "marketingacts/list/append";
	}
	@RequestMapping(value = "/find/append",params = { "find=ByCondition" ,"form"}, method = RequestMethod.GET)
	public String findMarketingActAppendForm(Model model) {
		addDateTimeFormatPatterns(model);
		return "marketingacts/list/append";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@Valid MarketingAct marketingAct, BindingResult result,
			Model model, HttpServletRequest request) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("marketingAct", marketingAct);
			model.addAttribute("checkCards", DictView.getListByKeyName(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
			addDateTimeFormatPatterns(model);
			System.out.println(result.getFieldError());
			return "marketingacts/create";
		}
		try {
			marketingActService.createMarketingAct(marketingAct, file);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE,this.getMessage(e));
			e.printStackTrace();
			return "prompt";
		}
		return "redirect:/marketingacts/"
				+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
						request);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("marketingAct", new MarketingAct());
		model.addAttribute("checkCards", DictView.getListByKeyName(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
		addDateTimeFormatPatterns(model);
		return "marketingacts/create";
	}

	@RequestMapping(value = "/{actNo}", method = RequestMethod.GET)
	public String show(@PathVariable("actNo") Long actNo, Model model) {
		addDateTimeFormatPatterns(model);
		MarketingAct act = MarketingAct.findMarketingAct(actNo);
		if (act.getActStatus() > MarketingAct.STATUS_BEFORE_GIVE) {
			MarketingActSummary marketingSummary = MarketingAct.marketingSummary(actNo);
			act.setSummary(marketingSummary);
			model.addAttribute("statistics",true);
		}
		model.addAttribute("marketingact", act);
		model.addAttribute("itemId", actNo);
		return "marketingacts/show";
	}
	@RequestMapping(value = "{actNo}/detail", method = RequestMethod.GET)
	public String couponDetail(@PathVariable("actNo") Long actNo, Model model){
		CouponSumaryReport rpt = couponService.summaryByMarketing(actNo);
		model.addAttribute("rpt", rpt);
		return "marketingacts/report";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model model) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			model.addAttribute("marketingacts", MarketingAct
					.findMarketingActEntries(
							page == null ? 0 : (page.intValue() - 1) * sizeNo,
							sizeNo));
			float nrOfPages = (float) MarketingAct.countMarketingActs()
					/ sizeNo;
			model.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			model.addAttribute("marketingacts",
					MarketingAct.findAllMarketingActs());
		}
		addDateTimeFormatPatterns(model);
		return "marketingacts/list";
	}

	/**
	 * 作废指令，仅在审核状态的营销活动可以作废
	 * 
	 * @param actNo
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/invalid/{actNo}", method = RequestMethod.POST)
	public String invalid(@PathVariable("actNo") Long actNo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			Model model,HttpServletRequest request)  {
		try {
			marketingActService.invalidMarketingAct(actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE,this.getMessage(e));
			return "prompt";
		}
		model.addAttribute("page", (page == null) ? "1" : page.toString());
		model.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/marketingacts?"+query;
	}
    
	@RequestMapping(value = "/find/check", method = RequestMethod.GET)
	public String findMarketingAct4Check(Model model) {
		model.addAttribute(
				"marketingacts",
				MarketingAct.findMarketingActsByActStatus(
						MarketingAct.STATUS_BEFORE_RECHECK).getResultList());
		return "marketingacts/list/check";
	}

	@RequestMapping(value = "/check/{actNo}", method = RequestMethod.GET)
	public String checkMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		addDateTimeFormatPatterns(model);
		model.addAttribute("marketingAct", MarketingAct.findMarketingAct(actNo));
		model.addAttribute("itemId", actNo);
		return "marketingacts/check/form";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkMarketingAct(
			@RequestParam(value = "actNo", required = true) Long actNo,
			@RequestParam(value = "actStatus", required = true) Integer actStatus,
			Model model) {
		try {
			model.addAttribute("count", marketingActService.checkMarketingAct(actNo, actStatus));
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/checkTip";
	}
	@RequestMapping(value = "/find/send", method = RequestMethod.GET)
	public String findMarketingActSend(Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		        Map<String, String> queryParams = PaginationHelper.makeParameters(
				request.getParameterMap(), request.getQueryString());
				page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
				size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
				String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
				QueryResult qr=MarketingAct.findMarketingActEntriesByActStatus(MarketingAct.STATUS_BEFORE_GIVE, page, size);
		    model.addAttribute("marketingacts",qr.getResultList());
		    model.addAttribute("maxPages",qr.getCount());
		    model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
		    model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		return "marketingacts/list/send";
	}
	@RequestMapping(value = "/send/{actNo}", method = RequestMethod.GET)
	public String sendMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		addDateTimeFormatPatterns(model);
		model.addAttribute("marketingact", MarketingAct.findMarketingAct(actNo));
		model.addAttribute("itemId", actNo);
		return "marketingacts/send/form";
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String send(Model model,@RequestParam(value = "actNo", required = true) Long actNo){
		try {
			this.marketingActService.marketingActSend(actNo);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/sendTip";
	}
	/**
	 * 组合条件查询，至少输入一个条件
	 * 
	 * @param minGenTime
	 * @param maxGenTime
	 * @param businessType
	 * @param minEndDate
	 * @param maxEndDate
	 * @param actName
	 * @param actStatus
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = { "find=ByCondition" }, method=RequestMethod.GET)
	public String findMarketingActsByCondition(
			@RequestParam("bizNo") String bizNo,
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minEndDate,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxEndDate,
			@RequestParam("actStatus") Integer actStatus, 
			@RequestParam("marketingCatalog") String marketingCatalog,
			@RequestParam("actNo") String actNo,Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request) {
		Map<String, String> queryParams = PaginationHelper.makeParameters(
		request.getParameterMap(), request.getQueryString());
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
		MarketingActCondition mac=new MarketingActCondition();
		mac.setMarketingCatalogId(new Long(marketingCatalog)); 
		mac.setBizNo(bizNo);
		mac.setStartGenDate(minEndDate);
		mac.setEndGenDate(maxEndDate);
		mac.setActStatus(actStatus);
		mac.setPage(page);
		mac.setSize(size);
		mac.setPagination(true);
		QueryResult qr=MarketingAct.findMarketingActsByCondition(mac);
		int maxPages = PaginationHelper.calcMaxPages(size, qr.getCount());
		model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
		model.addAttribute("maxPages",maxPages);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		model.addAttribute("marketingacts",qr.getResultList());
		addDateTimeFormatPatterns(model);
		return "marketingacts/findMarketingActsByCondition";
	}

	@RequestMapping(params = { "find=ByCondition", "form" }, method = RequestMethod.GET)
	public String findMarketingActsByConditionForm(Model model) {
		addDateTimeFormatPatterns(model);
		return "marketingacts/findMarketingActsByCondition";
	}
	
	@ModelAttribute("partners")
	public Collection<Partner> populatePartners() {
		return Partner.findAllPartners();
	}
	@ModelAttribute("partnerCatalogs")
	public Collection<PartnerCatalog> populatePartnerCatalogs() {
		return PartnerCatalog.findAllPartnerCatalogs();
	}
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return MarketingCatalog.findAllMarketingCatalogs();
	}
	@ModelAttribute("marketingsBlank")
	public Collection<MarketingAct> populateMarketingsBlank() {
		return new ArrayList<MarketingAct>();
	}
	@ModelAttribute("bizTypes")
	public Collection<DictView> populateBizTypes() {
		return DictView.getListByKeyName(MarketingAct.BUSINESS_TYPE);
	}
	@ModelAttribute("marketingstatusList")
	public Collection<DictView> populateMarketingstatusList() {
		return DictView.getListByKeyName(MarketingAct.DICT_KEY_NAME);
	}
	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute("date_format", "yyyy-MM-dd");
		model.addAttribute("datetime_format", "yyyy-MM-dd HH:mm");
	}

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
		String enc = request.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
	private String getMessage(Exception e){
		String errCode=ErrorsCode.SYSTEM_ERR;
		if(e instanceof AppException){
			errCode= ((AppException)e).getCode();
		}
		return errCode;
		
	}
}
