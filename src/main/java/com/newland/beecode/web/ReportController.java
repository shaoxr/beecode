package com.newland.beecode.web;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.ExcelService;
import com.newland.beecode.service.MarketingCatalogService;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.beecode.service.PartnerService;
import com.newland.utils.NewlandUtil;
import com.newland.utils.PaginationHelper;

@RequestMapping("/report")
@Controller
public class ReportController extends BaseController{
	private static final String REPORT_TYPE_DETAIL="2";
	private static final String REPORT_TYPE_COUNT="1";
    @Autowired
    private CouponService couponService;
    @Autowired
    private MarketingCatalogService marketingCatalogService;
	@Autowired
	private ExcelService detailExcelService;
	@Autowired
	private ExcelService countExcelService;
	@Autowired
	private PartnerService partnerService;
	
	@RequestMapping(value={"/count"},params = {"form" }, method = RequestMethod.GET)
	public String reportCountForm(Model model){
		addDateTimeFormatPatterns(model);
		return "report/count";
	}
	@RequestMapping(value={"/detail"},params = {"form" }, method = RequestMethod.GET)
	public String reportDetailForm(Model model){
		addDateTimeFormatPatterns(model);
		return "report/detail"; 
	}
	@RequestMapping(value={"/query"},params = {"find=ByCondition" }, method = RequestMethod.GET)
    public String reportCount(
    		@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("terminalNo") String terminalNo,
			@RequestParam("marketingCatalogId") String marketingCatalogId,
			@RequestParam("partnerId") String partnerId,
			@RequestParam("reportType") String reportType,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request, HttpServletResponse response){
		try {
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
			reportForm.setPartnerId(new Long(partnerId));
			reportForm.setTerminalNo(terminalNo);
			reportForm.setPage(page);
			reportForm.setSize(size);
			reportForm.setPagination(true);
			int maxPages=0;
			if(REPORT_TYPE_COUNT.equals(reportType)){
				ReportResult rr = this.couponService.reportCount(reportForm);
				maxPages = PaginationHelper.calcMaxPages(size, rr.getCount());
				model.addAttribute("rptDetail",rr.getResultList());
				model.addAttribute("maxPages", maxPages);
			}else{
				ReportResult rr = this.couponService.reportDetail(reportForm);
				maxPages = PaginationHelper.calcMaxPages(size, rr.getCount());
				model.addAttribute("rptDetail",rr.getResultList());
				model.addAttribute("maxPages", maxPages);
			}
			
			model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			model.addAttribute("minEndDate",NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"));
			model.addAttribute("maxEndDate",NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd"));
			model.addAttribute("actNo",actNo);
			model.addAttribute("marketingCatalogId",marketingCatalogId);
			model.addAttribute("terminalNo",terminalNo);
			model.addAttribute("partnerId",partnerId);
			
			if(maxPages>0){
				model.addAttribute("emptyNo",true);
			}
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		if(REPORT_TYPE_COUNT.equals(reportType)){
			return "report/count";
		}
		return "report/detail"; 
    }
	
	@RequestMapping(value={"/excel"}, method = RequestMethod.GET)
	public String reportDetailExcel (
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minGenTime,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxGenTime,
			@RequestParam("actNo") String actNo,
			@RequestParam("terminalNo") String terminalNo,
			@RequestParam("marketingCatalogId") String marketingCatalogId,
			@RequestParam("partnerId") String partnerId,
			@RequestParam("reportType") String reportType,
			Model model,
			HttpServletRequest request, HttpServletResponse response){
		try {
		    ReportForm reportForm=new ReportForm();
		    reportForm.setStartDate(minGenTime);
			reportForm.setEndDate(maxGenTime);
			reportForm.setActNo(actNo);
			reportForm.setMarketingCatalogId(new Long(marketingCatalogId));
			reportForm.setPartnerId(new Long(partnerId));
			reportForm.setTerminalNo(terminalNo);
		    reportForm.setPagination(false);
		    FileInputStream nStream;
		    if(REPORT_TYPE_COUNT.equals(reportType)){
		    	nStream = new FileInputStream(this.countExcelService.generateExcelFile(
						this.couponService.reportCount(reportForm).getResultList(), NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"), NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd")));
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment;filename=" + "reportCountExcel");
		    }else{
		    	nStream = new FileInputStream(this.detailExcelService.generateExcelFile(
						this.couponService.reportDetail(reportForm).getResultList(), NewlandUtil.dataToString(minGenTime, "yyyy-MM-dd"), NewlandUtil.dataToString(maxGenTime, "yyyy-MM-dd")));
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment;filename=" +"reportDetailExcel");
		    }
			
			byte[] b=new byte[1024 * 1024];
			while (nStream.read(b)>0){
				response.getOutputStream().write(b);
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return null;
	}
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return this.marketingCatalogService.findAll();
	}
	@ModelAttribute("partners")
    public Collection<Partner> populatePartners() {
        return this.partnerService.findAll();
    }
}
