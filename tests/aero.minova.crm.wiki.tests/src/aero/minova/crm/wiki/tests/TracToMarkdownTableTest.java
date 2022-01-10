package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownTableTest {

	public TracToMarkdownTableTest() {
		super();
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

}