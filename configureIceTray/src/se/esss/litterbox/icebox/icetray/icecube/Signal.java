package se.esss.litterbox.icebox.icetray.icecube;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;

import se.esss.litterbox.icebox.exceptions.SignalException;
import se.esss.litterbox.icebox.utilities.InputChecker;

public abstract class Signal {

	private final String name;
	private final String pvName;

	public Signal(JsonObject jsonInput) throws SignalException {
		this.name = jsonInput.getString("name");
		if (!InputChecker.nameChecker(name)){
			throw new SignalException("Illegal signal name");
		}
		
		this.pvName = this.name + getPvExt();
	}
	
	public Signal(String nameInput) throws SignalException {
		this.name = nameInput;
		if (!checkName()) {
			throw new SignalException("Illegal signal name");
		}
		
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
	
	protected Boolean checkName() {
		if (name.isEmpty()) return false;
		if (Character.isDigit(name.charAt(0))) return false;
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) return false;
		return true;
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
