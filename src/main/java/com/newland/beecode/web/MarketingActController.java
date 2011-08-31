package com.newland.beecode.web;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.DictViewFormatter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.ExcelException;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.CustomerService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.MarketingCatalogService;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.beecode.service.PartnerService;
import com.newland.beecode.service.TransactionalService;
import com.newland.utils.PaginationHelper;
import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/marketingacts")
@Controller
public class MarketingActController extends BaseController{
    
    @Resource(name = "dictViewService")
    private DictViewFormatter dictView;
    
	@Autowired
	private MarketingActService marketingActService;
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private PartnerCatalogService partnerCatalogService;
	@Autowired
	private MarketingCatalogService marketingCatalogService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TransactionalService transactionalService;

	@Autowired
	private FileService fileService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(
			@RequestParam(value = "partnerFile", required = true) MultipartFile partnerFile,
			@Valid MarketingAct marketingAct, BindingResult result,
			Model model, HttpServletRequest request) throws IOException {
		try {
			marketingActService.createMarketingAct(marketingAct, partnerFile);
		} catch (Exception e) {
			if(e instanceof ExcelException){
				/**
				 * excel检查结果，直接获取message
				 */
				model.addAttribute(ErrorsCode.MESSAGE, e.getMessage());
				return "promptExcel";
			}
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/marketingacts/appendMmsContent/"+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
				request)+"?form";
	}
	@RequestMapping(value="appendMmsContent/{actNo}",params="form", method = RequestMethod.GET)
	public String appendMmsContentForm(@PathVariable("actNo") Long actNo,Model model,
			HttpServletRequest request){
		MarketingAct marketingAct=this.marketingActService.findByActNo(actNo);
		model.addAttribute("marketingAct",marketingAct);
		return "marketingacts/appendmmscontent";
	}
	@RequestMapping(value="appendMmsContent",method = RequestMethod.POST)
	public String appendMmsContent(@Valid MarketingAct marketingAct,Model model, HttpServletRequest request){
		try {
			this.marketingActService.appendMmsContent(marketingAct);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/marketingacts/appendCustomer/"+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
				request)+"?form";
	}
	@RequestMapping(value="appendMmsContentPre",method = RequestMethod.POST)
	public String appendMmsContentPre(@Valid MarketingAct marketingAct,Model model, HttpServletRequest request){
		
		return "redirect:/marketingacts/"+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
				request)+"?form";
		
	}
	@RequestMapping(value="updatePartner/{actNo}", method = RequestMethod.GET)
	public String updatePartnerForm(@PathVariable("actNo") Long actNo,Model model){
		try {
			MarketingAct marketingAct=this.marketingActService.updatePartnerForm(actNo);
			model.addAttribute("marketingAct",marketingAct);
		} catch (AppException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/updatepartner";
	}
	@RequestMapping(value="updatePartner", method = RequestMethod.POST)
	public String updatePartner(@RequestParam("actNo") Long actNo,
			@RequestParam(value = "partnerFile", required = true) MultipartFile partnerFile,
			Model model, HttpServletRequest request){
		try {
			MarketingAct marketingAct=this.marketingActService.findByActNo(actNo);
			model.addAttribute("marketingAct",marketingAct);
			this.marketingActService.updatePartner(actNo, partnerFile);
		} catch (Exception e) {
			if(e instanceof ExcelException){
				/**
				 * excel检查结果，直接获取message
				 */
				model.addAttribute(ErrorsCode.MESSAGE, e.getMessage());
				return "promptExcel";
			}
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/marketingacts/updatePartner/"+actNo;
	}
	@RequestMapping(value="appendCustomer/{actNo}",params="form", method = RequestMethod.GET)
	public String appendCustomerForm(@PathVariable("actNo") Long actNo,Model model){
		try {
			MarketingAct marketingAct=this.marketingActService.appendCustomerForm(actNo);
			model.addAttribute("marketingAct",marketingAct);
			model.addAttribute("customersChecked",marketingAct.getCheckedCustomers());
			model.addAttribute("customersUn",marketingAct.getUnCheckCustomers());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/appendcustomer";
	}
	
	@RequestMapping(value="appendCustomer", method = RequestMethod.POST)
	public String appendCustomer(@RequestParam("actNo") Long actNo,
			@RequestParam(value = "customerFile", required = true) MultipartFile customerFile,
			Model model, HttpServletRequest request){
		try {
			String bizNo = this.marketingActService.findByActNo(actNo).getBizNo();
			this.transactionalService.append(actNo, customerFile, bizNo);
		} catch (Exception e) {
			    if(e instanceof ExcelException){
			    	/**
			    	 * excel检查结果，直接获取message
			    	 */
			    	model.addAttribute(ErrorsCode.MESSAGE, e.getMessage());
			    	try {
						MarketingAct marketingAct=this.marketingActService.appendCustomerForm(actNo);
						model.addAttribute("marketingAct",marketingAct);
						model.addAttribute("customersChecked",marketingAct.getCheckedCustomers());
						model.addAttribute("customersUn",marketingAct.getUnCheckCustomers());
					} catch (Exception e1) {
						model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				    	return "prompt";
					}
			    	return "marketingacts/appendcustomer";
			    }else{
			    	model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			    	return "prompt";
			    }
		}
		return "redirect:/marketingacts/appendCustomer/"+ encodeUrlPathSegment(actNo.toString(),
				request)+"?form";
	}
	@RequestMapping(value="submit2check", method = RequestMethod.POST)
	public String submit2check(@RequestParam("actNo") Long actNo,Model model, HttpServletRequest request){
		try {
			this.marketingActService.submit2check(actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/marketingacts/"
		+ encodeUrlPathSegment(actNo.toString(),
				request);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{newName}", method = RequestMethod.DELETE)
	public String deleteCustemer(@PathVariable("newName") String newName,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			Model model,HttpServletRequest request)  {
		Long actNo = this.customerService.findByNewName(newName).getActNo();
		Long id = this.customerService.findByNewName(newName).getId();
		try {			
			this.customerService.delete(id);			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:appendCustomer/"+ encodeUrlPathSegment(actNo.toString(),
				request)+"?form";
	}   
	/**
	 * 
	 */
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		try {
			model.addAttribute("marketingAct", new MarketingAct());
			model.addAttribute("checkCards", dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/create";
	}

	@RequestMapping(value = "/{actNo}", method = RequestMethod.GET)
	public String show(@PathVariable("actNo") Long actNo, Model model) {
		try {
			addDateTimeFormatPatterns(model);
			/*MarketingAct act = this.marketingActService.findByActNo(actNo);
			act.setMsStatus(this.couponService.findMSStatus(actNo));
			if (act.getActStatus() > MarketingAct.STATUS_BEFORE_GIVE) {
				MarketingActSummary marketingSummary = this.marketingActService.marketingSummary(actNo);
				act.setSummary(marketingSummary);
				model.addAttribute("statistics",true);
			}
			this.customerService.genCustomerList(act);*/
			MarketingAct act=this.marketingActService.show(actNo);
			model.addAttribute("customersChecked",act.getCheckedCustomers());
			model.addAttribute("customersUn",act.getUnCheckCustomers());
			model.addAttribute("marketingact", act);
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/show";
	}
	@RequestMapping(value="/{actNo}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("actNo") Long actNo, Model model){	
		try {
			MarketingAct act = this.marketingActService.find2Update(actNo);
			model.addAttribute("marketingAct", act);
			model.addAttribute("checkCards", dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit( MarketingAct marketingAct,
			@RequestParam(value = "partnerIds", required = false) Long[] partners,
			Model model,
			HttpServletRequest request){
		try{
			this.marketingActService.update(marketingAct,partners);
		}catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		//return "redirect:marketingacts/"+marketingAct.getActNo();
		return "redirect:/marketingacts/appendMmsContent/"+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
				request)+"?form";
	}
	@RequestMapping(value="extend",method=RequestMethod.PUT)
	public String extend( MarketingAct marketingAct,
			Model model,
			HttpServletRequest request){
		try{
			this.marketingActService.extendEndDate(marketingAct);
		}catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		//return "redirect:marketingacts/"+marketingAct.getActNo();
		return "redirect:/marketingacts/"+marketingAct.getActNo();
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
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return "redirect:/marketingacts?"+query;
	}
	@RequestMapping(value = "/shutdown/{actNo}", method = RequestMethod.POST)
	public String shutdown(@PathVariable("actNo") Long actNo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			Model model,HttpServletRequest request)  {
		try {
			marketingActService.shutdownMarketingAct(actNo);
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? PaginationHelper.PAGE_SIZE : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return "redirect:/marketingacts?"+query;
	}
	@RequestMapping(value = "reopen", method = RequestMethod.POST)
	public String reopen(@RequestParam(value = "actNo", required = false) Long actNo,
			Model model,HttpServletRequest request)  {
		try {
			marketingActService.reOpenMarketingAct(actNo);
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return "redirect:/marketingacts/"+actNo;
	}
	@RequestMapping(value = "reopentosend", method = RequestMethod.POST)
	public String reOpenToSend(@RequestParam(value = "actNo", required = false) Long actNo,
			Model model,HttpServletRequest request)  {
		try {
			marketingActService.reOpenToSendMarketingAct(actNo);
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		
		return "redirect:/marketingacts/"+actNo;
	}
    
	@RequestMapping(value = "/find/check", method = RequestMethod.GET)
	public String findMarketingAct4Check(Model model,
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
					List<MarketingAct> marketingActs=this.marketingActService.findMarketingActEntries2Check((page-1)*size, size);
					model.addAttribute("marketingacts",marketingActs);
                    int maxPages = PaginationHelper.calcMaxPages(size, this.marketingActService.count2Check());
                    model.addAttribute("maxPages",maxPages);
                    model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
                    model.addAttribute(PaginationHelper.PARAM_PAGE, page);
                    model.addAttribute(PaginationHelper.PARAM_SIZE, size);
				} catch (NumberFormatException e) {
					model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
					return "prompt";
				}
		return "marketingacts/list/check";
	}

	@RequestMapping(value = "/check/{actNo}", method = RequestMethod.GET)
	public String checkMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		try {
			addDateTimeFormatPatterns(model);
			MarketingAct act=this.marketingActService.show(actNo);
			model.addAttribute("marketingAct",act);
			model.addAttribute("customersChecked",act.getCheckedCustomers());
			model.addAttribute("customersUn",act.getUnCheckCustomers());
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/check/form";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkMarketingAct(
			@RequestParam(value = "actNo", required = true) Long actNo,
			@RequestParam(value = "actStatus", required = true) Integer actStatus,
			Model model) {
		try {
			long[] res=this.transactionalService.checkMarketingAct(actNo, actStatus);
			model.addAttribute("actStatus", actStatus);
			model.addAttribute("success", res[0]);	
			model.addAttribute("fail", res[1]);	
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/checkTip";
	}
	
	@RequestMapping(value = "/actInfoDownload/{actNo}", method = RequestMethod.GET)
	public String exportDoc(@PathVariable("actNo") Long actNo,
			Model model,HttpServletRequest request, HttpServletResponse response){
		FileInputStream fis;
		try {
			
			MarketingAct act = this.marketingActService.findByActNo(actNo);
			fis = this.fileService.getActInfomation(act);
			response.setContentType("application/msword");
			response.addHeader("Content-Disposition", "attachment;filename=" +"actinfo.doc");
			int i;
			while ((i=fis.read())!=-1){
				response.getOutputStream().write(i);
			}
			fis.close();
			response.getOutputStream().close();
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		} 
		return null;
	}
	/**
	 * 组合条件查询，至少输入一个条
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
			@RequestParam("actStatus") Integer actStatus, 
			@RequestParam("marketingCatalog") String marketingCatalog,
			@RequestParam("actNo") Long actNo,Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request) {
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), request.getQueryString());
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
			MarketingActCondition mac=new MarketingActCondition();
			mac.setMarketingCatalogId(new Long(marketingCatalog)); 
			mac.setBizNo(bizNo);
			mac.setActStatus(actStatus);
			mac.setActNo(actNo);
			List<MarketingAct> marketingActs=this.marketingActService.findByCondition(mac,(page-1)*size,size);
			int maxPages = PaginationHelper.calcMaxPages(size, this.marketingActService.countByCondition(mac));
			model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			model.addAttribute("marketingacts",marketingActs);
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/findMarketingActsByCondition";
	}

	@RequestMapping(params = { "find=ByCondition", "form" }, method = RequestMethod.GET)
	public String findMarketingActsByConditionForm(Model model) {
		addDateTimeFormatPatterns(model);
		
		return "marketingacts/findMarketingActsByCondition";
	}
	
	@ModelAttribute("partners")
	public Collection<Partner> populatePartners() {
		return this.partnerService.findAll();
	}
	@ModelAttribute("partnerCatalogs")
	public Collection<PartnerCatalog> populatePartnerCatalogs() {
		return this.partnerCatalogService.findAll();
	}
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return this.marketingCatalogService.findAll();
	}
	@ModelAttribute("marketingsBlank")
	public Collection<MarketingAct> populateMarketingsBlank() {
		return new ArrayList<MarketingAct>();
	}
	@ModelAttribute("bizTypes")
	public Collection<Dictionary> populateBizTypes() {
            return dictView.getSelectModelCollection(MarketingAct.BUSINESS_TYPE);
	}
	@ModelAttribute("marketingstatusList")
	public Collection<Dictionary> populateMarketingstatusList() {
            return dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME);
	}
	@ModelAttribute("valueTypes")
	public Collection<Dictionary> populateValueTypes() {
            return dictView.getSelectModelCollection("IMPORT_TYPE");
	}
	@ModelAttribute("selfCards")
	public Collection<Dictionary> populateSelfCards() {
            return dictView.getSelectModelCollection("SELF_CARD");
	}
	@ModelAttribute("templateTypes")
	public Collection<Dictionary> populateTemplateType() {
            return dictView.getSelectModelCollection("TEMPLATE_TYPE");
	}

}
