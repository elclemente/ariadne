package de.jmens.ariadne.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.persistence.tag.TagDao;
import de.jmens.ariadne.tag.SoundFile;
import de.jmens.ariadne.tag.TagEntity;

@Path("filter")
public class FilterService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterService.class);

	@Inject
	private TagDao tagDao;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response applyFilter(Filter filter)
	{
		final List<TagEntity> tags = tagDao.findTags(filter);

		final List<SoundFile> result = new ArrayList<>();
		for (final TagEntity entity : tags)
		{
			result.add(SoundFile.of(entity));
		}

		return Response.ok().entity(result).build();
	}

}
