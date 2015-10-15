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
			entity.setUncommittedArtist(tag.getTags().getArtist());
		}

		if (tag.getTags().getAlbum() != null)
		{
			entity.setUncommittedAlbum(tag.getTags().getAlbum());
		}

		if (tag.getTags().getTitle() != null)
		{
			entity.setUncommittedTitle(tag.getTags().getTitle());
		}

		if (tag.getTags().getGenre() != null)
		{
			entity.setUncommittedGenre(tag.getTags().getGenre());
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
			entity.setUncommittedYear(tag.getTags().getYear());
		}

		if (tag.getTags().getTrack() != null)
		{
			entity.setUncommittedTrack(tag.getTags().getTrack());
		}

		tagDao.saveOrUpdate(entity);

		return Response.ok().build();
	}

}
