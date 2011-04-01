package com.newland.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PaginationHelper {

	public static final String PARAM_PAGE = "page";

	public static final String PARAM_SIZE = "size";

	public static final String PARAM_QUERY_STRING = "query";
	
	public static final String PAGE_SIZE="10";

	public static final int calcMaxPages(Integer size, long counts) {
		float nrOfPages = (float) counts / size;
		int maxPages = (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
				: nrOfPages);
		return maxPages;
	}
	
	public static final Map<String, String> makeParameters(Map params, String querys) {
		StringBuffer buf = new StringBuffer();
		String page = params.get(PARAM_PAGE) == null ? "1" : ((String[])params.get(PARAM_PAGE))[0];
		String size = params.get(PARAM_SIZE) == null ? PAGE_SIZE : ((String[])params.get(PARAM_SIZE))[0];
		querys=querys==null?"":querys;
		querys = cleanQuerys(PARAM_PAGE, querys);
		querys = cleanQuerys(PARAM_SIZE, querys);
		querys = "&" + querys;

		Map<String, String> ret = new HashMap<String, String>();
		ret.put(PARAM_QUERY_STRING, querys);
		ret.put(PARAM_PAGE, page);
		ret.put(PARAM_SIZE, size);
		return ret;
	}

	private static String cleanQuerys(String param, String querys) {
		int startPosition = querys.indexOf(param);
		int endPosition = querys.indexOf("&", startPosition);
		if (startPosition < 0) {
			return querys;
		}
		if (endPosition > -1) {
			if (startPosition > 0) {
				querys = querys.substring(0, startPosition - 1)
						+ querys.substring(endPosition);
			} else {
				querys = querys.substring(endPosition + 1);
			}
		} else {
			querys = querys.substring(0, startPosition - 1);
		}
		return querys;
	}

	public static void main(String[] args) {
		String query = cleanQuerys(PARAM_PAGE, "page=1&find=ok&hi=abc&abc=ok");
		query = cleanQuerys(PARAM_SIZE, query);
		System.err.println(query);
		query = cleanQuerys(PARAM_PAGE, "find=ok&hi=abc");
		query = cleanQuerys(PARAM_SIZE, query);
		System.err.println(query);
		query = cleanQuerys(PARAM_PAGE, "find=ok&hi=abc&page=11&size=0&abc=ok");
		query = cleanQuerys(PARAM_SIZE, query);
		System.err.println(query);
	}
}
