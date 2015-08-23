package de.jmens.ariadne.scan;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jmens.ariadne.tag.Tagger;
import de.jmens.ariadne.test.DbTest;

public class TagScannerTest extends DbTest
{

	private static Path root;

	@BeforeClass
	public static void setupClass() throws Exception
	{
		initializeEntitymanager();
	}

	@Before
	public void setUp() throws Exception
	{
		root = provideRecursively("/tree");

		System.out.println(root);
	}

	@Test
	public void testSetup() throws Exception
	{
		assertThat(Files.isDirectory(folder.getRoot().toPath().resolve("tree/1/1/1")), is(true));
		assertThat(Files.isDirectory(folder.getRoot().toPath().resolve("tree/3/2/1")), is(true));
		assertThat(Files.exists(folder.getRoot().toPath().resolve("tree/1/2/3/silence_5_v2tag.mp3")), is(true));
	}

	@Test
	public void test()
	{
		final Importer scanner = new Importer();

		scanner.scan(root);

		final Tagger tagger = Tagger.load(folder.getRoot().toPath().resolve("tree/1/1/1/silence_5_v2tag.mp3")).get();

		assertThat(tagger.getTag().getScanId(), not(nullValue()));

		final UUID fileid1 = Tagger.load(folder.getRoot().toPath().resolve("tree/1/1/1/silence_5_v2tag.mp3")).get().getTag().getFileId();
		final UUID fileid2 = Tagger.load(folder.getRoot().toPath().resolve("tree/1/1/2/silence_5_v2tag.mp3")).get().getTag().getFileId();
		final UUID scanid1 = Tagger.load(folder.getRoot().toPath().resolve("tree/1/1/1/silence_5_v2tag.mp3")).get().getTag().getScanId();
		final UUID scanid2 = Tagger.load(folder.getRoot().toPath().resolve("tree/1/1/2/silence_5_v2tag.mp3")).get().getTag().getScanId();

		assertThat(fileid1, not(is(fileid2)));
		assertThat(scanid1, is(scanid2));
	}

}
