package aero.minova.crm.model.converter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {

	public static final String SEPARATOR = " ";

	@Override
	public String convertToDatabaseColumn(List<Integer> attribute) {
		StringBuilder sb = new StringBuilder();
		attribute.forEach(i -> {
			sb.append(SEPARATOR + i);
		});
		return sb.toString();
	}

	@Override
	public List<Integer> convertToEntityAttribute(String dbData) {
		List<Integer> list = new ArrayList<>();
		String[] strings = dbData.split(" ");
		for (String string : strings) {
			if (string.length() < 1) continue;
			list.add(Integer.parseInt(string));
		}
		return list;
	}

}
