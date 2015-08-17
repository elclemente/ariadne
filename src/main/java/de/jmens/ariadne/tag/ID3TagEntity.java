package de.jmens.ariadne.tag;

public class ID3TagEntity implements ID3Tag
{
    private String album;
    private String artist;
    private String comment;
    private String title;
    private String track;
    private String version;
    private String year;

    @Override
    public String getYear()
    {
	return year;
    }

    public void setYear(String year)
    {
	this.year = year;
    }

    private int genre;

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
    public String getComment()
    {
	return comment;
    }

    @Override
    public void setComment(String comment)
    {
	this.comment = comment;
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
    public String getVersion()
    {
	return version;
    }

    @Override
    public void setVersion(String version)
    {
	this.version = version;
    }

    @Override
    public int getGenre()
    {
	return genre;
    }

    @Override
    public void setGenre(int genre)
    {
	this.genre = genre;
    }
}
