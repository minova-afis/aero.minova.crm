package aero.minova.crm.service.http.tests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegExTests {


	@Test
	public void testTable() {
		String input = """
				Text
				||Feld||Wert||
				||Projekt||INTERN||
				||Aufwand||ZVERWALT||
				""";
		String output = """
				Text

				|Feld|Wert
				|----|----|
				|Projekt|INTERN
				|Aufwand|ZVERWALT
				
				""";

		int pos = 0;
		int startOfTable = -1;
		StringBuilder sb = new StringBuilder();
		Pattern rowPattern = Pattern.compile("^\\|\\|.*\\|\\|$", Pattern.MULTILINE);
		Matcher matcher = rowPattern.matcher(input);
		if (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			if (pos < start) {
				sb.append(input.substring(pos, start));
				pos = start;
				if (input.charAt(start - 1) != '\n') {
					sb.append("\n");
				}
				if (start > 1 && input.charAt(start - 2) != '\n') {
					sb.append("\n");
				}
				if (startOfTable == -1) {
					int columnCount = appendTableRow(sb, input, start, end);
					while (columnCount > 0) {
						sb.append("|----");
						columnCount--;
					}
					sb.append("|\n");
					startOfTable = end;
					start = end;
				}
				end++; // Newline character
				while (matcher.find(start) && matcher.start() == end) {
					start = end;
					end = matcher.end() + 1;
					appendTableRow(sb, input, start, end);
					start = end;
				}
			}
			sb.append("\n");
			assertEquals(output, sb.toString());
		}
	}

	private int appendTableRow(StringBuilder sb, String input, int start, int end) {
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

	 @Test
	public void testHeadlines() {
		String input = """
				Text

				=== Überschrift ==

				==== H4

				Text
				""";
		String output = """
				Text

				### Überschrift

				#### H4

				Text
				""";

		Pattern pattern = Pattern.compile("^(=*)=(#*\\s.*?)(\\s=*)?$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			input = matcher.replaceAll("$1#$2");
			matcher = pattern.matcher(input);
		}

		pattern = Pattern.compile("^(#*\\s.*?)$", Pattern.MULTILINE);
		matcher = pattern.matcher(input);
		input = matcher.replaceAll("$1\n");
		assertEquals(output, input);
	}

	@Test
	public void testHeadline2() {
		String input = """
				Text
				=== Überschrift ==
				==== H4
				Text
				""";
		String output = """
				Text
				### Überschrift

				#### H4

				Text
				""";

		Pattern pattern = Pattern.compile("^(=*)=(#*\\s.*?)(\\s=*)?$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			input = matcher.replaceAll("$1#$2");
			matcher = pattern.matcher(input);
		}

		pattern = Pattern.compile("^(#*\\s.*?)$", Pattern.MULTILINE);
		matcher = pattern.matcher(input);
		input = matcher.replaceAll("$1\n");
		assertEquals(output, input);
	}
	
	@Test
	public void testString() {
		assertEquals("\n## Test\n", "\n== Test\n".replaceAll("\n== (.*?)( ==)?\n", "\n## $1\n"));
		assertEquals("\n## Test\n", "\n== Test ==\n".replaceAll("\n== (.*?)( ==)?\n", "\n## $1\n"));
	}

}
