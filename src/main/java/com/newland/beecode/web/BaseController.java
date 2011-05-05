package com.newland.beecode.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.impl.MarketingActServiceImpl;

public class BaseController {
	protected Log logger = LogFactory.getLog(this.getClass());
	protected String getMessage(Exception e){
		String errCode=ErrorsCode.SYSTEM_ERR;
		if(e instanceof AppException){
			errCode= ((AppException)e).getCode();
		}
		return errCode;
		
	}
	protected String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
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
	protected void addDateTimeFormatPatterns(Model model) {
		model.addAttribute("date_format", "yyyy-MM-dd");
		model.addAttribute("datetime_format", "yyyy-MM-dd HH:mm");
	}

}
