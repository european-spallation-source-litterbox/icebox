package systems.icetech.icebox.icetray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.JsonObject;

public class EpicsIOC {
	
	private final String iocNameString;
	private final FileWriter dbFileWriter, protoFileWriter;
	private final File iocTopDir;
	private final IceCube iceCube;

	public EpicsIOC(JsonObject jsonInput) throws IOException {
		iceCube = new IceCube(jsonInput);
		
		iocNameString = new String(iceCube.getName());
		iocTopDir = new File("/home/pi/Apps/epics/"+iocNameString+"IOC/");
		if (iocTopDir.exists()) {
			throw new IOException(iocTopDir + " already exists");
		}
		if (!iocTopDir.mkdir()) {
			throw new IOException("Could not create" + iocTopDir);
		}
		
		dbFileWriter = new FileWriter(new File(iocTopDir + iocNameString + "IOCApp/Db/arduino.db"));
		protoFileWriter = new FileWriter(new File(iocTopDir + iocNameString + "IOCApp/Db/arduino.proto"));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
