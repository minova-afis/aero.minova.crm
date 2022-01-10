package aero.minova.crm.wiki.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aero.minova.crm.wiki.TracToMarkdown;

public class TracToMarkdownImageTest {

	public TracToMarkdownImageTest() {
		super();
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

}