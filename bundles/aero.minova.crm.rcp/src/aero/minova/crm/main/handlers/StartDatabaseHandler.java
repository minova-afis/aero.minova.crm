
package aero.minova.crm.main.handlers;

import java.sql.SQLException;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.parts.SamplePart;
import aero.minova.crm.model.service.TodoService;

public class StartDatabaseHandler {
	@Execute
	public void execute(TodoService todoService, @Optional MPart part) throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer("<ul>");
		todoService.getTodos(todoList -> todoList.forEach(todo -> {
			sb.append("<li>" + todo.getSummary() + "</li>");
		}));
		sb.append("</ul>");

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		samplePart.refresh(sb.toString());
	}
}