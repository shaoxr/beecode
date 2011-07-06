package com.newland.beecode.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.condition.SelectForm;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.PartnerService;

@RequestMapping("/ajax")
@Controller
public class AjaxRequestController extends BaseController{
    @Autowired
    private MarketingActService marketingActService;
    @Autowired
    private PartnerService partnerService;
	@RequestMapping(value="/marketingAct", method=RequestMethod.GET)
	public void getMarketingAct(@RequestParam Long id,Model model,HttpServletResponse response){
		try {
			List<MarketingAct> list=marketingActService.findByCatalog(id);
			List<SelectForm> list2=new ArrayList<SelectForm>();
			for (MarketingAct marketingAct :list){
				SelectForm sf=new SelectForm();
				sf.setName(marketingAct.getActName());
				sf.setValue(marketingAct.getActNo().toString());
				list2.add(sf);
			}
			
			JSONObject obj=new JSONObject();
			obj.accumulate("items", list2.toArray());
			obj.accumulate("label", "name");
			obj.accumulate("identifier", "value");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			this.getMessage(e);
		}
	}
	@RequestMapping(value="/partner", method=RequestMethod.GET)
	public void getPartner(@RequestParam Long id,Model model,HttpServletResponse response){
		try {
			List<Partner> list=this.partnerService.findByCatalog(id);
			List<SelectForm> list2=new ArrayList<SelectForm>();
			for (Partner partner :list){
				SelectForm sf=new SelectForm();
				sf.setName(partner.getPartnerName());
				sf.setValue(partner.getPartnerNo());
				list2.add(sf);
			}
			
			JSONObject obj=new JSONObject();
			obj.accumulate("items", list2.toArray());
			obj.accumulate("label", "name");
			obj.accumulate("identifier", "value");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			this.getMessage(e);
		}
	}

}
