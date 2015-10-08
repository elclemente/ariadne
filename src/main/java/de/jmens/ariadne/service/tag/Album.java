package de.jmens.ariadne.service.tag;

public class Album extends Tag<String>
{
	public Album(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.ALBUM;
	}
}
