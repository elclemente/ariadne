package de.jmens.ariadne.persistence;

import java.io.File;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PathConverter implements AttributeConverter<File, String>
{

	@Override
	public String convertToDatabaseColumn(File path)
	{
		if (path == null)
		{
			return "";
		}

		return path.getAbsolutePath();
	}

	@Override
	public File convertToEntityAttribute(String path)
	{
		return new File(path);
	}

}
