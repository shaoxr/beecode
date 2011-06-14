package com.newland.beecode.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.exception.AppRTException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.BarCodeService;
import com.newland.beecode.service.FileService;
@Service("barCodeService")
public class BarCodeServiceImpl implements BarCodeService{
	private static final String subfix = "gif";
	@Autowired
	private FileService fileService;
	private Log logger = LogFactory.getLog(this.getClass());
	@Override
	public void genCode(String str,String fileName)throws AppRTException {
			try {
				QRCodeWriter writer = new QRCodeWriter();
				BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 200,200);
				if(fileName==null){
					fileName="a";
				}
				fileName=fileName+"."+subfix;
				File f = new File(this.fileService.getImagePath()+fileName);
				if(f.exists()){
					f.delete();
				}
				f.createNewFile();
				BufferedImage imd=ImageIO.read(new File("c:/nonghang.png"));
				MatrixToImageWriter.writeToFile(bitMatrix, subfix, f,imd);
			} catch (WriterException e) {
				logger.error("WriterException", e);
				throw new AppRTException(ErrorsCode.BIZ_BARCODE_GEN_ERROR,"",e);
			} catch (IOException e) {
				logger.error("IOException", e);
				throw new AppRTException(ErrorsCode.BIZ_BARCODE_GEN_ERROR,"",e);
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
