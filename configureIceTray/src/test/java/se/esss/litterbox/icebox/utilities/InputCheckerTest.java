package se.esss.litterbox.icebox.utilities;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import se.esss.litterbox.icebox.exceptions.SignalException;
import se.esss.litterbox.icebox.icetray.icecube.ReadSignal;
import se.esss.litterbox.icebox.icetray.icecube.Signal;

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
	
	@Test
	public void testSignalListChecker() throws SignalException {
		ArrayList<Signal> signalList1 = new ArrayList<Signal>();
		ArrayList<Signal> signalList2 = new ArrayList<Signal>();
		
		signalList1.add(new ReadSignal("sig1"));
		signalList1.add(new ReadSignal("sig2"));
		assertTrue(InputChecker.signalListChecker(signalList1));
		
		signalList2.add(new ReadSignal("sig1"));
		signalList2.add(new ReadSignal("sig1"));
		assertFalse(InputChecker.signalListChecker(signalList2));
	}
}
