package systems.icetech.icebox.icetray.icecube;

import javax.json.JsonObject;

public class ReadSignal extends Signal {
	
	private String scanRate;
	private String pvName;
	private String recordType = "ai";

	public ReadSignal(JsonObject jsonInput) {
		super(jsonInput);

		setScanRate(jsonInput.getString("scanRate"));
		setPvName(getName() + ":get");
		setRecordType("ai");
	}
	
	public ReadSignal(String nameInput, String scanRateString) {
		super(nameInput);
		setScanRate(scanRateString);
		setPvName(getName() + ":get");
		setRecordType("ai");
	}
	
	public ReadSignal(String nameInput) {
		this(nameInput, ".1 second");
	}

	@Override
	public String WriteEPICSRecord(String fileName) {
		String outString = "record(" + getRecordType() + ", " + getPvName() + ") {\n";
		outString += "\t" + epicsDBFieldString("DTYP", "stream");
		outString += "\t" + epicsDBFieldString("INP", "@"+fileName+" get_"+getName()+"() $(PORT)");
		outString += "\t" + epicsDBFieldString("SCAN", getScanRate());
		outString += "}\n";
		return outString;
	}

	@Override
	public String WriteEPICSProtoFunc(char sig) {
		String outString = "get_" + getName() + " {\n";
		outString += "\tout \"" + sig + "\";\n";
		outString += "\tin \"" + sig + " %f\";\n";
		outString += "\tExtraInput = Ignore;\n";
		outString += "}\n";
		return outString;
	}
	
	@Override
	public boolean isRead() {
		return true;
	}

	public String getScanRate() {
		return scanRate;
	}

	public void setScanRate(String scanRate) {
		this.scanRate = scanRate;
	}
	
	public String getPVName() {
		return getName() + ":get";
	}

	public String getRecordType() {
		return recordType;
	}

	private void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getPvName() {
		return pvName;
	}

	private void setPvName(String pvName) {
		this.pvName = pvName;
	}

}
