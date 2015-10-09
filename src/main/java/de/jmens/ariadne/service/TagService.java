package de.jmens.ariadne.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.tag.Tag;

@Path("/tag")
public class TagService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Tag tag)
	{
		LOGGER.info("Got: {}", tag);

		return Response.ok().build();
	}

}
