package se.esss.litterbox.icebox.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.esss.litterbox.icebox.icetray.icecube.Signal;

public final class InputChecker {

	private InputChecker() {

	}
	
	public static Boolean nameChecker(String name) {
		if (name.isEmpty()) return false;
		if (Character.isDigit(name.charAt(0))) return false;
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) return false;
		return true;
	}
	
	public static Boolean signalListChecker(List<Signal> signalList) {
		ArrayList<String> nameList = new ArrayList<String>(signalList.size());
		for (Signal signal : signalList) {
			nameList.add(signal.getName());
		}
		
		Set<String> set = new HashSet<String>(nameList);
		return set.size() == signalList.size();
	}

}
