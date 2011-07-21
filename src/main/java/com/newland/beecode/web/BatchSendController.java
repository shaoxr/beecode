package com.newland.beecode.web;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.SendListService;
import com.newland.beecode.service.SendService;
import com.newland.utils.PaginationHelper;
import com.newland.beecode.service.RespStatusService;
import com.newland.beecode.task.service.SendInvokeService;

/**
 * @author shaoxr:
 * @version 2011-5-15 下午03:32:33
 * 
 */
@RequestMapping("/batchsend")
@Controller
public class BatchSendController extends BaseController{
	@Autowired
	private FileService fileService;
	@Autowired
	private SendListService sendListService;
	@Autowired
	private RespStatusService respStatusService;
	@Resource(name="smsFetch2SendInvokeService")
	private SendInvokeService smsFetch2SendInvokeService;
	@Autowired
	private SendService sendService;
	
	@RequestMapping(value={"mmsSendByzip"},method = RequestMethod.POST)
    public String mmsSendByzip(@RequestParam(value = "file", required = true) MultipartFile file,
    		@RequestParam(value = "msType", required = true) Integer msType,
    		Model model){
		  try {
			  this.sendListService.send(file,msType);
			  if(msType.equals(SendList.MS_TYPE_MMS)){
				  model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_MMS_SUBMIT_SUCCESS);
			  }else{
				  model.addAttribute(ErrorsCode.MESSAGE, ErrorsCode.BIZ_SMS_SUBMIT_SUCCESS);
			  }
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "batchsend/tip";
    	
    }
	@RequestMapping(value={"mmsSendForm"},params="form",method = RequestMethod.GET)
    public String mmsSendByzipForm(
    		Model model){
		return "batchsend/mmsSendForm";
    	
    }
	@RequestMapping(value={"smsSendForm"},params="form",method = RequestMethod.GET)
    public String smsSendByzipForm(
    		Model model){
		return "batchsend/smsSendForm";
    	
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		SendList sendList=this.sendListService.findById(id);
		model.addAttribute("sendList", sendList);
		return "batchsend/show";
	}
	@RequestMapping(value = "sendover", method = RequestMethod.POST)
	public String sendOver(Model model,@RequestParam("id") Long  id){
		this.sendListService.sendOver(id);
		return "redirect:/batchsend/"+id;
	}
	
	@RequestMapping(value={"smsSendByzip"},method = RequestMethod.POST)
	 public String smsSendByzip(@RequestParam(value = "file", required = true) MultipartFile file,
	    		Model model){
			  try {
				String dirName=this.fileService.extractSms(file);
				List<Coupon> coupons=this.fileService.getCoupon(dirName);
				MarketingAct act=this.fileService.getActFile(dirName, SendList.MS_TYPE_SMS);
				SendList sendList=new SendList();
				sendList.setTotalCount(new Long(coupons.size()));
				sendList.setSubmitTime(new Date());
				sendList.setSendStatus(SendList.STATUS_SENDING);
				sendList.setMsType(SendList.MS_TYPE_SMS);
				sendList.setActNo(act.getActNo());
				sendList.setActName(act.getActName());
				sendList=this.sendListService.saveOrUpdate(sendList);
				this.sendService.send(coupons, act, this.smsFetch2SendInvokeService, sendList.getId(), dirName);
				
			} catch (Exception e) {
				model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
				logger.error("", e);
				return "prompt";
			}
			return "batchsend/tip";
	    	
	    }
	@RequestMapping(value={"list"},method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, Model model,HttpServletRequest request){
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), "");
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			model.addAttribute("mmsSendLists", this.sendListService.findByLimit((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size,this.sendListService.countAll());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "batchsend/list";
	}
	@RequestMapping(value = "/mmsStatusDownload/{mmsSendListId}", method = RequestMethod.GET)
	public String excelExport(@PathVariable("mmsSendListId") Long sendListId,
			Model model,HttpServletRequest request, HttpServletResponse response){
		FileInputStream nStream;
		   
		try {
			 SendList sendList=this.sendListService.findById(sendListId);
			    if(sendList.getSendStatus().equals(SendList.STATUS_SENDING)){
			    	throw new AppException(ErrorsCode.BIZ_SEND_LIST_DO_NOT_DOWNLOAD,"");
			    }
			nStream=this.fileService.getRespStatus(sendList, this.respStatusService.findBySendListId(sendListId));
			response.setContentType("application/OCTET-STREAM");
			response.addHeader("Content-Disposition", "attachment;filename=" +"respStatus.txt");
			int i;
			while ((i=nStream.read())!=-1){
				response.getOutputStream().write(i);
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		} 
		return null;
	}
	@RequestMapping(value={"sendCount"},method = RequestMethod.GET)
    public String sendCount(
    		Model model){
		try {
			model.addAttribute("MsStatus", this.sendListService.findSendCount());
			
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			logger.error("", e);
			return "prompt";
		}
		
		return "batchsend/sendCount";
    	
    }

}
