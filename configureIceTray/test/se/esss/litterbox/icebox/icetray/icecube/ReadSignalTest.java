package se.esss.litterbox.icebox.icetray.icecube;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.exceptions.SignalException;

public class ReadSignalTest {
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	ReadSignal testReadSignal;
	String sigName = "testName";
	
	@Before
	public void setUp() throws SignalException {
		testReadSignal = new ReadSignal(sigName);
	}

	@Test
	public void testGetPvExt() {
		assertEquals("PV extension should be \":get\"", 
				":get", 
				testReadSignal.getPvExt());
	}

	@Test
	public void testIsRead() {
		assertTrue("isread() should be true for ReadSignals", testReadSignal.isRead());
	}

	@Test
	public void testGetScanRate() {
		assertEquals("scanRate should be \".1 second\"", 
				".1 second", 
				testReadSignal.getScanRate());
	}

	@Test
	public void testGetRecordType() {
		assertEquals("ReadSignals should have recordtype \"ai\"", 
				"ai", 
				testReadSignal.getRecordType());
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
		assertFalse("iswrite() should be false for ReadSignals", testReadSignal.isWrite());
	}

	@Test
	public void testGetName() {
		assertEquals(sigName, testReadSignal.getName());
	}

	@Test
	public void testGetPvName() {
		assertEquals("PVname should be name plus :get", sigName+":get", testReadSignal.getPvName());
	}

}
