package aero.minova.crm.main.parts;

import javax.annotation.PostConstruct;

import org.eclipse.jface.widgets.TextFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SamplePart {

	private Browser browser;

	private Text text;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		text = TextFactory.newText(SWT.BORDER) //
				.message("Enter text to mark part as dirty") //
				.onModify(e -> refresh()) //
				.layoutData(new GridData(GridData.FILL_HORIZONTAL))//
				.create(parent);

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	public void refresh() {
		browser.setUrl("http:/localhost:8082/test?x=" + text.getText());
	}

	public void refresh(String string) {
		browser.setUrl("http:/localhost:8082/test?x=" + string);
	}

}