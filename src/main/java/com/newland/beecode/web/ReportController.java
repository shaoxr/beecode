package com.newland.beecode.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.service.ExcelService;
import com.newland.beecode.service.ReportService;
import com.newland.utils.NewlandUtil;
import com.newland.utils.PaginationHelper;

@RequestMapping("/report")
@Controller
public class ReportController {
	
	
	@Autowired
	private ExcelService detailExcelService;
	@Autowired
	private ExcelService countExcelService;
	
	@RequestMapping(value={"/count"},params = {"form" }, method = RequestMethod.GET)
	public String reportCountForm(Model model){
		addDateTimeFormatPatterns(model);
		return "report/count";
	}
	@RequestMapping(value={"/count"},params = {"find=ByCondition" }, method = RequestMethod.GET)
    public String reportCount(
    		@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("parterNo") String parterNo,
			@RequestParam("marketingCatalogId") String marketingCatalogId,
			@RequestParam("partnerCatalogId") String partnerCatalogId,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response){
		Map<String, String> queryParams = PaginationHelper.makeParameters(
				request.getParameterMap(), request.getQueryString());
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);

		addDateTimeFormatPatterns(model);
		ReportForm reportForm=new ReportForm();
		reportForm.setStartDate(minGenTime);
		reportForm.setEndDate(maxGenTime);
		reportForm.setActNo(actNo);
		reportForm.setParterNo(parterNo);
		reportForm.setMarketingCatalogId(new Long(marketingCatalogId));
		reportForm.setPartnerCatalogId(new Long(partnerCatalogId));
		reportForm.setPage(page);
		reportForm.setSize(size);
		reportForm.setPagination(true);
		
		ReportResult rr=Coupon.reportCount(reportForm);
		int maxPages = PaginationHelper.calcMaxPages(size, rr.getCount());
		model.addAttribute("rptDetail",rr.getResultList());
		addDateTimeFormatPatterns(model);
		model.addAttribute("maxPages", maxPages);
		model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		model.addAttribute("minEndDate",NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"));
		model.addAttribute("maxEndDate",NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd"));
		model.addAttribute("actNo",actNo);
		model.addAttribute("parterNo",parterNo);
		
		if(maxPages>0){
			model.addAttribute("emptyNo",true);
		}
    	return "report/count";
    }
	@RequestMapping(value={"/detail"},params = {"form" }, method = RequestMethod.GET)
	public String reportForm(Model model,
			HttpServletRequest request, HttpServletResponse response){
		addDateTimeFormatPatterns(model);
		return "report/detail";
	}
	
	@RequestMapping(value={"/detail"},params = {"find=ByCondition" }, method = RequestMethod.GET)
    public String report(
    		@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("parterNo") String parterNo,
			@RequestParam("marketingCatalogId") String marketingCatalogId,
			@RequestParam("partnerCatalogId") String partnerCatalogId,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response){
		Map<String, String> queryParams = PaginationHelper.makeParameters(
				request.getParameterMap(), request.getQueryString());
		page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
		size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
		String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
		addDateTimeFormatPatterns(model);
		ReportForm reportForm=new ReportForm();
		reportForm.setStartDate(minGenTime);
		reportForm.setEndDate(maxGenTime);
		reportForm.setActNo(actNo);
		reportForm.setMarketingCatalogId(new Long(marketingCatalogId));
		reportForm.setPartnerCatalogId(new Long(partnerCatalogId));
		reportForm.setParterNo(parterNo);
		reportForm.setPage(page);
		reportForm.setSize(size);
		reportForm.setPagination(true);
		
		ReportResult rr=Coupon.reportDetail(reportForm);
		int maxPages = PaginationHelper.calcMaxPages(size, rr.getCount());
		model.addAttribute("rptDetail",rr.getResultList());
		addDateTimeFormatPatterns(model);
		model.addAttribute("maxPages", maxPages);
		model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
		model.addAttribute(PaginationHelper.PARAM_PAGE, page);
		model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		model.addAttribute("minEndDate",NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"));
		model.addAttribute("maxEndDate",NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd"));
		model.addAttribute("actNo",actNo);
		model.addAttribute("parterNo",parterNo);
		if(maxPages>0){
			model.addAttribute("emptyNo",true);
		}
    	return "report/detail";
    }
	@RequestMapping(value={"/detailExcel"}, method = RequestMethod.GET)
	public String reportDetailExcel (
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("parterNo") String parterNo,
			Model model,
			HttpServletRequest request, HttpServletResponse response){
		    ReportForm reportForm=new ReportForm();
		    reportForm.setStartDate(minGenTime);
		    reportForm.setEndDate(maxGenTime);
		    reportForm.setActNo(actNo);
		    reportForm.setParterNo(parterNo);
		    reportForm.setPagination(false);
		    FileInputStream nStream;
		try {
			nStream = new FileInputStream(this.detailExcelService.generateExcelFile(
					Coupon.reportDetail(reportForm).getResultList(), NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"), NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd")));
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" +"reportDetailExcel");
			int l=0;
			byte[] b=new byte[1024 * 1024];
			while (nStream.read(b)>0){
				response.getOutputStream().write(b);
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@RequestMapping(value={"/countExcel"}, method = RequestMethod.GET)
	public String reportCountExcel (
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("parterNo") String parterNo,
			Model model,
			HttpServletRequest request, HttpServletResponse response){
		    ReportForm reportForm=new ReportForm();
		    reportForm.setStartDate(minGenTime);
		    reportForm.setEndDate(maxGenTime);
		    reportForm.setActNo(actNo);
		    reportForm.setParterNo(parterNo);
		    reportForm.setPagination(false);
		    FileInputStream nStream;
		try {
			nStream = new FileInputStream(this.countExcelService.generateExcelFile(
					Coupon.reportCount(reportForm).getResultList(), NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"), NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd")));
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + "reportCountExcel");
			int l=0;
			byte[] b=new byte[1024 * 1024];
			while (nStream.read(b)>0){
				response.getOutputStream().write(b);
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute("date_format", "yyyy-MM-dd");
		model.addAttribute("datetime_format", "yyyy-MM-dd HH:mm");
	}
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return MarketingCatalog.findAllMarketingCatalogs();
	}
	@ModelAttribute("partnerCatalogs")
    public Collection<PartnerCatalog> populatePartnerCatalogs() {
        return PartnerCatalog.findAllPartnerCatalogs();
    }
}
