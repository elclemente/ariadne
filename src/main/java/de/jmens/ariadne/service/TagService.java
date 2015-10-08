package de.jmens.ariadne.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.service.tag.Album;
import de.jmens.ariadne.service.tag.Artist;
import de.jmens.ariadne.service.tag.Image;
import de.jmens.ariadne.service.tag.Tags;

@Path("/tag")
public class TagService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

	@PUT
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(@PathParam("id") String id, Tags tag)
	{
		LOGGER.info("Requested tag update for {}: {}", id, tag);

		return Response.ok().build();
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public Response foo()
	{
		final Tags tags = new Tags();
		tags.add(new Album("album"));
		tags.add(new Artist("artist"));
		tags.add(new Image(new byte[] { 1, 2, 3 }));

		return Response.ok().entity(tags).build();
	}
}
