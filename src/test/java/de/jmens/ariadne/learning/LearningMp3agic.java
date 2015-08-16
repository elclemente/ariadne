package de.jmens.ariadne.learning;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.mpatric.mp3agic.Mp3File;

public class LearningMp3agic {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private File testfile;

	@Before
	public void setUp() throws Exception {
		Path tempfile = Paths.get(folder.getRoot().getAbsolutePath(), "testfile.mp3");
		
		try (InputStream mp3file = getClass().getResourceAsStream("/silence_5.mp3")) {
			Files.copy(mp3file, tempfile);
		} catch (IOException e) {
			Assert.fail("Cannot initialize test: " + e.getMessage());
		}

		testfile = new File(tempfile.toString());
	}

	@Test
	public void testReadTag() throws Exception {
		Mp3File file = new Mp3File(testfile);

		assertThat(file.hasId3v1Tag(), is(false));
		assertThat(file.hasId3v2Tag(), is(true));

		assertThat(file.getId3v2Tag().getComment(), is("From http://www.xamuel.com/blank-mp3s/"));
	}

	@Test
	public void testUpdateTag() throws Exception {
		
		Mp3File file = new Mp3File(testfile);
		assertThat(file.getId3v2Tag().getAlbum(), isEmptyOrNullString());

		file.getId3v2Tag().setAlbum("Freakfunk");
		assertThat(file.getId3v2Tag().getAlbum(), is("Freakfunk"));
	}
	
	@Test
	public void testPersistTag() throws Exception {

		File tempLocation = new File(folder.getRoot(), "copy.mp3");

		Mp3File initializer = new Mp3File(testfile);
		initializer.getId3v2Tag().setAlbum("FizzBuzz");
		initializer.save(tempLocation.getAbsolutePath());
		
		Mp3File original = new Mp3File(testfile);
		Mp3File copy = new Mp3File(tempLocation);
		
		assertThat(original.getId3v2Tag().getAlbum(), isEmptyOrNullString());
		assertThat(copy.getId3v2Tag().getAlbum(), is("FizzBuzz"));
	}

}
