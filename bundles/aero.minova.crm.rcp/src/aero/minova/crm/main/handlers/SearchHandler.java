
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SearchHandler {
	@Execute
	public void execute(MWindow window, EModelService modelService, EPartService partService) {
		MPartStack find = (MPartStack) modelService.find("aero.minova.crm.main.partstack.crm", window);
		if (find != null) {
			System.out.println("Hab was gefunden");
		}
	}

}