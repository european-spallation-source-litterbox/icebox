package se.esss.litterbox.icebox.icetray;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.json.Json;

import org.junit.Before;
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
	public void testIceCubeJsonObject() throws FileNotFoundException, IceCubeException {
		String filePath = "src/test/resources/example.json";
		FileReader jsonReader;jsonReader = new FileReader(filePath);
		IceCube iceCubeObj = new IceCube(Json.createReader(jsonReader).readObject());
		assertEquals("RPi1", iceCubeObj.getName());
		assertEquals(2,  iceCubeObj.countSignals());
		assertEquals(1,  iceCubeObj.countReadSignals());
		assertEquals(1,  iceCubeObj.countWriteSignals());
		assertEquals("photoresistor1", iceCubeObj.getReadSignals().get(0).getName());
		assertEquals("led1", iceCubeObj.getWriteSignals().get(0).getName());
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
}
