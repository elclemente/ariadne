package de.jmens.ariadne.tag;

public class Tag
{
	private String artist;
	private String album;
	private String title;
	private String genre;
	private String year;
	private String track;
	private byte[] image;
	private String mimeType;

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

	public static Tag of(TagEntity entity)
	{
		final Tag tag = new Tag();

		tag.setAlbum(entity.getAlbum());
		tag.setArtist(entity.getArtist());
		tag.setTitle(entity.getTitle());
		tag.setGenre(entity.getGenre());
		tag.setYear(entity.getYear());
		tag.setTrack(entity.getTrack());
		tag.setImage(entity.getImage());
		tag.setMimeType(entity.getMimeType());

		return tag;
	}
}