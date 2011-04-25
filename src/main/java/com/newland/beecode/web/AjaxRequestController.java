package com.newland.beecode.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.condition.SelectForm;

@RequestMapping("/ajax")
@Controller
public class AjaxRequestController {
    
    private 
    
	@RequestMapping(value="/marketingAct", method=RequestMethod.GET)
	public void getMarketingAct(@RequestParam String id,Model model,HttpServletResponse response){
		List<MarketingAct> list=MarketingAct.findByCatalog(new Long(id));
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
		try {
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/partner", method=RequestMethod.GET)
	public void getPartner(@RequestParam String id,Model model,HttpServletResponse response){
		List<Partner> list=Partner.findByCatalog(new Long(id));
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
		try {
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
