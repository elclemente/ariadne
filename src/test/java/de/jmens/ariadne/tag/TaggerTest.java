package de.jmens.ariadne.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import de.jmens.ariadne.test.FileTest;

public class TaggerTest extends FileTest {

    private Path testfile;
    private Path testfileV1;
    private Path testfileV2;


    @Before
    public void setUp() throws Exception {
	testfile = providePathFor("/silence_5.mp3");
	testfileV1 = providePathFor("/silence_5_v1tag.mp3");
	testfileV2 = providePathFor("/silence_5_v2tag.mp3");
    }

    @Test
    public void testLoadTagger() {
	assertThat(Tagger.load(testfile).get(), notNullValue(Tagger.class));
	assertThat(Tagger.load(null).isPresent(), is(false));
	assertThat(Tagger.load(Paths.get("/freaky/path/into/nowhere")).isPresent(), is(false));
    }

    @Test
    public void testLoadV1Tags() throws Exception
    {
	final ID3Tag tag = Tagger.load(testfileV1).get().getTag();

	assertThat(tag.getAlbum(), is("For the weak"));
	assertThat(tag.getArtist(), is("Decay"));
	assertThat(tag.getComment(), is("Cool Record"));
	assertThat(tag.getGenre(), is(0));
	assertThat(tag.getTitle(), is("Foul Friend"));
	assertThat(tag.getTrack(), is("2"));
	assertThat(tag.getVersion(), is("1"));
	assertThat(tag.getYear(), is("2000"));
    }

    @Test
    public void testLoadV2Tags() throws Exception
    {
	final ID3Tag tag = Tagger.load(testfileV2).get().getTag();

	assertThat(tag.getAlbum(), is("For the weak"));
	assertThat(tag.getArtist(), is("Decay"));
	assertThat(tag.getComment(), is("Cool Record"));
	assertThat(tag.getGenre(), is(0));
	assertThat(tag.getTitle(), is("Foul Friend"));
	assertThat(tag.getTrack(), is("2"));
	assertThat(tag.getVersion(), is("4.0"));
	assertThat(tag.getYear(), is("2000"));
    }



    @Test
    @Ignore
    public void testName() throws Exception
    {
	final ID3v1Tag v1Tag = new ID3v1Tag();
	v1Tag.setAlbum("For the weak");
	v1Tag.setArtist("Decay");;
	v1Tag.setComment("Cool Record");
	v1Tag.setGenre(0);
	v1Tag.setTitle("Foul Friend");
	v1Tag.setTrack("2");
	v1Tag.setYear("2000");

	final Mp3File file = new Mp3File(testfile.toFile());
	file.removeCustomTag();
	file.removeId3v1Tag();
	file.removeId3v2Tag();
	file.setId3v1Tag(v1Tag);
	file.save("/tmp/silence_5_v1tag.mp3");

	final ID3v24Tag v2Tag = new ID3v24Tag();
	v2Tag.setAlbum("For the weak");
	v2Tag.setArtist("Decay");;
	v2Tag.setComment("Cool Record");
	v2Tag.setGenre(0);
	v2Tag.setTitle("Foul Friend");
	v2Tag.setTrack("2");
	v2Tag.setYear("2000");

	file.removeId3v1Tag();
	file.setId3v2Tag(v2Tag);
	file.save("/tmp/silence_5_v2tag.mp3");
    }

}
