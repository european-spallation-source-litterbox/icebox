package se.esss.litterbox.icebox.icetray.icecube;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.exceptions.SignalException;

public class WriteSignalTest {
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	WriteSignal testWriteSignal;
	String sigName = "testName";
	
	@Before
	public void setUp() throws SignalException {
		testWriteSignal = new WriteSignal(sigName);
	}

	@Test
	public void testGetPvExt() {
		assertEquals("PV extension should be \":set\"", 
				":set", 
				testWriteSignal.getPvExt());
	}

	@Test
	public void testIsRead() {
		assertFalse("isread() should be true for ReadSignals", testWriteSignal.isRead());
	}

	@Test
	public void testGetRecordType() {
		assertEquals("ReadSignals should have recordtype \"ao\"", 
				"ao", 
				testWriteSignal.getRecordType());
	}

	@SuppressWarnings("unused")
	@Test
	public void testEmptyName() throws SignalException, IceCubeException {
		thrown.expect(SignalException.class);
		ReadSignal readSignal = new ReadSignal("");
	}

	@SuppressWarnings("unused")
	@Test
	public void testFirstCharDigitName() throws SignalException, IceCubeException {
		thrown.expect(SignalException.class);
		ReadSignal readSignal = new ReadSignal("1asd");
	}

	@SuppressWarnings("unused")
	@Test
	public void testWhiteSpaceInName() throws SignalException, IceCubeException {
		thrown.expect(SignalException.class);
		ReadSignal readSignal = new ReadSignal("a signal");
	}

	@Test
	public void testIsWrite() {
		assertTrue("iswrite() should be false for ReadSignals", testWriteSignal.isWrite());
	}

	@Test
	public void testGetName() {
		assertEquals(sigName, testWriteSignal.getName());
	}

	@Test
	public void testGetPvName() {
		assertEquals("PVname should be name plus :set", sigName+":set", testWriteSignal.getPvName());
	}

}
