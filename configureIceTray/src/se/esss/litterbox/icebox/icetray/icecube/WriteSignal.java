package se.esss.litterbox.icebox.icetray.icecube;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import se.esss.litterbox.icebox.exceptions.SignalException;

public class WriteSignal extends Signal {

	private final String recordType = "ao";
	private JsonObject jsonRep;

	public WriteSignal(JsonObject jsonInput) throws SignalException {
		super(jsonInput);
		
		buildJsonRep();
	}
	
	public WriteSignal(String nameInput) throws SignalException {
		super(nameInput);
		
		buildJsonRep();
	}
	
	public JsonObject getJsonRep() {
		return jsonRep;
	}

	@Override
	public String writeEPICSRecord(String fileName) {
		String outString = "record(" + getRecordType() + ", " + getPvName() + ") {\n";
		outString += "\t" + epicsDBFieldString("DTYP", "stream");
		outString += "\t" + epicsDBFieldString("OUT", "@"+fileName+" set_"+getName()+"() $(PORT)");
		outString += "}\n";
		return outString;
	}

	@Override
	public String writeEPICSProtoFunc(char sig) {
		String outString = "set_" + getName() + " {\n";
		outString += "\tout \"" + sig + "%d\\n\";\n";
		outString += "\tExtraInput = Ignore;\n";
		outString += "}\n";
		return outString;
	}
	
	@Override
	protected void buildJsonRep() {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		jsonRep = factory.createObjectBuilder()
			     .add("name", getName())
			     .add("RW", "W")
			     .build();
	}
	
	@Override
	public boolean isRead() {
		return false;
	}

	public String getRecordType() {
		return recordType;
	}

	@Override
	public boolean equals(Object inputObject) {
		if (this==inputObject) return true;
		if (this.getClass() != inputObject.getClass()) return false;
		
		WriteSignal inputSignal = (WriteSignal) inputObject;
		
		return this.getPvName().equals(inputSignal.getPvName())
				&& this.recordType.equals(inputSignal.recordType)
				&& this.getName().equals(inputSignal.getName());
	}

	@Override
	protected String getPvExt() {
		return ":set";
	}
	
	public static void main(String[] args){
		WriteSignal a,b,c;
		try {
			a = new WriteSignal("testName");
			b = new WriteSignal("testName");
			c = a;

			System.out.println(a==b); // this should be false
			System.out.println(a==c); // this should be true
			System.out.println(a.equals(b)); // this should be true
			System.out.println(a.equals(a)); // this should be true
			System.out.println(b.equals(a)); // this should be true
		} catch (SignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
