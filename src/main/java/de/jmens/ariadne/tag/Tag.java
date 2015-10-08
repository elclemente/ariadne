package de.jmens.ariadne.tag;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.File;
import java.util.UUID;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

public class Tag
{
	private UUID fileId;
	private UUID scanId;
	private String artist;
	private String album;
	private String title;
	private String genre;
	private String year;
	private String track;
	private byte[] image;
	private String mimeType;
	private File path;

	public UUID getFileId()
	{
		return fileId;
	}

	public void setFileId(UUID fileId)
	{
		this.fileId = fileId;
	}

	public UUID getScanId()
	{
		return scanId;
	}

	public void setScanId(UUID scanId)
	{
		this.scanId = scanId;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public String getTrack()
	{
		return track;
	}

	public void setTrack(String track)
	{
		this.track = track;
	}

	public byte[] getImage()
	{
		return image;
	}

	public void setImage(byte[] image)
	{
		this.image = image;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
	}

	public File getPath()
	{
		return path;
	}

	public void setPath(File path)
	{
		this.path = path;
	}

	public ID3v2 toFileTag()
	{
		final ID3v24Tag result = new ID3v24Tag();

		result.setAlbum(getAlbum());
		result.setArtist(getArtist());
		result.setGenreDescription(getGenre());
		result.setTitle(getTitle());
		result.setTrack(getTrack());
		result.setYear(getYear());
		result.setTrack(getTrack());
		result.setKey(getFileId().toString());

		if (getScanId() != null)
		{
			result.setComment(getScanId().toString());
		}

		result.setAlbumImage(getImage(), getMimeType());

		return result;
	}

	public TagEntity toEntity()
	{
		final TagEntity result = new TagEntity();

		result.setArtist(getArtist());
		result.setAlbum(getAlbum());
		result.setFileId(getFileId());
		result.setScanId(getScanId());
		result.setTitle(getTitle());
		result.setGenre(getGenre());
		result.setImage(getImage());
		result.setMimeType(getMimeType());
		result.setTrack(getTrack());
		result.setYear(getYear());

		return result;

	}

	public static Tag of(Mp3File mp3File)
	{
		final Tag tag;

		if (mp3File.hasId3v2Tag())
		{
			tag = Tag.of(mp3File.getId3v2Tag());
		}
		else if (mp3File.hasId3v1Tag())
		{
			tag = Tag.of(mp3File.getId3v1Tag());
		}
		else
		{
			tag = Tag.emtpyTag();
		}

		tag.setPath(new File(mp3File.getFilename()));

		return tag;
	}

	public static Tag of(ID3v1 tag)
	{
		final Tag result = new Tag();
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
		return new Tag();
	}
}
