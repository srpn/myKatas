package com.train;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Needle {

	public static void main(String[] args) {
		Object[] haystack1 = {"3", "123124234", null, "needle", "world", "hay", 2, "3", true, false};
	    System.out.println(findNeedle(haystack1));
	}
	
	  public static String findNeedle(Object[] haystack) {
		  OptionalInt index = OptionalInt.empty();
		    if(haystack != null) {
		      index = IntStream.range(0, haystack.length)
		    		  .parallel()
		    		  .filter(i -> "needle".equals(haystack[i]))
		    		  .findFirst();
		    }
		    return "found the needle at position "+index.orElse(-1);
		  }

}
