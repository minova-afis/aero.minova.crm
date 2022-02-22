package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.cloud.crm.frontend.adapter.in.web.ApiClient;
import aero.minova.cloud.crm.frontend.adapter.in.web.ApiException;
import aero.minova.cloud.crm.frontend.adapter.in.web.api.ContactsPersonsApi;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsAllPersonsResponse;

public class LoadContactsHandler {

	@Execute
	public void execute(MPart part) {
		ApiClient client = new ApiClient();
		client.updateBaseUri("https://www.minova.de/de/");

		ContactsPersonsApi api = new ContactsPersonsApi(client);
		try {
			ContactsAllPersonsResponse allPersons = api.getAllPersons("minova");
			System.out.println(allPersons);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}
