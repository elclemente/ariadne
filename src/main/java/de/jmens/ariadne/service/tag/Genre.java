package de.jmens.ariadne.service.tag;

public class Genre extends Tag<String>
{

	public Genre(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.GENRE;
	}

}
