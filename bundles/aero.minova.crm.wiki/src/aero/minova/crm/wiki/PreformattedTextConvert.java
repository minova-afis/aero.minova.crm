package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class PreformattedTextConvert {

	public static String convert(String input) {
		input = Pattern.compile("^\\{\\{\\{", Pattern.MULTILINE).matcher(input).replaceAll("```");
		input = Pattern.compile("^}}}$", Pattern.MULTILINE).matcher(input).replaceAll("``");
		return input;
	}

}
