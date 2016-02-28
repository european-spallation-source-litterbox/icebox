package se.esss.litterbox.icebox.icetray.icecube;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.esss.litterbox.icebox.exceptions.SignalException;

public class ReadSignalTest {
	
	ReadSignal testReadSignal;
	
	@Before
	public void setUp() throws SignalException {
		testReadSignal = new ReadSignal("testName");
	}

	@Test
	public void testWriteEPICSRecord() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteEPICSProtoFunc() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildJsonRep() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJsonRep() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPvExt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsRead() {
		assertTrue("isread() should be true for ReadSignals", testReadSignal.isRead());
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadSignalJsonObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadSignalStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadSignalString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScanRate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecordType() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignalJsonObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignalString() {
		fail("Not yet implemented");
	}

	@Test
	public void testEpicsDBFieldString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckName() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsWrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPvName() {
		fail("Not yet implemented");
	}

}
