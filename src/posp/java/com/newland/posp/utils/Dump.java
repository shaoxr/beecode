package com.newland.posp.utils;

/**
 * 
 * @author szshen
 *
 */
public class Dump {
    private static final byte[] highDigits;

    private static final byte[] lowDigits;

    // initialize lookup tables
    static {
        final byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];
        }

        highDigits = high;
        lowDigits = low;
    }

	public static String getHexDump(byte[] bytes) {
	
		if (bytes == null || bytes.length == 0) 
			return "empty";
		
		StringBuffer out = new StringBuffer((bytes.length * 3) - 1);

		int byteValue = bytes[0] & 0xFF;
		out.append((char) highDigits[byteValue]);
		out.append((char) lowDigits[byteValue]);
    
		for (int i=1; i<bytes.length; i++) {
			out.append(' ');
	        byteValue = bytes[i] & 0xFF;
	        out.append((char) highDigits[byteValue]);
	        out.append((char) lowDigits[byteValue]);
		}
		System.out.println(out.length());
		return out.toString();
	}
}
