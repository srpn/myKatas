package com.train;

import java.util.HashMap;
import java.util.Map;

public class CountingDuplicates {
	  public static int duplicateCount(String text) {
	    int numOfDups = 0;
	    Map<Character, Integer> charMap = new HashMap<>();
	    for(char c: text.toCharArray()) {
	    	Character uChar = Character.toUpperCase(c);
	    	Integer index = charMap.get(uChar); 
	    	if(index != null) {
	    		charMap.put(uChar, 1);
	    	} else {
	    		charMap.put(uChar, 0);
	    	}
	    }
	    numOfDups = (int) charMap.values().stream().mapToInt(Integer::intValue).sum();
	    return numOfDups;
	  }
	}