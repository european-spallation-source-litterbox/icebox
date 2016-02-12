package systems.icetech.icebox.icetray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

public class EpicsIOC {
	
	private final String iocNameString;
	private FileWriter dbFileWriter, protoFileWriter;
	private final File iocTopDir;
	private final IceCube iceCube;

	public EpicsIOC(JsonObject jsonInput) throws IOException {
		iceCube = new IceCube(jsonInput);
		
		iocNameString = new String(iceCube.getName());
		// TODO Uncomment the following when deploying on a real ice-cube
		//iocTopDir = new File("/home/pi/Apps/epics/"+iocNameString+"IOC/");
		iocTopDir = new File("/Users/stephenmolloy/Code/gitRepos/icebox/configureIceTray/src/systems/icetech/test/jsonTests/"+iocNameString+"IOC/");
		if (iocTopDir.exists()) {
			throw new IOException(iocTopDir + " already exists");
		}
	}

	public void makeIceIOC() throws IOException {
		makeBasicIOC();
		
		writeDBFile();
		
		writeProtoFile();
	}

	private void writeProtoFile() throws IOException {
		File protoFile = new File(iocTopDir + File.separator + iocNameString + "IOCApp/Db/arduino.proto");
		if (!protoFile.createNewFile()) {
			throw new IOException("Could not create " + protoFile);
		}
		protoFileWriter = new FileWriter(protoFile);
		
		protoFileWriter.write(iceCube.getEpicsProtoString());
		protoFileWriter.flush();
		protoFileWriter.close();
	}

	private void writeDBFile() throws IOException {
		File dbFile = new File(iocTopDir + File.separator + iocNameString + "IOCApp/Db/arduino.db");
		if (!dbFile.createNewFile()) {
			throw new IOException("Could not create " + dbFile);
		}
		dbFileWriter = new FileWriter(dbFile);
		
		dbFileWriter.write(iceCube.getEpicsDBString());
		dbFileWriter.flush();
		dbFileWriter.close();
	}

	private void makeBasicIOC() throws IOException {
		if (!iocTopDir.mkdir()) {
			throw new IOException("Could not create " + iocTopDir);
		}
		// TODO The following is only here for the dev env. It needs to be replaced with makeBaseApp.pl, etc.
		File tempDir = new File(iocTopDir + File.separator + iocNameString + "IOCApp/Db/");
		if (!tempDir.mkdirs()) {
			throw new IOException("Could not create " + tempDir);
		}
	}
	
	public static void main(String[] args) {
		String filepath = "/Users/stephenmolloy/Code/gitRepos/icebox/configureIceTray/src/systems/icetech/test/jsonTests/";
		try {
			EpicsIOC epicsIOC = new EpicsIOC(Json.createReader(new FileReader(filepath + "example.json")).readObject());
			epicsIOC.makeIceIOC();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
