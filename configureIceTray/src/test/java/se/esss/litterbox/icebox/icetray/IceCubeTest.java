package se.esss.litterbox.icebox.icetray;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.exceptions.SignalException;
import se.esss.litterbox.icebox.icetray.icecube.ReadSignal;
import se.esss.litterbox.icebox.icetray.icecube.Signal;
import se.esss.litterbox.icebox.icetray.icecube.WriteSignal;

public class IceCubeTest {
	
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
	public void testIceCubeConstructor() {
		assertNotNull(testCube.countSignals());
	}

	@Test
	@Ignore
	public void testIceCubeJsonObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testIceCubeStringListOfSignal() {
		fail("Not yet implemented"); // TODO
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
