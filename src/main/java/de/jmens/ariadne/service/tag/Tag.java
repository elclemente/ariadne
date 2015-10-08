package de.jmens.ariadne.service.tag;

public abstract class Tag<T>
{
	private final T value;

	public abstract TagType getType();

	public Tag(T value)
	{
		this.value = value;
	}

	public T getValue()
	{
		return value;
	}
}
