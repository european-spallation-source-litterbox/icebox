package systems.icetech.icebox.icetray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.JsonObject;

public class EpicsIOC {
	
	private final String pathString;
	private final FileWriter dbFileWriter, protoFileWriter;
	private IceCube iceCube;

	public EpicsIOC(String inputPathString) throws IOException {
		pathString = new String(inputPathString);
		dbFileWriter = new FileWriter(new File(pathString + "arduino.db"));
		protoFileWriter = new FileWriter(new File(pathString + "arduino.proto"));
	}
	
	public void setIceCube(JsonObject jsonInput) {
		iceCube = new IceCube(jsonInput);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
