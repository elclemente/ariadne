package de.jmens.ariadne.service;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Filter
{
	private String genre;
	private String artist;

	private int firstResult;
	private int maxResults;

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public int getFirstResult()
	{
		return firstResult;
	}

	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	public int getMaxResults()
	{
		return maxResults;
	}

	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}