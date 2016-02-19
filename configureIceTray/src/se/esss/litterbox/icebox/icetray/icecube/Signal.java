package se.esss.litterbox.icebox.icetray.icecube;

import javax.json.JsonObject;

public abstract class Signal {

	private final String name;

	public Signal(JsonObject jsonInput) {
		this.name = jsonInput.getString("name");
	}
	
	public Signal(String nameInput) {
		this.name = nameInput;
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
	
}
