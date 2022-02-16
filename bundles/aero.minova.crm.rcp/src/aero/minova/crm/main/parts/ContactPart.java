package aero.minova.crm.main.parts;

import static org.eclipse.jface.widgets.ButtonFactory.newButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditBindings;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.extension.e4.selection.E4SelectionListener;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.RowSelectionModel;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.main.entries.DefaultPropertyEntry;
import aero.minova.crm.main.entries.NotesPropertyEntry;
import aero.minova.crm.main.entries.PhotoPropertyEntry;
import aero.minova.crm.main.entries.PropertyEntry;
import aero.minova.crm.main.handlers.SendMailHandler;
import aero.minova.crm.main.handlers.VCardExportHandler;
import aero.minova.crm.main.nattable.ContactColumnPropertyAccessor;
import aero.minova.crm.main.nattable.DragAndDropSupportContacts;
import aero.minova.crm.main.nattable.DragAndDropSupportGroups;
import aero.minova.crm.main.nattable.EditorConfigurationGrouplist;
import aero.minova.crm.main.nattable.GroupColumnPropertyAccessor;
import aero.minova.crm.model.Constants;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.Database;
import aero.minova.crm.model.jpa.Group;
import aero.minova.crm.model.vCard.VCardOptions;

public class ContactPart {

	private Database db = Database.getInstance();

	private Composite groupList;
	private Composite contactDetail;

	private Button deleteGroupButton;
	private NatTable groupTable;
	private SelectionLayer selectionLayerGroup;
	private List<Group> groups;
	private List<Group> selectedGroups = new ArrayList<>();

	private NatTable contactTable;
	private SelectionLayer selectionLayerContact;
	private Contact currentContact;
	private List<Contact> contacts;
	private List<Contact> selectedContacts;

	private boolean editable = false;
	private Map<String, PropertyEntry> entries;

	@Inject
	private MPart mPart;
	@Inject
	private ESelectionService service;
	@Inject
	private Shell shell;
	@Inject
	EMenuService menuService;
	@Inject
	EModelService modelService;

	private MApplication mApplication;

	@PostConstruct
	public void postConstruct(Composite parent, MApplication app) {
		this.mApplication = app;
		contacts = new ArrayList<>(db.getContacts());
		groups = db.getGroups();
		selectedGroups.add(db.getGroupById(0));

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setSashWidth(1);

		groupList = new Composite(sashForm, SWT.None);
		Composite contactList = new Composite(sashForm, SWT.None);
		contactDetail = new Composite(sashForm, SWT.None);

		createGroupList(groupList);
		createContactList(contactList);
		createContactDetail(contactDetail);
	}

