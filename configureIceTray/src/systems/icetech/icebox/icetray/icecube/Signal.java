package systems.icetech.icebox.icetray.icecube;

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
	
	public abstract String WriteEPICSRecord(String fileName);
	
	public abstract String WriteEPICSProtoFunc(char sig);

	public abstract boolean isRead();
	
	public boolean isWrite() {
		return !isRead();
	}
	
	public String toString() {
		//String rwString = isRead() ? "read" : "write";
		//return "Signal:  (" + rwString + ") " + getName();
		return getName();
	}

	public String getName() {
		return name;
	}
	
}
