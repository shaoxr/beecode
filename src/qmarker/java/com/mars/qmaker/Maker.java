package com.mars.qmaker;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Maker {

	private static final String subfix = "jpeg";
	public static void main(String[] args) throws WriterException, IOException {
		QRCodeWriter writer = new QRCodeWriter();
		
		String contents = "abcdefghijkmnlopasdfasdfasdfasdlfasjdlfjasodfiwpoeurasdlfasjdfasldkjfsdfjasldkfjasldfjsadfljas;dfajsdfsl;djfas;df";
		BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 0, 0);
		
		long t = System.currentTimeMillis();
		String fileName = t + "." + subfix;
		File f = new File("c:/tmp/" + fileName);
		if(f.exists()){
			f.delete();
		}
		f.createNewFile();
		
		MatrixToImageWriter.writeToFile(bitMatrix, subfix, f);
	}
}
