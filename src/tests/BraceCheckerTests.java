package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.train.BraceChecker;

public class BraceCheckerTests {

	private BraceChecker checker = new BraceChecker();

	@Test
	public void testValid() {
		assertEquals(true, checker.isValid("()"));
		assertEquals(true, checker.isValid("()"));
		assertEquals(true, checker.isValid("[]"));
		assertEquals(true, checker.isValid("{}"));
		assertEquals(true, checker.isValid("(){}[]"));
		assertEquals(true, checker.isValid("([{}])"));
	}

	@Test
	public void testInvalid() {
		assertEquals(false, checker.isValid("[(])"));
		assertEquals(false, checker.isValid("[(){{]"));
	}

}