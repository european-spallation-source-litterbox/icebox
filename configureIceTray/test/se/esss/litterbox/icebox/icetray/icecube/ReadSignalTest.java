package se.esss.litterbox.icebox.icetray.icecube;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.esss.litterbox.icebox.exceptions.SignalException;

public class ReadSignalTest {
	
	ReadSignal testReadSignal;
	String sigName = "testName";
	
	@Before
	public void setUp() throws SignalException {
		testReadSignal = new ReadSignal(sigName);
	}

	@Test
	@Ignore
	public void testWriteEPICSRecord() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testWriteEPICSProtoFunc() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testBuildJsonRep() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetJsonRep() {
		fail("Not yet implemented");
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
	@Ignore
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testReadSignalJsonObject() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testReadSignalStringString() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testReadSignalString() {
		fail("Not yet implemented");
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

	@Test
	@Ignore
	public void testSignalJsonObject() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSignalString() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testEpicsDBFieldString() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testCheckName() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsWrite() {
		assertFalse("iswrite() should be false for ReadSignals", testReadSignal.isWrite());
	}

	@Test
	@Ignore
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		assertEquals(sigName, testReadSignal.getName());
	}

	@Test
	@Ignore
	public void testGetPvName() {
		fail("Not yet implemented");
	}

}
