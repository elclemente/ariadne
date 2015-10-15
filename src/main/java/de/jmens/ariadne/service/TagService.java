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
import de.jmens.ariadne.tag.SoundFile;
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
	public Response change(SoundFile tag)
	{
		LOGGER.info("Got: {}", tag);

		final TagEntity entity = tagDao.findMatching(tag);

		if (entity == null)
		{
			LOGGER.info("Cannot update tags for dataset {}", tag.getFileId());

			return Response.noContent().build();
		}

		if (tag.getTags().getArtist() != null)
		{
			entity.setArtist(tag.getTags().getArtist());
		}

		if (tag.getTags().getAlbum() != null)
		{
			entity.setAlbum(tag.getTags().getAlbum());
		}

		if (tag.getTags().getTitle() != null)
		{
			entity.setTitle(tag.getTags().getTitle());
		}

		if (tag.getTags().getGenre() != null)
		{
			entity.setGenre(tag.getTags().getGenre());
		}

		if (tag.getTags().getMimeType() != null)
		{
			entity.setMimeType(tag.getTags().getMimeType());
		}

		if (tag.getTags().getImage() != null)
		{
			entity.setImage(tag.getTags().getImage());
		}

		if (tag.getTags().getYear() != null)
		{
			entity.setYear(tag.getTags().getYear());
		}

		if (tag.getTags().getTrack() != null)
		{
			entity.setTrack(tag.getTags().getTrack());
		}

		tagDao.saveOrUpdate(entity);

		return Response.ok().build();
	}

}
