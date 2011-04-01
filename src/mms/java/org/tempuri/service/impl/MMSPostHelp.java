package org.tempuri.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URL;

public class MMSPostHelp {
	
	public static String HttpPost(String urlStr,String param)throws Exception{
		StringBuffer sbr=new StringBuffer();;
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());    
            out.write(param);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
            	sbr.append(line);
            }
        return sbr.toString();
	}

}
