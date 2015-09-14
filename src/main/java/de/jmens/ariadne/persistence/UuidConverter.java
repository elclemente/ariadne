package de.jmens.ariadne.persistence;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UuidConverter implements AttributeConverter<UUID, String>
{
	@Override
	public String convertToDatabaseColumn(UUID attribute)
	{
		return attribute.toString();
	}

	@Override
	public UUID convertToEntityAttribute(String dbData)
	{
		return UUID.fromString(dbData);
	}
}
