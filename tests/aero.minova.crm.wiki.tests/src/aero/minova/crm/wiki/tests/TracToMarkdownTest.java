package aero.minova.crm.wiki.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownTest {

	@Test
	public void testH1() {
		assertEquals("# H1", TracToMarkdown.convert("= H1", null));
		assertEquals("\n# H1\n", TracToMarkdown.convert("\n= H1 =\n", null));
		assertEquals("\n# H1\n", TracToMarkdown.convert("\n= H1\n", null));
	}
	@Test
	public void testH2() {
		assertEquals("\n## H2\n", TracToMarkdown.convert("\n== H2 ==\n", null));
		assertEquals("\n## H2\n", TracToMarkdown.convert("\n== H2\n", null));
	}
	
	@Test
	public void testH3() {
		assertEquals("\n### H3\n", TracToMarkdown.convert("\n=== H3 ===\n", null));
		assertEquals("\n### H3\n", TracToMarkdown.convert("\n=== H3\n", null));
	}
	
	@Test
	public void testH4() {
		assertEquals("\n#### H4\n", TracToMarkdown.convert("\n==== H4 ====\n", null));
		assertEquals("\n#### H4\n", TracToMarkdown.convert("\n==== H4\n", null));
	}
	
	@Test
	public void testH5() {
		assertEquals("\n##### H5\n", TracToMarkdown.convert("\n===== H5 =====\n", null));
		assertEquals("\n##### H5\n", TracToMarkdown.convert("\n===== H5\n", null));
	}
	
	@Test
	public void testH6() {
		assertEquals("\n###### H6\n", TracToMarkdown.convert("\n====== H6 ======\n", null));
		assertEquals("\n###### H6\n", TracToMarkdown.convert("\n====== H6\n", null));
	}
	
	@Test
	public void testH7() {
		assertEquals("\n####### H7\n", TracToMarkdown.convert("\n======= H7 =======\n", null));
		assertEquals("\n####### H7\n", TracToMarkdown.convert("\n======= H7\n", null));
	}

	@Test
	public void testImagePercent() {
		assertEquals("<img src=\"/attachment/ticket/42955/SKEL_internalerror.png\" width=\"85%\" />", TracToMarkdown.convert("[[Image(SKEL_internalerror.png, 85%)]]", "ticket/42955"));
	}	
	
	@Test
	public void testImageNothing() {
		assertEquals("<img src=\"/attachment/ticket/42955/SKEL_internalerror.png\" width=\"85\" />", TracToMarkdown.convert("[[Image(SKEL_internalerror.png, 85)]]", "ticket/42955"));
	}
	
	@Test
	public void testImagePx() {
		assertEquals("<img src=\"/attachment/ticket/42955/SKEL_internalerror.png\" width=\"85px\" />", TracToMarkdown.convert("[[Image(SKEL_internalerror.png, 85px)]]", "ticket/42955"));
	}
	
	@Test
	public void testImageWiki() {
		String input = """
				  
				[[Image(git-install-01.png)]]
				
				Auf `Installieren` klicken.
				
				[[Image(git-install-02.png)]]
				
				Auf `Akzeptieren` klicken.
				
				""";
		String output = """
				  
				<img src="/attachment/wiki/mitarbeiter/--saw/git-install-01.png" />
				
				Auf `Installieren` klicken.
				
				<img src="/attachment/wiki/mitarbeiter/--saw/git-install-02.png" />
				
				Auf `Akzeptieren` klicken.
				
				""";
		assertEquals(output, TracToMarkdown.convert(input, "wiki/mitarbeiter/--saw"));
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
		
		assertEquals(output, TracToMarkdown.convert(input, null));
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

		assertEquals(output, TracToMarkdown.convert(input, null));
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
		
		assertEquals(output, TracToMarkdown.convert(input, null));
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
		
		assertEquals(output, TracToMarkdown.convert(input, null));
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
		
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
	
	@Test
	public void testLineBreak() {
		String input = "[[BR]]";
		String output = "<br/>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
	@Test
	public void testWikiLink() {
		String input = "[wiki:Projekte]";
		String output = "<a href=\"wiki/Projekte\">wiki:Projekte</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
	@Test
	public void testWikiLinkNamed() {
		String input = "[wiki:Projekte Bezeichnung]";
		String output = "<a href=\"wiki/Projekte\">Bezeichnung</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
}
