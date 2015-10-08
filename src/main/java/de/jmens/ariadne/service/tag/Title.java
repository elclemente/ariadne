package de.jmens.ariadne.service.tag;

public class Title extends Tag<String>
{

	public Title(String value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.TITLE;
	}

}
