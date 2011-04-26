package com.newland.beecode.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.service.BarCodeService;
@Service("barCodeService")
public class BarCodeServiceImpl implements BarCodeService{
	private static final String subfix = "gif";
	@Override
	public void genCode(String str,String fileName) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 200, 200);
			if(fileName==null){
				fileName="a";
			}
			long t = System.currentTimeMillis();
			fileName=fileName+"."+subfix;
			File f = new File("D:/beecode/images/" + fileName);
			if(f.exists()){
				f.delete();
			}
			f.createNewFile();
			
			MatrixToImageWriter.writeToFile(bitMatrix, subfix, f);
		} catch (WriterException e) {
			  e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void genCode(String[] str) {
		for(String content:str){
		   this.genCode(content,null);
		}
		
	}

	@Override
	public void genCode(Coupon coupon) {
		// TODO Auto-generated method stub
		
	}

}
