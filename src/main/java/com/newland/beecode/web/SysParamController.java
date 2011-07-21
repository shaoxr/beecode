package com.newland.beecode.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.DictViewFormatter;
import com.newland.beecode.domain.Store;
import com.newland.beecode.domain.SysParam;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.SysParamService;
import com.newland.utils.PaginationHelper;

/**
 * @author shaoxr:
 * @version 2011-7-16 上午11:22:36
 * 
 */
@RequestMapping("/sysparam")
@Controller
public class SysParamController extends BaseController{
	@Resource(name = "dictViewService")
    private DictViewFormatter dictView;
	@Autowired
	private SysParamService sysParamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size,Model model,HttpServletRequest request){
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), "");
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			List<SysParam> sysParams=this.sysParamService.findByEntries((page.intValue() - 1) * size, size);
			model.addAttribute("sysparams",sysParams );
			int maxPages = PaginationHelper.calcMaxPages(size,this.sysParamService.countSysParam());
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			if(maxPages>0){
				model.addAttribute("paramKey", sysParams.get(0).getParamKey());
			}
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "sysparam/list";
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model,@PathVariable("id") Long id){
		try {
			SysParam sysParam=this.sysParamService.findById(id);
			model.addAttribute("sysParam", sysParam);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "sysparam/show";
	}
	@RequestMapping(value="/{id}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model){
		try {
			SysParam sysParam=this.sysParamService.findById(id);
			model.addAttribute("sysParam", sysParam);
			Collection<Dictionary> dic=this.dictView.getSelectModelCollection(sysParam.getParamKey());
			model.addAttribute("dic", dic);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "sysparam/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit(@Valid SysParam sysParam,Model model){	
		try {
            this.sysParamService.update(sysParam);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "redirect:/sysparam";
	}
	@ModelAttribute("sendMgrs")
	public Collection<Dictionary> populateSendMgr() {
            return dictView.getSelectModelCollection("SEND_MGR");
	}

}
