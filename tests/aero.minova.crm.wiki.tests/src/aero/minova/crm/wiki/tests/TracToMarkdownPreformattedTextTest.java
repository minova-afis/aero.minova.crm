package aero.minova.crm.wiki.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownPreformattedTextTest {

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
}
