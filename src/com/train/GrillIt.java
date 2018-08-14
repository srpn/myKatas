package com.train;

public class GrillIt {
    public static String grille(String message, int code) {
    	char[] codeChars = Integer.toBinaryString(code).toCharArray();
    	char[] msgChars = message.toCharArray();
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i=codeChars.length-1, count=0; i>=0; i--,count++) {
    		if(codeChars[i] == '1' && msgChars.length-1-count >= 0)
    			sb.append(msgChars[msgChars.length-1-count]);
    	}
    	
        return sb.reverse().toString();
    }

}
