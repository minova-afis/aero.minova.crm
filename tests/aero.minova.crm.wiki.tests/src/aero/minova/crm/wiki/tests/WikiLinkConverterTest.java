package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class WikiLinkConverterTest {

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