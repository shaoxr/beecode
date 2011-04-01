package org.tempuri.service.impl;

import org.dom4j.Document;
import org.dom4j.Element;

public class MMSResultParserHelp {

	public static int BalanceParser(String str) throws Exception{
		Document document=null;
		int count=0;
			document = org.dom4j.DocumentHelper.parseText(str);
			Element content=document.getRootElement();
			count=Integer.parseInt(content.getText());
		return count;
	}

}
