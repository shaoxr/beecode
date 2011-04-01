package com.newland.beecode.service;

import java.io.File;
import java.util.Collection;

import com.newland.beecode.exception.AppException;


public interface ExcelService {
	
	public File generateExcelFile(Collection dataCollection,String beginTime,String endTime) throws AppException ;

}
