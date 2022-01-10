package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownHeadlineTest {

	public TracToMarkdownHeadlineTest() {
		super();
	}

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

}