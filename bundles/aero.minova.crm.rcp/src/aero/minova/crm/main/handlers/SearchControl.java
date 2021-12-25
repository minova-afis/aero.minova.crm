package aero.minova.crm.main.handlers;


import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SearchControl {
	
	Text text;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout());
		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		Text text = new Text(comp, SWT.BORDER);
		text.setMessage("Search");
		text.setToolTipText("search");

		GridData lGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		lGridData.widthHint = 200;
		text.setLayoutData(lGridData);
//
//		text = new Text(parent, SWT.BORDER | SWT.ICON_SEARCH);
//		text.setBounds(-1, -1, 200, -1);
	}
}