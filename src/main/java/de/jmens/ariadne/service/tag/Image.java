package de.jmens.ariadne.service.tag;

public class Image extends Tag<byte[]>
{

	public Image(byte[] value)
	{
		super(value);
	}

	@Override
	public TagType getType()
	{
		return TagType.IMAGE;
	}

}
