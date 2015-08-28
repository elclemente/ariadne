package de.jmens.ariadne.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.jmens.ariadne.test.FileTest;

public class TaggerTest extends FileTest
{

	private Path testfile;
	private Path testfileV1;
	private Path testfileV2;

	@Before
	public void setUp() throws Exception
	{
		testfile = providePathFor("/silence_5.mp3");
		testfileV1 = providePathFor("/silence_5_v1tag.mp3");
		testfileV2 = providePathFor("/silence_5_v2tag.mp3");
	}

	@Test
	public void testLoadTagger()
	{
		assertThat(Tagger.load(testfile).get(), notNullValue(Tagger.class));
		assertThat(Tagger.load(null).isPresent(), is(false));
		assertThat(Tagger.load(Paths.get("/freaky/path/into/nowhere")).isPresent(), is(false));
	}

	@Test
	public void testLoadV1Tags() throws Exception
	{
		final Tag tag = Tagger.load(testfileV1).get().getTag();

		assertThat(tag.getAlbum(), is("For the weak"));
		assertThat(tag.getArtist(), is("Decay"));
		assertThat(tag.getGenre(), is(0));
		assertThat(tag.getTitle(), is("Foul Friend"));
		assertThat(tag.getTrack(), is("2"));
		assertThat(tag.getYear(), is("2000"));
	}

	@Test
	public void testLoadV2Tags() throws Exception
	{
		final Tag tag = Tagger.load(testfileV2).get().getTag();

		assertThat(tag.getAlbum(), is("For the weak"));
		assertThat(tag.getArtist(), is("Decay"));
		assertThat(tag.getGenre(), is(0));
		assertThat(tag.getTitle(), is("Foul Friend"));
		assertThat(tag.getTrack(), is("2"));
		assertThat(tag.getYear(), is("2000"));
	}

	@Test
	public void testUpdateTags() throws Exception
	{
		final Tagger tagger = Tagger.load(testfileV1).get();

		final Tag tag = tagger.getTag();

		final UUID fileId = UUID.randomUUID();
		final UUID scanId = UUID.randomUUID();

		tag.setAlbum("album");
		tag.setArtist("artist");
		tag.setGenre(1);
		tag.setTitle("title");
		tag.setTrack("track");
		tag.setYear("3000");
		tag.setFileId(fileId);
		tag.setScanId(scanId);

		tagger.writeTags();

		final Tag result = Tagger.load(testfileV1).get().getTag();

		assertThat(result.getAlbum(), is("album"));
		assertThat(result.getArtist(), is("artist"));
		assertThat(result.getGenre(), is(1));
		assertThat(result.getTitle(), is("title"));
		assertThat(result.getTrack(), is("track"));
		assertThat(result.getYear(), is("3000"));
		assertThat(result.getScanId(), is(scanId));
		assertThat(result.getFileId(), is(fileId));
	}

	@Test
	public void testUpdateReadonlyFile() throws Exception
	{
		final Set<PosixFilePermission> perms = new HashSet<>();
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.OTHERS_READ);

		final Tagger tagger = Tagger.load(testfileV2).get();
		tagger.getTag().setAlbum("foo");

		Files.setPosixFilePermissions(testfileV2, new HashSet<PosixFilePermission>());
		assertThat(testfileV2.toFile().canWrite(), is(false));

		assertThat(tagger.writeTags(), is(false));

		Files.setPosixFilePermissions(testfileV2, perms);
		assertThat(Tagger.load(testfileV2).get().getTag().getAlbum(), is("For the weak"));
	}
}
