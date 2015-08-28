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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.jmens.ariadne.persistence.ScanDao;
import de.jmens.ariadne.tag.Tagger;
import de.jmens.ariadne.test.DbTest;

public class ImporterTest extends DbTest
{

	private static final String FILE_1_1_2 = "1/1/2/silence_5_v2tag.mp3";
	private static final String FILE_1_1_1 = "1/1/1/silence_5_v2tag.mp3";

	private static Path root;

	@Mock
	private ScanDao scanDao;

	@InjectMocks
	private Importer importer;

	@BeforeClass
	public static void setupClass() throws Exception
	{
		initializeEntitymanager();
	}

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		root = provideRecursively("/tree");

		System.out.println(root);
	}

	@Test
	public void testSetup() throws Exception
	{
		assertThat(Files.isDirectory(root.resolve("1/1/1")), is(true));
		assertThat(Files.isDirectory(root.resolve("3/2/1")), is(true));
		assertThat(Files.exists(root.resolve("1/2/3/silence_5_v2tag.mp3")), is(true));
	}

	@Test
	public void test()
	{
		importer.scan(root);

		final Tagger tagger = Tagger.load(root.resolve(FILE_1_1_1)).get();

		assertThat(tagger.getTag().getScanId(), not(nullValue()));

		final UUID fileid1 = Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getFileId();
		final UUID fileid2 = Tagger.load(root.resolve(FILE_1_1_2)).get().getTag().getFileId();
		final UUID scanid1 = Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getScanId();
		final UUID scanid2 = Tagger.load(root.resolve(FILE_1_1_2)).get().getTag().getScanId();

		assertThat(fileid1, not(is(fileid2)));
		assertThat(scanid1, is(scanid2));
	}

	@Test
	public void testKeepExistingFileid() throws Exception
	{
		final Tagger tagger = Tagger.load(root.resolve(FILE_1_1_1)).get();

		final UUID expectedFileid = UUID.randomUUID();

		tagger.getTag().setFileId(expectedFileid);
		tagger.writeTags();

		importer.scan(root);

		assertThat(Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getFileId(), is(expectedFileid));
	}

	@Test
	public void testUpdateMissingFileid() throws Exception
	{
		assertThat(Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getFileId(), nullValue());

		final UUID scanId = importer.scan(root);

		assertThat(Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getFileId(), not(nullValue()));
		assertThat(Tagger.load(root.resolve(FILE_1_1_1)).get().getTag().getScanId(), is(scanId));
	}

	@Test
	public void testName() throws Exception
	{

	}

}
