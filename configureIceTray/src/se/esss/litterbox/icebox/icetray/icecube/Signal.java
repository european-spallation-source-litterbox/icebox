package se.esss.litterbox.icebox.icetray.icecube;

import javax.json.JsonObject;

public abstract class Signal {

	private final String name;
	private final String pvName;

	public Signal(JsonObject jsonInput) {
		this.name = jsonInput.getString("name");
		this.pvName = this.name + getPvExt();
	}
	
	public Signal(String nameInput) {
		this.name = nameInput;
		this.pvName = this.name + getPvExt();
	}
	
	protected String epicsDBFieldString(String key, String value) {
		String outString = "field(";
		outString += key;
		outString += ", ";
		outString += "\"" + value + "\"";
		outString += ")\n";
		return outString;
	}
	
	public abstract String writeEPICSRecord(String fileName);
	
	public abstract String writeEPICSProtoFunc(char sig);
	
	protected abstract void buildJsonRep();
	
	public abstract JsonObject getJsonRep();
	
	protected abstract String getPvExt();

	public abstract boolean isRead();
	
	public boolean isWrite() {
		return !isRead();
	}
	
	public String toString() {
		return getName();
	}

	public String getName() {
		return name;
	}
	
	public abstract boolean equals(Object inputObject);

	public String getPvName() {
		return pvName;
	}
	
}
