package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class LineBreakConverter {
	public static String convert(String input, String path) {
		input = Pattern.compile("\\[\\[BR\\]\\]", Pattern.MULTILINE).matcher(input).replaceAll("<br/>"); // LineBreak in
		return input;
	}
}
