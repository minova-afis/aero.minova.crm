package aero.minova.crm.wiki;

public class TracToMarkdown {
	public static String convert(String input, String path) {
		input = TableConverter.convert(input);
		input = PreformattedTextConvert.convert(input);
		input = ImageConverter.convert(input, path);
		input = LineBreakConverter.convert(input, path);
		input = WikiLinkConverter.convert(input, path);
		return HeaderConverter.convert(input); 
	}
}
