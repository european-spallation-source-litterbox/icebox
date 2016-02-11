package systems.icetech.icebox.icetray.icecube;

import javax.json.JsonObject;

public class WriteSignal extends Signal {

	private String pvName;
	private String recordType;

	public WriteSignal(JsonObject jsonInput) {
		super(jsonInput);
		setPvName(getName() + ":set");
		setRecordType("ao");
	}

	@Override
	public String WriteEPICSRecord(String fileName) {
		String outString = "record(" + getRecordType() + ", " + getPvName() + ") {\n";
		outString += "\t" + epicsDBFieldString("DTYP", "stream");
		outString += "\t" + epicsDBFieldString("OUT", "@"+fileName+" set_"+getName()+"() $(PORT)");
		outString += "}\n";
		return outString;
	}

	@Override
	public String WriteEPICSProtoFunc(char sig) {
		String outString = "set_" + getName() + " {\n";
		outString += "\tout \"" + sig + "%d\\n\";\n";
		outString += "\tExtraInput = Ignore;\n";
		outString += "}\n";
		return outString;
	}
	
	@Override
	public boolean isRead() {
		return false;
	}

	public String getPvName() {
		return pvName;
	}

	private void setPvName(String pvName) {
		this.pvName = pvName;
	}

	public String getRecordType() {
		return recordType;
	}

	private void setRecordType(String recordType) {
		this.recordType = recordType;
	}

}
