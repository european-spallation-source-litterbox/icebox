package se.esss.litterbox.icebox.icetray;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.exceptions.SignalException;
import se.esss.litterbox.icebox.icetray.icecube.ReadSignal;
import se.esss.litterbox.icebox.icetray.icecube.Signal;
import se.esss.litterbox.icebox.icetray.icecube.WriteSignal;

public class IceCubeTest {
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	public String iceCubeName = "testCube";
	public ArrayList<Signal> signalList = new ArrayList<Signal>();
	public IceCube testCube;
	
	@Before
	public void setUp() throws SignalException, IceCubeException {
		signalList.add(new ReadSignal("sig1"));
		signalList.add(new ReadSignal("sig2"));
		signalList.add(new WriteSignal("sig3"));
		signalList.add(new WriteSignal("sig4"));
		
		testCube = new IceCube(iceCubeName, signalList);
	}

	@Test
	@Ignore
	public void testIceCubeJsonObject() {
		fail("Not yet implemented"); // TODO
	}

	@SuppressWarnings("unused")
	@Test
	public void testIceCubeStringListOfSignal() throws IceCubeException {
		ArrayList<Signal> sigList = new ArrayList<Signal>();
		IceCube iceCube = new IceCube("test", sigList);
		
		sigList = new ArrayList<Signal>();
		try {
			sigList.add(new ReadSignal("sig1"));
			iceCube = new IceCube("test", sigList);
		} catch (SignalException e1) {
			fail(e1.getMessage());
		}
		
		thrown.expect(IceCubeException.class);
		sigList = new ArrayList<Signal>();
		try {
			sigList.add(new ReadSignal("sig1"));
			sigList.add(new WriteSignal("sig1"));
			iceCube = new IceCube("test", sigList);
		} catch (SignalException e2) {
			fail(e2.getMessage());
		}
	}

	@Test
	@Ignore
	public void testGetEpicsDBString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testGetEpicsProtoString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCountSignals() {
		assertEquals(4, testCube.countSignals());
	}

}
