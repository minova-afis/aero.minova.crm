package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownLineBreakTest {

	public TracToMarkdownLineBreakTest() {
		super();
	}

	@Test
	public void testLineBreak() {
		String input = "[[BR]]";
		String output = "<br/>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}

}