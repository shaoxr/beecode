package com.newland.beecode.domain;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newland.utils.UuidHelper;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Configurable
public class Misc{

	@Test
	public void testPrint(){
		System.out.println("0000000000");
	}
	public static void main(String[] args) throws ParseException {
		String pattern = DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale());
		System.out.println(ISO.DATE);
		
		String str ="asss,asdfsf,sdsd";
		System.err.println(str.split(",").length);
		
		System.err.println("[" + UuidHelper.generateUUID().length() +"]");
		
		Set<String> ids = new HashSet<String>();
		for(int i=0; i< 100000; i++){
			ids.add(UuidHelper.generateUUID());
		}
		System.err.println(ids.size());
		
		Long randomCode = (long)(Math.random()* 100000);
		DecimalFormat format = new DecimalFormat("000000");
		System.err.println(format.format(randomCode));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		System.out.println(sdf.parse(date));
		
	}
}
