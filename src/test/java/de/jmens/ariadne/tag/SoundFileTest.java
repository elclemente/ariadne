package de.jmens.ariadne.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class SoundFileTest
{

	@Test
	public void test()
	{
		final TagEntity entity = new TagEntity();

		entity.setAlbum("foo");
		entity.setArtist("bar");
		entity.setUncommittedAlbum("unfoo");

		final SoundFile file = SoundFile.of(entity);

		assertThat(file.getTags().getAlbum(), is("foo"));
		assertThat(file.getChanges().getAlbum(), is("unfoo"));
		assertThat(file.getAlbum(), is("unfoo"));

		assertThat(file.getTags().getArtist(), is("bar"));
		assertThat(file.getChanges().getArtist(), nullValue());
		assertThat(file.getArtist(), is("bar"));
	}

}
