package de.jmens.ariadne.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.persistence.tag.TagDao;
import de.jmens.ariadne.tag.Tag;
import de.jmens.ariadne.tag.TagEntity;

@Path("/tag")
public class TagService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

	@Inject
	private TagDao tagDao;

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response change(Tag tag)
	{
		LOGGER.info("Got: {}", tag);

		final TagEntity entity = tagDao.findMatching(tag);

		if (entity == null)
		{
			LOGGER.info("Cannot update tags for dataset {}", tag.getFileId());

			return Response.noContent().build();
		}

		if (tag.getArtist() != null)
		{
			entity.setArtist(tag.getArtist());
		}

		if (tag.getAlbum() != null)
		{
			entity.setAlbum(tag.getAlbum());
		}

		if (tag.getTitle() != null)
		{
			entity.setTitle(tag.getTitle());
		}

		if (tag.getGenre() != null)
		{
			entity.setGenre(tag.getGenre());
		}

		if (tag.getMimeType() != null)
		{
			entity.setMimeType(tag.getMimeType());
		}

		if (tag.getImage() != null)
		{
			entity.setImage(tag.getImage());
		}

		if (tag.getYear() != null)
		{
			entity.setYear(tag.getYear());
		}

		if (tag.getTrack() != null)
		{
			entity.setTrack(tag.getTrack());
		}

		tagDao.saveOrUpdate(entity);

		return Response.ok().build();
	}

}
