package de.jmens.ariadne.service.tag;

public class Track extends Tag<String>
{

	public Track(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.TRACK;
	}

}
