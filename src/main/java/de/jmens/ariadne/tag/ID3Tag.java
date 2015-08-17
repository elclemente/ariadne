package de.jmens.ariadne.tag;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;

public interface ID3Tag
{
    String getAlbum();

    String getArtist();

    String getComment();

    String getTitle();

    String getTrack();

    String getVersion();

    String getYear();

    int getGenre();

    void setAlbum(String album);

    void setGenre(int genre);

    void setVersion(String version);

    void setTrack(String track);

    void setTitle(String title);

    void setComment(String comment);

    void setArtist(String artist);

    void setYear(String string);

    public static ID3v1 toId3v1Tag(ID3Tag tag)
    {
	final ID3v1Tag result = new ID3v1Tag();

	result.setAlbum(tag.getAlbum());
	result.setArtist(tag.getArtist());
	result.setComment(tag.getComment());
	result.setGenre(tag.getGenre());
	result.setTitle(tag.getTitle());
	result.setTrack(tag.getTrack());
	result.setYear(tag.getYear());
	result.setTrack(tag.getTrack());

	return result;
    }

    public static ID3v2 toId3v2Tag(ID3Tag tag)
    {
	final ID3v24Tag result = new ID3v24Tag();

	result.setAlbum(tag.getAlbum());
	result.setArtist(tag.getArtist());
	result.setComment(tag.getComment());
	result.setGenre(tag.getGenre());
	result.setTitle(tag.getTitle());
	result.setTrack(tag.getTrack());
	result.setYear(tag.getYear());
	result.setTrack(tag.getTrack());

	return result;
    }

    public static ID3Tag of(ID3v1 tag)
    {
	final ID3TagEntity result = new ID3TagEntity();
	result.setAlbum(trimToEmpty(tag.getAlbum()));
	result.setArtist(trimToEmpty(tag.getArtist()));
	result.setComment(trimToEmpty(tag.getComment()));
	result.setGenre(tag.getGenre());
	result.setTitle(trimToEmpty(tag.getTitle()));
	result.setTrack(trimToEmpty(tag.getTrack()));
	result.setVersion(trimToEmpty(tag.getVersion()));
	result.setYear(trimToEmpty(tag.getYear()));

	return result;
    }

    public static ID3Tag emtpyTag()
    {
	return new ID3TagEntity();
    }

}
