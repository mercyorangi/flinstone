package com.wildlife.kws.helper;

public class URLEncode {
	
	// encodes URL
	   public static String encodeURL(String url) {
//	        throw new UnsupportedOperationException("Not yet implemented");

	        url= replace(url, 'à', "%E0");
	        url = replace(url, 'è', "%E8");
	        url = replace(url, 'é', "%E9");
	        url = replace(url, 'ì', "%EC");
	        url = replace(url, 'ò', "%F2");
	        url = replace(url, 'ù', "%F9");
	        url = replace(url, '$', "%24");
	        url = replace(url, '#', "%23");
	        url = replace(url, '£', "%A3");
	        url = replace(url, '@', "%40");
	        url = replace(url, '\'', "%27");
	        url = replace(url, ' ', "%20");

	        return url;
	    } 
	   
	// replaces string with desired character
	    private static String replace(String source, char oldChar, String dest) {
	        String str = "";
	        for (int i = 0; i < source.length(); i++) {
	            if (source.charAt(i) != oldChar) {
	                str += source.charAt(i);
	            } else {
	                str += dest;
	            }
	        }
	        return str;
	    }

}
