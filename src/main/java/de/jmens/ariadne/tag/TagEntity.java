package de.jmens.ariadne.tag;

import java.io.File;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.jmens.ariadne.persistence.PathConverter;

@Entity
@Table(name = "tag")
public class TagEntity implements Tag
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "file_id")
	private UUID fileId = UUID.randomUUID();

	@Column(name = "scan_id")
	private UUID scanId;

	@Column(name = "album")
	private String album;

	@Column(name = "artist")
	private String artist;

	@Column(name = "title")
	private String title;

	@Column(name = "track")
	private String track;

	@Column(name = "year")
	private String year;

	@Column(name = "genre")
	private String genre;

	@Lob
	@Column(name = "image")
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Column(name = "mimetype")
	private String mimeType;

	@Column(name = "path")
	@Convert(converter = PathConverter.class)

	private File path;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public File getPath()
	{
		return path;
	}

	@Override
	public void setPath(File path)
	{
		this.path = path;
	}

	@Override
	public String getYear()
	{
		return year;
	}

	@Override
	public void setYear(String year)
	{
		this.year = year;
	}

	@Override
	public String getAlbum()
	{
		return album;
	}

	@Override
	public void setAlbum(String album)
	{
		this.album = album;
	}

	@Override
	public String getArtist()
	{
		return artist;
	}

	@Override
	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public String getTrack()
	{
		return track;
	}

	@Override
	public void setTrack(String track)
	{
		this.track = track;
	}

	@Override
	public String getGenre()
	{
		return genre;
	}

	@Override
	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	@Override
	public void setScanId(UUID id)
	{
		this.scanId = id;
	}

	@Override
	public UUID getScanId()
	{
		return this.scanId;
	}

	@Override
	public void setFileId(UUID id)
	{
		this.fileId = id;
	}

	@Override
	public UUID getFileId()
	{
		return this.fileId;
	}

	@Override
	public byte[] getImage()
	{
		return image;
	}

	@Override
	public void setImage(byte[] image)
	{
		this.image = image;
	}

	@Override
	public String getMimeType()
	{
		return mimeType;
	}

	@Override
	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
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
	public boolean equals(Object object)
	{
		return EqualsBuilder.reflectionEquals(this, object);
	}
}
