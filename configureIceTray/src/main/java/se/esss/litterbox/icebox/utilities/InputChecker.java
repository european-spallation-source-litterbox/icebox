package se.esss.litterbox.icebox.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}
