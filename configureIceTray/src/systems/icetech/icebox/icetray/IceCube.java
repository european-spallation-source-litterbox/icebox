package systems.icetech.icebox.icetray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import systems.icetech.icebox.icetray.icecube.ReadSignal;
import systems.icetech.icebox.icetray.icecube.Signal;
import systems.icetech.icebox.icetray.icecube.WriteSignal;

public class IceCube {
	/*
	 * Each physical IceCube consists of an IOC (on a Raspberry Pi)
	 * and a single Arduino.  This Java class is the software 
	 * equivalent of that, and so consists of a name, and a list of 
	 * signals (corresponding to the PV's in the EPICS database).
	 * 
	 * The class is instantiated from a javax.json.JsonObject created
	 * from a JSON file.  This JSON file contains the name of the IceCube
	 * (which will appear as part of the PV name), and the list of signals.
	 * An example of such a file is as follows:
	 * 
	 * {
	 * "name": "RPi1",
	 * "signals": [{
	 *     "name": "photoresistor1",
	 *     "RW": "R",
	 *     "scanRate": "1 second"
	 *     }, {
	 *     "name": "led1",
	 *     "RW": "W"
	 * }]
	 * }
	 * 
	 * From this file, the class creates two extra strings: one 
	 * corresponding to the EPICS DB definition file, and one for
	 * the EPICS protocol file.
	 */
	private final String name;
	private final List<Signal> signals;
	private final String epicsDBString; // the contents of the EPICS DB defn file
	private final String epicsProtoString; // the contents of the EPICS proto file
	private Integer protoCharCtr = 0; // A ctr to keep track of the no of proto funcs
	private final String dbFileName = "arduino.db";

	public IceCube(JsonObject jsonInput) {
		this.name = jsonInput.getString("name");
		
		List<Signal> tempSignals = new ArrayList<Signal>();
		JsonArray jsonSigs = jsonInput.getJsonArray("signals");
		for (int i=0; i<jsonSigs.size(); i++) {
			JsonObject jsonObj = jsonSigs.getJsonObject(i);
			if (jsonObj.getString("RW").equals("R")) {
				tempSignals.add(new ReadSignal(jsonObj));
			}
			else if (jsonObj.getString("RW").equals("W")) {
				tempSignals.add(new WriteSignal(jsonObj));
			}
		}
		
		this.signals = new ArrayList<Signal>(tempSignals);
		
		this.epicsDBString = makeEpicsDBString(dbFileName);
		this.epicsProtoString = makeEpicsProtoString();
	}
	
	public IceCube(String nameInput, List<Signal> signalsInput) {
		this.name = nameInput;
		this.signals = new ArrayList<Signal>(signalsInput);
		
		this.epicsDBString = makeEpicsDBString(dbFileName);
		this.epicsProtoString = makeEpicsProtoString();
	}
	
	public String makeEpicsDBString(String fileName) {
		String outString = "";
		Iterator<Signal> iceCubeIterator = this.getSignals().iterator();
		while (iceCubeIterator.hasNext()) {
			outString += iceCubeIterator.next().WriteEPICSRecord(fileName);
		}
		return outString;
	}
	
	public String makeEpicsProtoString() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		alphabet += "abcdefghijklmnopqrstuvwxyz";
		String outString = "Terminator = LF;\n";
		Iterator<Signal> iceCubeIterator = this.getSignals().iterator();
		while (iceCubeIterator.hasNext()) {
			outString += iceCubeIterator.next().WriteEPICSProtoFunc(alphabet.charAt(protoCharCtr));
			protoCharCtr++;
		}
		return outString;
	}
	
	public String toString() {
		String outString = "IceCube: " + getName();
		for (int i=0; i<signals.size(); i++) {
			outString += "\n    " + signals.get(i);
		}
		return outString;
	}

	public String getName() {
		return name;
	}

	public List<Signal> getSignals() {
		return signals;
	}

	public String getEpicsDBString() {
		return epicsDBString;
	}

	public String getEpicsProtoString() {
		return epicsProtoString;
	}

	public static void main(String[] args) {
		String filepath = "/Users/stephenmolloy/Code/gitRepos/icebox/configureIceTray/src/systems/icetech/test/jsonTests/";
		try {
			IceCube iceCubeObj = new IceCube(Json.createReader(new FileReader(filepath + "example.json")).readObject());
			System.out.println(iceCubeObj.getEpicsDBString());
			System.out.println(iceCubeObj.getEpicsProtoString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
