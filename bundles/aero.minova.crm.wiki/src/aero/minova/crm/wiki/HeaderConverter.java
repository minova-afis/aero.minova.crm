package aero.minova.crm.wiki;

import java.util.regex.Pattern;

public class HeaderConverter {
	public static String convert(String input) {
		input = Pattern.compile("^= (.*?)( =)?$", Pattern.MULTILINE).matcher(input).replaceAll("# $1"); // H1
		input = Pattern.compile("^== (.*?)( ==)?$", Pattern.MULTILINE).matcher(input).replaceAll("## $1"); // H2
		input = Pattern.compile("^=== (.*?)( ===)?$", Pattern.MULTILINE).matcher(input).replaceAll("### $1"); // H3
		input = Pattern.compile("^==== (.*?)( ====)?$", Pattern.MULTILINE).matcher(input).replaceAll("#### $1"); // H4
		input = Pattern.compile("^===== (.*?)( =====)?$", Pattern.MULTILINE).matcher(input).replaceAll("##### $1"); // H5
		input = Pattern.compile("^====== (.*?)( ======)?$", Pattern.MULTILINE).matcher(input).replaceAll("###### $1"); // H6
		input = Pattern.compile("^======= (.*?)( =======)?$", Pattern.MULTILINE).matcher(input).replaceAll("####### $1"); // H7
		return input;
	}
}
