package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class WikiLinkConverter {

	public static String convert(String input, String path) {
		input = Pattern.compile("\\[wiki:(.*?) (.*?)\\]", Pattern.MULTILINE).matcher(input)
				.replaceAll("<a href=\"wiki/$1\">$2</a>");
		input = Pattern.compile("\\[wiki:(.*?)\\]", Pattern.MULTILINE).matcher(input)
				.replaceAll("<a href=\"wiki/$1\">wiki:$1</a>");
		return input;
	}

}
