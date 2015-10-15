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
	private Tag tags = new Tag();
	private Tag changes = new Tag();

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

	public Tag getTags()
	{
		return tags;
	}

	public void setTags(Tag tags)
	{
		this.tags = tags;
	}

	public Tag getChanges()
	{
		return changes;
	}

	public void setChanges(Tag changes)
	{
		this.changes = changes;
	}

	public ID3v2 toFileTag()
	{
		final ID3v24Tag result = new ID3v24Tag();

		result.setAlbum(tags.getAlbum());
		result.setArtist(tags.getArtist());
		result.setGenreDescription(tags.getGenre());
		result.setTitle(tags.getTitle());
		result.setTrack(tags.getTrack());
		result.setYear(tags.getYear());
		result.setTrack(tags.getTrack());
		result.setKey(getFileId().toString());

		if (getScanId() != null)
		{
			result.setComment(getScanId().toString());
		}

		result.setAlbumImage(tags.getImage(), tags.getMimeType());

		return result;
	}

	public TagEntity toEntity()
	{
		final TagEntity result = new TagEntity();

		result.setArtist(tags.getArtist());
		result.setAlbum(tags.getAlbum());
		result.setFileId(getFileId());
		result.setScanId(getScanId());
		result.setTitle(tags.getTitle());
		result.setGenre(tags.getGenre());
		result.setImage(tags.getImage());
		result.setMimeType(tags.getMimeType());
		result.setTrack(tags.getTrack());
		result.setYear(tags.getYear());

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
		result.tags.setAlbum(trimToEmpty(tag.getAlbum()));
		result.tags.setArtist(trimToEmpty(tag.getArtist()));
		result.tags.setGenre(tag.getGenreDescription());
		result.tags.setTitle(trimToEmpty(tag.getTitle()));
		result.tags.setTrack(trimToEmpty(tag.getTrack()));
		result.tags.setYear(trimToEmpty(tag.getYear()));

		if (tag instanceof ID3v2)
		{
			result.tags.setMimeType(((ID3v2) tag).getAlbumImageMimeType());
			result.tags.setImage(((ID3v2) tag).getAlbumImage());
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

	public static SoundFile of(TagEntity entity)
	{
		final SoundFile file = new SoundFile();
		final Tag tags = Tag.of(entity);

		file.setTags(tags);
		file.setFileId(entity.getFileId());
		file.setPath(entity.getPath());
		file.setScanId(entity.getScanId());

		return file;
	}
}
