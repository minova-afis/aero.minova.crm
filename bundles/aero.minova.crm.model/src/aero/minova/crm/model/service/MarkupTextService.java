package aero.minova.crm.model.service;

import java.util.Optional;

import aero.minova.crm.model.jpa.MarkupText;

public interface MarkupTextService {

//	void getMarkupTexts(Consumer<List<MarkupText>> markupTextsConsumer);

	boolean saveMarkupText(MarkupText newMarkupText);

	Optional<MarkupText> getMarkupText(int id);

//	boolean deleteMarkupText(int id);
}