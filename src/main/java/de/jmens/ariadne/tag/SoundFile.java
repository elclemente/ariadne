package de.jmens.ariadne.tag;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.File;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

public class SoundFile
{
	private UUID fileId;
	private UUID scanId;
	private File path;
	private final Tag tags = new Tag();

	public String getArtist()
	{
		return tags.getArtist();
	}

	public void setArtist(String artist)
	{
		tags.setArtist(artist);
	}

	public String getAlbum()
	{
		return tags.getAlbum();
	}

	public void setAlbum(String album)
	{
		tags.setAlbum(album);
	}

	public String getTitle()
	{
		return tags.getTitle();
	}

	public void setTitle(String title)
	{
		tags.setTitle(title);
	}

	public String getGenre()
	{
		return tags.getGenre();
	}

	public void setGenre(String genre)
	{
		tags.setGenre(genre);
	}

	public String getYear()
	{
		return tags.getYear();
	}

	public void setYear(String year)
	{
		tags.setYear(year);
	}

	public String getTrack()
	{
		return tags.getTrack();
	}

	public void setTrack(String track)
	{
		tags.setTrack(track);
	}

	public byte[] getImage()
	{
		return tags.getImage();
	}

	public void setImage(byte[] image)
	{
		tags.setImage(image);
	}

	public String getMimeType()
	{
		return tags.getMimeType();
	}

	public void setMimeType(String mimeType)
	{
		tags.setMimeType(mimeType);
	}

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

	public static SoundFile of(Mp3File mp3File)
	{
		final SoundFile tag;

		if (mp3File.hasId3v2Tag())
		{
			tag = SoundFile.of(mp3File.getId3v2Tag());
		}
		else if (mp3File.hasId3v1Tag())
		{
			tag = SoundFile.of(mp3File.getId3v1Tag());
		}
		else
		{
			tag = SoundFile.emtpyTag();
		}

		tag.setPath(new File(mp3File.getFilename()));

		return tag;
	}

	public static SoundFile of(ID3v1 tag)
	{
		final SoundFile result = new SoundFile();
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

	public static SoundFile emtpyTag()
	{
		return new SoundFile();
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object that)
	{
		return EqualsBuilder.reflectionEquals(this, that);
	}
}
