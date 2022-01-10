package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class ImageConverter {
	public static String convert(String input, String path) {
		input = Pattern.compile("\\[\\[Image\\((.*?),\\s*(\\d*[%px]*)\\)\\]\\]", Pattern.MULTILINE).matcher(input)
				.replaceAll("<img src=\"/attachment/" + path + "/$1\" width=\"$2\" />"); // H
		return input;
	}
}
