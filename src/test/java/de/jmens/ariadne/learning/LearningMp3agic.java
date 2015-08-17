package de.jmens.ariadne.learning;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.mpatric.mp3agic.Mp3File;

import de.jmens.ariadne.test.FileTest;

public class LearningMp3agic extends FileTest {

    private File testfile;

    @Before
    public void setUp() throws Exception {
	testfile = provideTestfile("/silence_5.mp3");
    }

    @Test
    public void testReadTag() throws Exception {
	final Mp3File file = new Mp3File(testfile);

	assertThat(file.hasId3v1Tag(), is(false));
	assertThat(file.hasId3v2Tag(), is(true));

	assertThat(file.getId3v2Tag().getComment(), is("From http://www.xamuel.com/blank-mp3s/"));
    }

    @Test
    public void testUpdateTag() throws Exception {

	final Mp3File file = new Mp3File(testfile);
	assertThat(file.getId3v2Tag().getAlbum(), isEmptyOrNullString());

	file.getId3v2Tag().setAlbum("Freakfunk");
	assertThat(file.getId3v2Tag().getAlbum(), is("Freakfunk"));
    }

    @Test
    public void testPersistTag() throws Exception {

	final File tempLocation = new File(folder.getRoot(), "copy.mp3");

	final Mp3File initializer = new Mp3File(testfile);
	initializer.getId3v2Tag().setAlbum("FizzBuzz");
	initializer.save(tempLocation.getAbsolutePath());

	final Mp3File original = new Mp3File(testfile);
	final Mp3File copy = new Mp3File(tempLocation);

	assertThat(original.getId3v2Tag().getAlbum(), isEmptyOrNullString());
	assertThat(copy.getId3v2Tag().getAlbum(), is("FizzBuzz"));
    }

    @Test
    public void testRemoveTag() throws Exception {
	final Mp3File file = new Mp3File(testfile);
	file.removeId3v1Tag();
	file.removeId3v2Tag();

	assertThat(new Mp3File(testfile).hasId3v2Tag(), is(true));

	file.save(testfile.getAbsolutePath() + ".new");
	assertThat(new Mp3File(testfile.getAbsolutePath() + ".new").hasId3v2Tag(), is(false));
    }

    @Test
    public void testGetFilename() throws Exception
    {
	final Mp3File file = new Mp3File(testfile);

	assertThat(file.getFilename(), is(testfile.getAbsolutePath()));
    }

}
