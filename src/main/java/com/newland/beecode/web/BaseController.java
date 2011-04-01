package com.newland.beecode.web;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;

public class BaseController {
	
	protected String getMessage(Exception e){
		String errCode=ErrorsCode.SYSTEM_ERR;
		if(e instanceof AppException){
			errCode= ((AppException)e).getCode();
		}
		return errCode;
		
	}

}
