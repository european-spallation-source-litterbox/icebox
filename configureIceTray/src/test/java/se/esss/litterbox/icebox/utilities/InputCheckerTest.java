package se.esss.litterbox.icebox.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputCheckerTest {
	
	private String validString = "abcdefg1";

	@Test
	public void testNameChecker() {
		assertFalse("Empty strings not allowed", InputChecker.nameChecker(""));
		assertFalse("Input strings shouldn't start with a digit", 
				InputChecker.nameChecker("1edasf"));
		assertFalse("Input strings shouldn't contain whitespace", 
				InputChecker.nameChecker("abc def"));
		assertTrue(validString + " should be valid", 
				InputChecker.nameChecker(validString));
	}
}
