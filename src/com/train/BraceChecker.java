package com.train;

import java.util.Stack;

public class BraceChecker {

	public static void main(String[] args) {
		System.out.println(isValid("[(])"));
	}

	public static boolean isValid(String braces) { 
	    String b = braces;
	    System.out.println(braces);   
	    for(int i=0;i<braces.length()/2;i++)
	    {
	      b = b.replaceAll("\\(\\)", "");
	      b = b.replaceAll("\\[\\]", "");
	      b = b.replaceAll("\\{\\}", "");
	      if(b.length() == 0)
	        return true;
	    }
	    return false;
	  }
	
	public static boolean isValid_Old(String braces) {
		boolean isValid = false;
		int sq =0, reg=0, cur=0;
		char prev = ' ';
		Stack<Character> prevs = new Stack<>();
		prevs.push(' ');
		for(char c: braces.toCharArray()) {
			prev = prevs.peek();
			switch (c) {
			case '(':
				reg++;
				prevs.push(c);
				break;
			case ')':
				if(reg <= 0 || prev != '(') {
					return false;
				}
				prevs.pop();
				reg--;
				break;
			case '[':
				sq++;
				prevs.push(c);
				break;
			case ']':
				if(sq <= 0 || prev != '[') {
					return false;
				}
				prevs.pop();
				sq--;
				break;
			case '{':
				cur++;
				prevs.push(c);
				break;
			case '}':
				if(cur<=0 || prev != '{') {
					return false;
				}
				prevs.pop();
				cur--;
				break;
			}
		}
		if(sq==0 && reg==0 && cur==0) {
			isValid = true;
		}
		return isValid;
	}

}
