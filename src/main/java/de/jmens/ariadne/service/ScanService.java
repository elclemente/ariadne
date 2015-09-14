package de.jmens.ariadne.service;

import java.nio.file.Paths;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.jmens.ariadne.persistence.ScanDao;
import de.jmens.ariadne.scan.Importer;
import de.jmens.ariadne.tag.ScanEntity;

@Path("scan")
public class ScanService
{
	@Inject
	private ScanDao scans;

	@Inject
	private Importer importer;

	@GET
	@Produces("application/json")
	public Response getScan()
	{
		final ScanEntity scan = scans.getLastScan();

		return Response.ok().entity(scan).build();
	}

	@POST
	@Produces("application/json")
	public Response newScan()
	{
		final ScanEntity scan = importer.scan(Paths.get("/home/clemens/Work/ariadne"));

		return Response.ok().entity(scan).build();
	}
}
