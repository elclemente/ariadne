package de.jmens.ariadne.service.tag;

public class Year extends Tag<String>
{

	public Year(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.YEAR;
	}

}
