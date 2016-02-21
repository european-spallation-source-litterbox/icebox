package se.esss.litterbox.icebox.icetray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import se.esss.litterbox.icebox.exceptions.IceCubeException;

public class EpicsIOC {
	
	private final String iocNameString;
	private FileWriter dbFileWriter, protoFileWriter;
	private final File iocTopDir;
	private final IceCube iceCube;

	public EpicsIOC(JsonObject jsonInput) throws IOException, IceCubeException {
		this(new IceCube(jsonInput));
	}
	
	public EpicsIOC(IceCube iceCubeInput) throws IOException {
		this.iceCube = iceCubeInput;
		
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
		
		fixConfigureRelease();
		fixDbMakefile();
		fixSrcMakefile();
		fixStCmdFile();
		
		writeDBFile();
		writeProtoFile();
	}

	private void fixStCmdFile() throws FileNotFoundException, IOException {
		String topSearch = "\\$\\(TOP\\)";
		String iocSearch = "\\$\\(IOC\\)";
		String topReplace = iocTopDir.toString();
		String iocReplace = "ioc" + iocNameString + "IOC";
		StringBuilder stcmdStringBuilder = new StringBuilder();
		File stcmdFile = new File(iocTopDir + File.separator + "iocBoot/ioc" + iocNameString + "IOC" + File.separator + "st.cmd");
		BufferedReader stcmdReader = new BufferedReader(new FileReader(stcmdFile));
		String line;
		while ((line=stcmdReader.readLine()) != null) {
			line = line.replaceAll(topSearch, topReplace);
			line = line.replaceAll(iocSearch, iocReplace);
			stcmdStringBuilder.append(line+"\n");
			if (line.equals("> envPaths")) {
				stcmdStringBuilder.append("epicsEnvSet(STREAM_PROTOCOL_PATH,\"" + iocNameString + "IOCApp/Db\")\n");
			}
			if (line.equals(iocNameString + "IOC_registerRecordDeviceDriver pdbbase")) {
				stcmdStringBuilder.append("drvAsynSerialPortConfigure(\"SERIALPORT\",\"/dev/ttyACM0\",0,0,0)\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"baud\",\"115200\")\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"bits\",\"8\")\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"parity\",\"none\")\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"stop\",\"1\")\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"clocal\",\"Y\")\n");
				stcmdStringBuilder.append("asynSetOption(\"SERIALPORT\",-1,\"crtscts\",\"N\")\n");
				stcmdStringBuilder.append("dbLoadRecords(\"db/arduino.db\",\"PORT='SERIALPORT'\")\n");
			}
		}
		stcmdReader.close();
		stcmdFile.delete();
		stcmdFile.createNewFile();
		FileWriter stcmdWriter = new FileWriter(stcmdFile);
		stcmdWriter.write(stcmdStringBuilder.toString());
		stcmdWriter.flush();
		stcmdWriter.close();
	}

	private void fixDbMakefile() throws FileNotFoundException, IOException {
		StringBuilder makefileStringBuilder = new StringBuilder();
		File makefileFile = new File(iocTopDir + File.separator + iocNameString + "IOCApp/Db/Makefile");
		BufferedReader makeFileReader = new BufferedReader(new FileReader(makefileFile));
		String line;
		while ((line = makeFileReader.readLine()) != null) {
			makefileStringBuilder.append(line+"\n");
			if (line.equals("#DB += xxx.db")) {
				makefileStringBuilder.append("DB += arduino.db\n");
			}
		}
		makeFileReader.close();
		makefileFile.delete();
		makefileFile.createNewFile();
		FileWriter makefileWriter = new FileWriter(makefileFile);
		makefileWriter.write(makefileStringBuilder.toString());
		makefileWriter.flush();
		makefileWriter.close();
	}

	private void fixSrcMakefile() throws FileNotFoundException, IOException {
		StringBuilder makefileStringBuilder = new StringBuilder();
		File makefileFile = new File(iocTopDir + File.separator + iocNameString + "IOCApp/src/Makefile");
		BufferedReader makeFileReader = new BufferedReader(new FileReader(makefileFile));
		String line;
		while ((line = makeFileReader.readLine()) != null) {
			makefileStringBuilder.append(line+"\n");
			if (line.equals(iocNameString + "IOC_DBD += base.dbd")) {
				makefileStringBuilder.append(iocNameString + "IOC_DBD += asyn.dbd\n");
				makefileStringBuilder.append(iocNameString + "IOC_DBD += stream.dbd\n");
				makefileStringBuilder.append(iocNameString + "IOC_DBD += drvAsynIPPort.dbd\n");
				makefileStringBuilder.append(iocNameString + "IOC_DBD += drvAsynSerialPort.dbd\n");
			}
			if (line.contains(iocNameString + "IOC_LIBS")) {
				makefileStringBuilder.append(iocNameString + "IOC_LIBS += asyn\n");
				makefileStringBuilder.append(iocNameString + "IOC_LIBS += stream\n");
			}
		}
		makeFileReader.close();
		makefileFile.delete();
		makefileFile.createNewFile();
		FileWriter makefileWriter = new FileWriter(makefileFile);
		makefileWriter.write(makefileStringBuilder.toString());
		makefileWriter.flush();
		makefileWriter.close();
	}