	private void createGroupList(Composite groupList) {
		// groupList.setVisible(false);
		groupList.setLayout(new GridLayout(2, false));

		newButton(SWT.PUSH).text("Neue Gruppe").onSelect(e -> newGroup()).create(groupList);
		deleteGroupButton = newButton(SWT.PUSH).text("Gruppe Löschen").onSelect(e -> deleteGroup()).create(groupList);
		deleteGroupButton.setEnabled(false);

		IColumnPropertyAccessor<Group> columnPropertyAccessor = new GroupColumnPropertyAccessor();
		IRowDataProvider<Group> bodyDataProvider = new ListDataProvider<>(groups, columnPropertyAccessor);

		DataLayer bodyDataLayerGroup = new DataLayer(bodyDataProvider);

		selectionLayerGroup = new SelectionLayer(bodyDataLayerGroup);
		selectionLayerGroup.setSelectionModel(new RowSelectionModel<>(selectionLayerGroup, bodyDataProvider, rowObject -> rowObject.getGroupID(), true));
		E4SelectionListener<Group> eslGroup = new E4SelectionListener<>(service, selectionLayerGroup, bodyDataProvider);
		selectionLayerGroup.addLayerListener(eslGroup);

		ViewportLayer viewportLayer = new ViewportLayer(selectionLayerGroup);
		viewportLayer.setRegionName(GridRegion.BODY);

		groupTable = new NatTable(groupList, viewportLayer, false);

		menuService.registerContextMenu(groupTable, "aero.minova.crm.rcp.popupmenu.groupmenu");
		groupTable.addMouseListener(new MouseAdapter() {
			// Wähle Gruppe bei Rechtsklick
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					selectionLayerGroup.selectRow(0, groupTable.getRowPositionByY(e.y), false, false);
				}
			}
		});

		DragAndDropSupportGroups dndSupport = new DragAndDropSupportGroups(groupTable, selectionLayerGroup);
		Transfer[] transfer = { FileTransfer.getInstance() };
		groupTable.addDragSupport(DND.DROP_COPY, transfer, dndSupport);
		groupTable.addDropSupport(DND.DROP_COPY, transfer, dndSupport);

		final ColumnOverrideLabelAccumulator columnLabelAccumulator = new ColumnOverrideLabelAccumulator(bodyDataLayerGroup);
		bodyDataLayerGroup.setConfigLabelAccumulator(columnLabelAccumulator);

		groupTable.addConfiguration(new DefaultNatTableStyleConfiguration());
		groupTable.addConfiguration(new EditorConfigurationGrouplist());

		// Editieren der Gruppennamen
		groupTable.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, new IEditableRule() {

					@Override
					public boolean isEditable(int columnIndex, int rowIndex) {
						return columnIndex == 0;
					}

					@Override
					public boolean isEditable(ILayerCell cell, IConfigRegistry configRegistry) {
						return (cell.getRowIndex() != 0 && cell.getColumnIndex() == 0);
					}
				});
			}
		});
		bodyDataLayerGroup.addConfiguration(new DefaultEditConfiguration());
		bodyDataLayerGroup.addConfiguration(new DefaultEditBindings());

		groupTable.configure();

		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(groupTable);

		selectionLayerGroup.selectRow(0, 0, false, false);
	}

	private void createContactList(Composite contactList) {

		contactList.setLayout(new GridLayout(1, false));

		Text search = new Text(contactList, SWT.SEARCH | SWT.CANCEL | SWT.ICON_SEARCH);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false);
		search.setLayoutData(gd);
		search.setMessage("Suche Kontakt");
		search.setSize(1000, 200);
		search.addModifyListener(e -> filterContactTable(search.getText().toLowerCase().trim()));

		IColumnPropertyAccessor<Contact> columnPropertyAccessor = new ContactColumnPropertyAccessor();
		IRowDataProvider<Contact> bodyDataProvider = new ListDataProvider<>(contacts, columnPropertyAccessor);
		DataLayer bodyDataLayerContact = new DataLayer(bodyDataProvider);

		selectionLayerContact = new SelectionLayer(bodyDataLayerContact);
		selectionLayerContact.setSelectionModel(new RowSelectionModel<>(selectionLayerContact, bodyDataProvider, rowObject -> rowObject.getId(), true));

		E4SelectionListener<Contact> esl = new E4SelectionListener<>(service, selectionLayerContact, bodyDataProvider);
		selectionLayerContact.addLayerListener(esl);

		ViewportLayer viewportLayer = new ViewportLayer(selectionLayerContact);
		viewportLayer.setRegionName(GridRegion.BODY);

		contactTable = new NatTable(contactList, viewportLayer, true);

		// Rechtsklick-Menü
		menuService.registerContextMenu(contactTable, "aero.minova.crm.rcp.popupmenu.contactmenu");
		contactTable.addMouseListener(new MouseAdapter() {
			// Wähle Kontakt bei Rechtsklick
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					selectionLayerContact.selectRow(0, contactTable.getRowPositionByY(e.y), false, false);
				}
			}
		});

		DragAndDropSupportContacts dndContact = new DragAndDropSupportContacts(contactTable, selectionLayerContact);
		Transfer[] transferFile = { FileTransfer.getInstance() };
		contactTable.addDragSupport(DND.DROP_COPY, transferFile, dndContact);
		contactTable.addDropSupport(DND.DROP_COPY, transferFile, dndContact);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(contactTable);

		selectionLayerContact.selectRow(0, 0, false, false);
	}

	private void createContactDetail(Composite body) {

		entries = new LinkedHashMap<>();

		// Layout für Body definieren
		body.setLayout(new GridLayout(3, false));

		// Profilbild
		entries.put(VCardOptions.PHOTO, new PhotoPropertyEntry(body));

		// Normale input Felder
		entries.put(VCardOptions.N, new DefaultPropertyEntry(body, VCardOptions.N));
		entries.put(VCardOptions.ORG, new DefaultPropertyEntry(body, VCardOptions.ORG));
		entries.put(VCardOptions.TEL, new DefaultPropertyEntry(body, VCardOptions.TEL));
		entries.put(VCardOptions.EMAIL, new DefaultPropertyEntry(body, VCardOptions.EMAIL));
		entries.put(VCardOptions.BDAY, new DefaultPropertyEntry(body, VCardOptions.BDAY));
		entries.put(VCardOptions.ADR, new DefaultPropertyEntry(body, VCardOptions.ADR));

		// Notizen
		entries.put(VCardOptions.NOTE, new NotesPropertyEntry(body));
	}

	@Inject
	@Optional
	private void handleSelectionGroup(@Named(IServiceConstants.ACTIVE_SELECTION) List<Group> selected) {
		if (selected != null && !selected.isEmpty() && selected.get(0) instanceof Group && contacts != null) {
			contacts.clear();
			contacts.addAll(selected.get(0).getMembers());
			contactTable.refresh();
			selectedGroups = selected;
			selectionLayerContact.selectRow(0, 0, false, false);

			deleteGroupButton.setEnabled(selected.get(0).getGroupID() == 0);

		}
	}

	@Inject
	@Optional
	private void handleSelectionContact(@Named(IServiceConstants.ACTIVE_SELECTION) List<Contact> selected) {
		if (selected != null && !selected.isEmpty() && selected.get(0) instanceof Contact && contacts != null) {
			saveChanges();
			currentContact = selected.get(0);
			updateContactDetail(selected.get(0));
			selectedContacts = selected;
			switchEditable(false);
		}
	}

	private void showContacts(List<Contact> contactsToShow) {
		contacts.clear();
		contacts.addAll(contactsToShow);
		contactTable.refresh();
		groupTable.refresh();
	}

	private void filterContactTable(String s) {
		List<Contact> list = new ArrayList<>();
		for (Contact c : selectedGroups.get(0).getMembers()) {
			if (c.getValueString(VCardOptions.N).toLowerCase().contains(s)) {
				list.add(c);
			}
		}
		contacts.clear();
		contacts.addAll(list);
		contactTable.refresh();
	}

	private void updateContactDetail(Contact c) {

		for (Entry<String, PropertyEntry> entry : entries.entrySet()) {
			entry.getValue().setInput(c);
		}

		contactDetail.requestLayout();
	}

	@Inject
	@Optional
	private void subscribeTopicNewContact(@UIEventTopic(Constants.NEW_CONTACT) Contact c) {
		saveChanges();

		currentContact = c;
		selectedGroups.get(0).addMember(c);
		updateContactDetail(c);
		showContacts(selectedGroups.get(0).getMembers());

		selectionLayerGroup.selectRow(0, db.getPositionOfGroup(selectedGroups.get(0)), false, false);
		selectionLayerContact.selectRow(0, selectedGroups.get(0).getPositionInList(c), false, false);

		switchEditable(true);
	}

	@Inject
	@Optional
	private void subscribeTopicNewContact(@UIEventTopic(Constants.SELECT_CONTACTS) List<Contact> contacts) {
		saveChanges();
		for (Contact c : contacts) {
			selectionLayerContact.selectRow(0, selectedGroups.get(0).getPositionInList(c), true, false);
		}
	}

	@Inject
	@Optional
	private void subscribeTopicExistingContact(@UIEventTopic(Constants.CONTACT_EXISTS) int amount) {
		if (amount > 1) {
			MessageDialog.openInformation(shell, null, amount + " dieser Kontakte existieren bereits und werden aktualisiert.");
		} else {
			MessageDialog.openInformation(shell, null, "Dieser Kontakte existiert bereits und wird aktualisiert.");
		}
	}

	@Inject
	@Optional
	private void subscribeTopicRefreshContacts(@UIEventTopic(Constants.REFRESH_CONTACTS) Contact c) {
		saveChanges();

		currentContact = c;
		selectedGroups.get(0).addMember(c);
		updateContactDetail(c);
		showContacts(selectedGroups.get(0).getMembers());

		selectionLayerGroup.selectRow(0, db.getPositionOfGroup(selectedGroups.get(0)), false, false);
		selectionLayerContact.selectRow(0, selectedGroups.get(0).getPositionInList(c), false, false);
	}

	@Inject
	@Optional
	private void subscribeTopicEditContact(@UIEventTopic(Constants.TOPIC_EDIT) boolean e) {
		editable = !editable;
		switchEditable(editable);
	}

	public void setGroupListVisible(boolean visible) {
		groupList.setVisible(visible);
		groupList.getParent().requestLayout();
	}

	@Inject
	@Optional
	private void subscribeTopicDeleteContact(@UIEventTopic(Constants.DELETE_CONTACT) String e) {
		if (selectedContacts != null) {
			if (selectedGroups == null || selectedGroups.get(0).getGroupID() == 0) {
				MessageDialog dia;
				if (selectedContacts.size() == 1) {
					Contact c = selectedContacts.get(0);
					dia = new MessageDialog(shell, "Löschen", null, "Wollen Sie den Kontakt \"" + c.getValueString(VCardOptions.N) + "\" endgültig löschen?",
							MessageDialog.CONFIRM, new String[] { "Löschen", "Abbrechen" }, 0);
				} else {
					dia = new MessageDialog(shell, "Löschen", null, "Wollen Sie " + selectedContacts.size() + " Kontakte endgültig löschen?",
							MessageDialog.CONFIRM, new String[] { "Löschen", "Abbrechen" }, 0);
				}
				int result = dia.open();
				if (result == 0) {
					for (Contact c : selectedContacts) {
						db.removeContact(c);
					}
				}
			} else {
				Group g = db.getGroupById(selectedGroups.get(0).getGroupID());
				MessageDialog dia;
				if (selectedContacts.size() == 1) {
					Contact c = selectedContacts.get(0);
					dia = new MessageDialog(shell, "Entfernen", null,
							"Wollen Sie den Kontakt \"" + c.getValueString(VCardOptions.N) + "\" aus der Gruppe \"" + g.getName() + "\" entfernen?",
							MessageDialog.CONFIRM, new String[] { "Entfernen", "Abbrechen" }, 0);
				} else {
					dia = new MessageDialog(shell, "Entfernen", null,
							"Wollen Sie " + selectedContacts.size() + " Kontakte aus der Gruppe \"" + g.getName() + "\" entfernen?", MessageDialog.CONFIRM,
							new String[] { "Entfernen", "Abbrechen" }, 0);
				}
				int result = dia.open();
				if (result == 0) {
					for (Contact c : selectedContacts) {
						g.removeMember(c);
					}
				}
			}

			showContacts(selectedGroups.get(0).getMembers());
			selectionLayerGroup.selectRow(0, db.getPositionOfGroup(selectedGroups.get(0)), false, false);
			selectionLayerContact.selectRow(0, 0, false, false);
			groupTable.refresh();
		} else {
			MessageDialog.openInformation(shell, null, "Es ist kein Kontakt ausgewählt");
		}
	}

	@Inject
	@Optional
	private void subscribeTopicExportVCard(@UIEventTopic(Constants.EXPORT_VCARD) String e) {
		VCardExportHandler.exportVCard(currentContact);
	}

	@Inject
	@Optional
	private void subscribeTopicExportGroup(@UIEventTopic(Constants.EXPORT_GROUP) String e) {
		VCardExportHandler.exportVCard(selectedGroups.get(0));
	}

	@Inject
	@Optional
	private void subscribeTopicSendMail(@UIEventTopic(Constants.SEND_MAIL) String e) {
		SendMailHandler.sendMail(selectedGroups.get(0));
	}

	@Inject
	@Optional
	private void subscribeTopicDeleteGroup(@UIEventTopic(Constants.DELETE_GROUP) String e) {
		deleteGroup();
	}

	private void switchEditable(boolean editable) {
		this.editable = editable;

		// Change Text on Edit button
		for (MToolBarElement item : mPart.getToolbar().getChildren()) {
			if ("aero.minova.crm.rcp.handledtoolitem.bearbeiten".equals(item.getElementId())) {
				if (editable) {
					((MUILabel) item).setLabel("Fertig!");
				} else {
					((MUILabel) item).setLabel("Bearbeiten");
					contactTable.redraw();
				}
			}
		}

		for (Entry<String, PropertyEntry> entry : entries.entrySet()) {
			entry.getValue().setEditable(editable);
		}
		if (!editable && entries.get(VCardOptions.N).getTypeEntryByType("") != null) {
			entries.get(VCardOptions.N).getTypeEntryByType("").getTypeLabel().setFocus();
		}
		contactDetail.requestLayout();

		// Update Contact
		if (!editable) {
			saveChanges();
		}
		contactDetail.requestLayout();
	}

	private void saveChanges() {
		if (currentContact != null) {
			for (Entry<String, PropertyEntry> entry : entries.entrySet()) {
				entry.getValue().updateContact();
			}
		}
	}

	public void newGroup() {
		db.addGroup("Neu");
		groupTable.refresh();
	}

	public void deleteGroup() {
		if (selectedGroups != null) {
			Group g = selectedGroups.get(0);
			MessageDialog dia = new MessageDialog(shell, "Löschen", null, "Wollen Sie wirklich Gruppe \"" + g.getName() + "\" löschen?", MessageDialog.CONFIRM,
					new String[] { "Löschen", "Abbrechen" }, 0);
			int result = dia.open();

			if (result == 0) {
				db.removeGroup(g);
				groupTable.refresh();
				selectionLayerGroup.selectRow(0, 0, false, false);
			}
		} else {
			MessageDialog.openInformation(shell, null, "Es ist keine Gruppe ausgewählt");
		}
	}
}