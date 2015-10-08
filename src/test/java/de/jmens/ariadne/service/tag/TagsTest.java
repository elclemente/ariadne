package de.jmens.ariadne.service.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class TagsTest
{
	@Test
	public void testTags()
	{
		final Tags tags = new Tags();
		tags.add(new Album("foo"));
		tags.add(new Album("album"));
		tags.add(new Album("bar"));
		tags.add(new Artist("artist"));
		tags.add(new Image(new byte[] {}));

		assertThat(tags.size(), is(3));
		assertThat(tags.get(TagType.ALBUM).getValue(), is("bar"));
		assertThat(tags.get(TagType.ARTIST).getValue(), is("artist"));
		assertThat(tags.get(TagType.TITLE), nullValue());
	}
}
