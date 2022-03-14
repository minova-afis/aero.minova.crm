package aero.minova.crm.main.nattable;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.gui.ICellEditDialog;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class EditorConfigurationGrouplist extends AbstractRegistryConfiguration {

	public static final String COLUMN_ONE_LABEL = "ColumnOneLabel";
	public static final String COLUMN_TWO_LABEL = "ColumnTwoLabel";

	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, IEditableRule.ALWAYS_EDITABLE);

		registerEditors(configRegistry);
	}

	private void registerEditors(IConfigRegistry configRegistry) {
		registerColumnOneTextEditor(configRegistry);
		registerColumnTwoTextEditor(configRegistry);
	}

	private void registerColumnOneTextEditor(IConfigRegistry configRegistry) {
		// register a TextCellEditor for column two that commits on key up/down
		// moves the selection after commit by enter
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new TextCellEditor(true, true), DisplayMode.NORMAL,
				EditorConfigurationGrouplist.COLUMN_ONE_LABEL);

		// configure to open the adjacent editor after commit
		configRegistry.registerConfigAttribute(EditConfigAttributes.OPEN_ADJACENT_EDITOR, Boolean.TRUE, DisplayMode.EDIT,
				EditorConfigurationGrouplist.COLUMN_ONE_LABEL);

		// configure a custom message for the multi edit dialog
		Map editDialogSettings = new HashMap<>();
		editDialogSettings.put(ICellEditDialog.DIALOG_MESSAGE, "Please specify the lastname in here:");

		configRegistry.registerConfigAttribute(EditConfigAttributes.EDIT_DIALOG_SETTINGS, editDialogSettings, DisplayMode.EDIT,
				EditorConfigurationGrouplist.COLUMN_ONE_LABEL);
	}

	private void registerColumnTwoTextEditor(IConfigRegistry configRegistry) {
		// register a TextCellEditor for column two that commits on key up/down
		// moves the selection after commit by enter
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new TextCellEditor(true, true), DisplayMode.NORMAL,
				EditorConfigurationGrouplist.COLUMN_TWO_LABEL);

		// configure to open the adjacent editor after commit
		configRegistry.registerConfigAttribute(EditConfigAttributes.OPEN_ADJACENT_EDITOR, Boolean.TRUE, DisplayMode.EDIT,
				EditorConfigurationGrouplist.COLUMN_TWO_LABEL);

		// configure a custom message for the multi edit dialog
		Map editDialogSettings = new HashMap<>();
		editDialogSettings.put(ICellEditDialog.DIALOG_MESSAGE, "Please specify the lastname in here:");

		configRegistry.registerConfigAttribute(EditConfigAttributes.EDIT_DIALOG_SETTINGS, editDialogSettings, DisplayMode.EDIT,
				EditorConfigurationGrouplist.COLUMN_TWO_LABEL);
	}

}