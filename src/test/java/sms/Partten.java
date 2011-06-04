package sms;

import java.util.regex.Pattern;

/**
 * @author shaoxr:
 * @version 2011-5-21 下午10:13:19
 * 
 */
public class Partten {
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[0-9[*]]{6}"); 
		System.out.println(pattern.matcher("6666**").matches());
		pattern = Pattern.compile("[0-9]+[*]*");
		System.out.println(pattern.matcher("666666").matches());
	}

}