	private void fixConfigureRelease() throws IOException {
		File releaseFile = new File(iocTopDir + File.separator + "configure" + File.separator + "RELEASE");
		FileWriter releaseFileWriter = new FileWriter(releaseFile);
		releaseFileWriter.append("ASYN=/usr/local/epics/modules/asyn\n");
		releaseFileWriter.append("STREAM=/usr/local/epics/modules/stream\n");
		releaseFileWriter.flush();
		releaseFileWriter.close();
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
		// TODO replace the following method with makeBaseApp.pl, etc.
		pretendForDevEnv();
	}

	private void pretendForDevEnv() throws IOException {
		// TODO This entire method is only here for the dev env. It needs to be replaced with makeBaseApp.pl, etc.
		File dbDir = new File(iocTopDir + File.separator + iocNameString + "IOCApp/Db/");
		if (!dbDir.mkdirs()) {
			throw new IOException("Could not create " + dbDir);
		}
		File dbMakefileFile = new File(dbDir + File.separator + "Makefile");
		if (!dbMakefileFile.createNewFile()) {
			throw new IOException("Could not create " + dbMakefileFile);
		}
		FileWriter dbMakefileWriter = new FileWriter(dbMakefileFile);
		dbMakefileWriter.append("blah blah\n");
		dbMakefileWriter.append("blah blah again\n");
		dbMakefileWriter.append("blah blah and again\n");
		dbMakefileWriter.append("#DB += xxx.db\n");
		dbMakefileWriter.append("blah blah and for the last time\n");
		dbMakefileWriter.flush();
		dbMakefileWriter.close();
		
		File configDir = new File(iocTopDir + File.separator + "configure");
		if (!configDir.mkdir()) {
			throw new IOException("Could not create " + configDir);
		}
		
		File srcDir = new File(iocTopDir + File.separator + iocNameString + "IOCApp/src/");
		if (!srcDir.mkdirs()) {
			throw new IOException("Could not create " + srcDir);
		}
		File srcMakefileFile = new File(srcDir + File.separator + "Makefile");
		if (!srcMakefileFile.createNewFile()) {
			throw new IOException("Could not create " + srcMakefileFile);
		}
		FileWriter srcMakefileWriter = new FileWriter(srcMakefileFile);
		srcMakefileWriter.append("blah blah\n");
		srcMakefileWriter.append("blah blah again\n");
		srcMakefileWriter.append(iocNameString + "IOC_DBD += base.dbd\n");
		srcMakefileWriter.append("blah blah and again\n");
		srcMakefileWriter.append(iocNameString + "IOC_LIBS = blah\n");
		srcMakefileWriter.append("blah blah and for the last time\n");
		srcMakefileWriter.flush();
		srcMakefileWriter.close();
		
		File releaseFile = new File(configDir + File.separator + "RELEASE");
		if (!releaseFile.createNewFile()) {
			throw new IOException("Could not create " + releaseFile);
		}
		
		File stcmdDir = new File(iocTopDir + File.separator + "iocBoot/ioc" + iocNameString + "IOC");
		if (!stcmdDir.mkdirs()) {
			throw new IOException("Could not create " + stcmdDir);
		}
		File stcmdFile = new File(stcmdDir + File.separator + "st.cmd");
		if (!stcmdFile.createNewFile()) {
			throw new IOException("Could not create " + stcmdFile);
		}
		FileWriter stcmdFileWriter = new FileWriter(stcmdFile);
		stcmdFileWriter.append("blah blah\n");
		stcmdFileWriter.append("> envPaths\n");
		stcmdFileWriter.append("blah $(TOP) blah\n");
		stcmdFileWriter.append("blah $(IOC) blah\n");
		stcmdFileWriter.append("blah blah\n");
		stcmdFileWriter.append(iocNameString + "IOC_registerRecordDeviceDriver pdbbase\n");
		stcmdFileWriter.append("blah blah\n");
		stcmdFileWriter.append("blah blah\n");
		stcmdFileWriter.flush();
		stcmdFileWriter.close();
	}
	
	public static void main(String[] args) {
		String filepath = "/Users/stephenmolloy/Code/gitRepos/icebox/configureIceTray/src/systems/icetech/test/jsonTests/";
		try {
			EpicsIOC epicsIOC = new EpicsIOC(Json.createReader(new FileReader(filepath + "example.json")).readObject());
			epicsIOC.makeIceIOC();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IceCubeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
