package aero.minova.crm.wiki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableConverter {

	public static String convert(String input) {
		int start = 0, end = 0, pos = 0;
		boolean startOfTable = true;
		StringBuilder sb = new StringBuilder();
		Pattern rowPattern = Pattern.compile("^\\|\\|.*\\|\\|$", Pattern.MULTILINE);
		Matcher matcher = rowPattern.matcher(input);
		while (pos < input.length() && matcher.find(pos)) {
			start = matcher.start();
			end = matcher.end();
			if (pos < start) {
				sb.append(input.substring(pos, start));
				if (input.charAt(start - 1) != '\n') {
					sb.append("\n");
				}
				if (start > 1 && input.charAt(start - 2) != '\n') {
					sb.append("\n");
				}
				if (startOfTable) {
					int columnCount = appendTableRow(sb, input, start, end);
					while (columnCount > 0) {
						sb.append("|----");
						columnCount--;
					}
					sb.append("|\n");
					startOfTable = false;
					start = end;
				}
				end++; // Newline character
				while (start < input.length() && matcher.find(start) && matcher.start() == end) {
					start = end;
					end = matcher.end() + 1;
					appendTableRow(sb, input, start, end);
					start = end;
				}
			}
			sb.append("\n");
			startOfTable = true;
			pos = start;
		}
		if (end < input.length())
			sb.append(input.substring(end));
		return sb.toString();
	}

	private static int appendTableRow(StringBuilder sb, String input, int start, int end) {
		Pattern columnPattern = Pattern.compile("(\\|\\|=?)(.*?)(=?\\|\\|)");
		Matcher matcher = columnPattern.matcher(input);
		int columnCount = 0;
		while (matcher.find(start)) {
			if (matcher.end() > end) {
				sb.append("\n");
				return columnCount;
			}
			sb.append("|" + matcher.group(2));
			columnCount++;
			start = matcher.end() - 2;
		}
		sb.append("\n");
		return columnCount;
	}
}
