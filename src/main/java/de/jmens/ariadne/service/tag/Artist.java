package de.jmens.ariadne.service.tag;

public class Artist extends Tag
{
	public Artist(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.ARTIST;
	}
}
