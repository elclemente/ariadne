package de.jmens.ariadne.tag;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.UUID;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;

public interface Tag
{
	String getAlbum();

	String getArtist();

	String getTitle();

	String getTrack();

	String getYear();

	String getGenre();

	void setAlbum(String album);

	void setGenre(String genre);

	void setTrack(String track);

	void setTitle(String title);

	void setArtist(String artist);

	void setYear(String string);

	void setScanId(UUID uuid);

	UUID getScanId();

	void setFileId(UUID randomUUID);

	UUID getFileId();

	byte[] getImage();

	void setImage(byte[] image);

	String getMimeType();

	void setMimeType(String mimeType);

	public static ID3v2 toFileTag(Tag tag)
	{
		final ID3v24Tag result = new ID3v24Tag();

		result.setAlbum(tag.getAlbum());
		result.setArtist(tag.getArtist());
		result.setGenreDescription(tag.getGenre());
		result.setTitle(tag.getTitle());
		result.setTrack(tag.getTrack());
		result.setYear(tag.getYear());
		result.setTrack(tag.getTrack());
		result.setKey(tag.getFileId().toString());
		if (tag.getScanId() != null)
		{
			result.setComment(tag.getScanId().toString());
		}
		result.setAlbumImage(tag.getImage(), tag.getMimeType());

		return result;
	}

	public static Tag of(ID3v1 tag)
	{
		final TagEntity result = new TagEntity();
		result.setAlbum(trimToEmpty(tag.getAlbum()));
		result.setArtist(trimToEmpty(tag.getArtist()));
		result.setGenre(tag.getGenreDescription());
		result.setTitle(trimToEmpty(tag.getTitle()));
		result.setTrack(trimToEmpty(tag.getTrack()));
		result.setYear(trimToEmpty(tag.getYear()));

		if (tag instanceof ID3v2)
		{
			result.setMimeType(((ID3v2) tag).getAlbumImageMimeType());
			result.setImage(((ID3v2) tag).getAlbumImage());
			result.setFileId(uuidFrom(((ID3v2) tag).getKey()));
			result.setScanId(uuidFrom(((ID3v2) tag).getComment()));
		}
		else
		{
			result.setFileId(null);
			result.setScanId(null);
		}

		return result;
	}

	public static UUID uuidFrom(String string)
	{
		try
		{
			return UUID.fromString(string);
		}
		catch (final Exception e)
		{
			return null;
		}
	}

	public static Tag emtpyTag()
	{
		return new TagEntity();
	}
}
