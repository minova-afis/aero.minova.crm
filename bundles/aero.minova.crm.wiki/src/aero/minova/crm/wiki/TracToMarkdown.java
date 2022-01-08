package aero.minova.crm.wiki;

public class TracToMarkdown {
	public static String convert(String input) {
		input = TableConverter.convert(input);
		input = PreformattedTextConvert.convert(input);
		return HeaderConverter.convert(input); 
	}
}
