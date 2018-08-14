package com.train;

import java.util.ArrayList;
import java.util.List;

public class NoReason {

	public static void main(String[] args) {
		List<String> strs = new ArrayList<>();
		strs.add("one");
		strs.add("");
		strs.add(null);
		strs.add(" ,");
		strs.add("\"");
		strs.add("last");
		
		System.out.println(strs);
	}

}
