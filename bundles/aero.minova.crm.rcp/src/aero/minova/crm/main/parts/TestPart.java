package aero.minova.crm.main.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class TestPart {
	private Text reporterField;
	private Text ownerField;

	@PostConstruct
	public void createUI(Composite parent) {

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);

		TabItem tbtmBearbeiten = new TabItem(tabFolder, SWT.NONE);
		tbtmBearbeiten.setText("Bearbeiten");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmBearbeiten.setControl(composite);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));

		SashForm sashForm = new SashForm(composite, SWT.BORDER);
		new Label(composite, SWT.NONE);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));

		Browser editBrowser = new Browser(composite_1, SWT.NONE);

		TabItem tbtmView = new TabItem(tabFolder, SWT.NONE);
		tbtmView.setText("View");

		Browser viewBrowser = new Browser(tabFolder, SWT.NONE);
		tbtmView.setControl(viewBrowser);

	}
}
