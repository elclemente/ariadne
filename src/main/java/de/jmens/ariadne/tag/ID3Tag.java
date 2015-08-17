package de.jmens.ariadne.tag;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import com.mpatric.mp3agic.ID3v1;

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
