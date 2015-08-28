package de.jmens.ariadne.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.jmens.ariadne.tag.ScanEntity;

@Path("scan")
public class ScanService
{
	@GET
	@Produces("application/json")
	public Response getScan()
	{
		final ScanEntity scan = new ScanEntity();

		scan.setId(5);

		return Response.ok().entity(scan).build();
	}

}
