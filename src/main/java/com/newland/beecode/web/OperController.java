package com.newland.beecode.web;

import com.newland.beecode.domain.Oper;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.OperService;
import com.newland.utils.PaginationHelper;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/opers")
@Controller
public class OperController extends BaseController{
    @Autowired
    private OperService operService;    
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Oper oper,@RequestParam(value="roleIds") Long[] roleIds,  Model model,
			HttpServletRequest request) {
		try {
			if(this.operService.findOperByOperName(oper.getOperName())!=null){
				throw new AppException(ErrorsCode.BIZ_OPER_NAME_EXITS,"");
			}
			oper.setGenTime(new Date());
			this.operService.save(oper,roleIds);
		} catch (AppException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/opers/"
				+ encodeUrlPathSegment(oper.getOperNo().toString(), request);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		try {
			model.addAttribute("oper", new Oper());
			model.addAttribute("roleses", this.operService.findRoleAll());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "opers/create";
	}

	@RequestMapping(value = "/{operNo}", method = RequestMethod.GET)
	public String show(@PathVariable("operNo") Long operNo, Model model) {
		try {
			model.addAttribute("oper", this.operService.findById(operNo));
			model.addAttribute("itemId", operNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		
		return "opers/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model model,HttpServletRequest request) {
		
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), "");
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			model.addAttribute("opers", this.operService.findOperEntries((page.intValue() - 1) * size, size));
			int maxPages = PaginationHelper.calcMaxPages(size,this.operService.countOpers());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "opers/list";
		
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(Oper oper,@RequestParam(value="roleIds") Long[] roleIds, Model model,
			HttpServletRequest request) {
		
        try {
			this.operService.update(oper,roleIds);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/opers/"
				+ encodeUrlPathSegment(oper.getOperNo().toString(), request);
	}

	@RequestMapping(value = "/{operNo}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("operNo") Long operNo, Model model) {
		try {	
			model.addAttribute("oper", this.operService.findById(operNo));
			model.addAttribute("roleses", this.operService.findAll());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "opers/update";
	}

	public String changePassword(
			@RequestParam(required = true, value = "newPassword") String newPassword,
			@RequestParam(required = true, value = "oldPassword") String oldPassword,
			@RequestParam(required = true, value = "operNo") Long operNo,
			Model model, HttpServletRequest request) {
		try {
			Oper oper = this.operService.findById(operNo);
			if(oper == null || oper.getOperPwd().equals(oldPassword) == false){
				//TODO throw exec, forward to error page
			}
			oper.setOperPwd(newPassword);
			this.operService.update(oper,null);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:";
	}

	@RequestMapping(value = "/changepasword/{operNo}", params = "form", method = RequestMethod.GET)
	public String changePasswordForm(@PathVariable("operNo") Long operNo,
			Model model) {
		model.addAttribute("operNo", operNo);
		return "oper/changepasword";
	}

	@RequestMapping(value = "/{operNo}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("operNo") Long operNo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model model) {
		try {
			this.operService.delete(operNo);
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? "10" : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/opers?page="
				+ ((page == null) ? "1" : page.toString()) + "&size="
				+ ((size == null) ? "10" : size.toString());
	}


}
