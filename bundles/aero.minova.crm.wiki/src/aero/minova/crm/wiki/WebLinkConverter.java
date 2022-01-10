package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class WebLinkConverter {

	public static String convert(String input, String path) {
		input = Pattern.compile("\\[(https?://.*?) (.*?)\\]", Pattern.MULTILINE).matcher(input)
				.replaceAll("<a href=\"$1\">$2</a>");
		input = Pattern.compile("\\[(https?://.*?)\\]", Pattern.MULTILINE).matcher(input)
				.replaceAll("<a href=\"$1\">$1</a>");
		return input;
	}

}
