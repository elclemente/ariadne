package de.jmens.ariadne.service.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tags implements Iterable<Tag<?>>
{
	private final Map<TagType, Tag<?>> tags = new HashMap<>();

	public void add(Tag<?> tag)
	{
		tags.put(tag.getType(), tag);
	}

	@Override
	public Iterator<Tag<?>> iterator()
	{
		return tags.values().iterator();
	}

	public int size()
	{
		return tags.size();
	}

	public Tag<?> get(TagType type)
	{
		return tags.get(type);
	}
}
