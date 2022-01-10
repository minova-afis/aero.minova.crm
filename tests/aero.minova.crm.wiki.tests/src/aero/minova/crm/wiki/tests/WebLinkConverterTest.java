package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class WebLinkConverterTest {

	@Test
	public void testWebLinkHttp() {
		String input = "[http://trac.minova.com/trac/minova/wiki/ISO]";
		String output = "<a href=\"http://trac.minova.com/trac/minova/wiki/ISO\">http://trac.minova.com/trac/minova/wiki/ISO</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}

	@Test
	public void testWebLinkHttps() {
		String input = "[https://trac.minova.com/trac/minova/wiki/ISO]";
		String output = "<a href=\"https://trac.minova.com/trac/minova/wiki/ISO\">https://trac.minova.com/trac/minova/wiki/ISO</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
	
	@Test
	public void testWebLinkNamed() {
		String input = "[http://trac.minova.com/trac/minova/wiki/ISO ISO]";
		String output = "<a href=\"http://trac.minova.com/trac/minova/wiki/ISO\">ISO</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}
	
	@Test
	public void testWebLinkNamedHttps() {
		String input = "[https://trac.minova.com/trac/minova/wiki/ISO ISO]";
		String output = "<a href=\"https://trac.minova.com/trac/minova/wiki/ISO\">ISO</a>";
		assertEquals(output, TracToMarkdown.convert(input, null));
	}

}