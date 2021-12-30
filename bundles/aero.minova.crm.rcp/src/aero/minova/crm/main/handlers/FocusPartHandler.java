 
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.swt.widgets.Widget;

public class FocusPartHandler {
	@Execute
	public void execute(MPartStack partStack, MPart part) {
		Object o = partStack.getWidget();
		if (o != null) {
			Widget w = (Widget) o;
		}
	}
		
}