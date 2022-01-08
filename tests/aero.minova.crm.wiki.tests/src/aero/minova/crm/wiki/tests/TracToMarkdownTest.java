package aero.minova.crm.wiki.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownTest {

	@Test
	public void testH1() {
		assertEquals("# H1", TracToMarkdown.convert("= H1"));
		assertEquals("\n# H1\n", TracToMarkdown.convert("\n= H1 =\n"));
		assertEquals("\n# H1\n", TracToMarkdown.convert("\n= H1\n"));
	}

	@Test
	public void testH2() {
		assertEquals("\n## H2\n", TracToMarkdown.convert("\n== H2 ==\n"));
		assertEquals("\n## H2\n", TracToMarkdown.convert("\n== H2\n"));
	}
	
	@Test
	public void testH3() {
		assertEquals("\n### H3\n", TracToMarkdown.convert("\n=== H3 ===\n"));
		assertEquals("\n### H3\n", TracToMarkdown.convert("\n=== H3\n"));
	}
	
	@Test
	public void testH4() {
		assertEquals("\n#### H4\n", TracToMarkdown.convert("\n==== H4 ====\n"));
		assertEquals("\n#### H4\n", TracToMarkdown.convert("\n==== H4\n"));
	}
	
	@Test
	public void testH5() {
		assertEquals("\n##### H5\n", TracToMarkdown.convert("\n===== H5 =====\n"));
		assertEquals("\n##### H5\n", TracToMarkdown.convert("\n===== H5\n"));
	}
	
	@Test
	public void testH6() {
		assertEquals("\n###### H6\n", TracToMarkdown.convert("\n====== H6 ======\n"));
		assertEquals("\n###### H6\n", TracToMarkdown.convert("\n====== H6\n"));
	}
	
	@Test
	public void testH7() {
		assertEquals("\n####### H7\n", TracToMarkdown.convert("\n======= H7 =======\n"));
		assertEquals("\n####### H7\n", TracToMarkdown.convert("\n======= H7\n"));
	}

	@Test
	public void testPreformattedText() {
		String input = """
				{{{
				Java asas
				}}}
				""";
		String output = """
				```
				Java asas
				```
				""";
		
		assertEquals(output, TracToMarkdown.convert(input));
	}
	
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

		assertEquals(output, TracToMarkdown.convert(input));
	}
	
//	@Test
	public void testTableMultiColumn() {
		String input = """
				Text
				||Feld||Wert||
				||Projekt||||
				||||ZVERWALT||
				""";
		String output = """
				Text
				
				|Feld|Wert
				|----|----|
				|Projekt| |
				|ZVERWALT||
				
				""";
		
		assertEquals(output, TracToMarkdown.convert(input));
	}
	
	@Test
	public void testTables() {
		String input = """
				Text
				||Feld||Wert||
				||Projekt||INTERN||
				||Aufwand||ZVERWALT||
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
				
				Text
				
				|Feld|Wert
				|----|----|
				|Projekt|INTERN
				|Aufwand|ZVERWALT
				
				""";
		
		assertEquals(output, TracToMarkdown.convert(input));
	}

	@Test
	public void testTablesLastChar() {
		String input = """
				Text
				||Feld||Wert||
				||Projekt||INTERN||
				||Aufwand||ZVERWALT||""";
		String output = """
				Text
				
				|Feld|Wert
				|----|----|
				|Projekt|INTERN
				|Aufwand|ZVERWALT
				
				""";
		
		assertEquals(output, TracToMarkdown.convert(input));
	}
}
