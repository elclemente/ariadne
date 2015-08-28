package de.jmens.ariadne.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestService
{
	@GET
	public String test()
	{
		return "{\"foo\":\"bar\"}";
	}

}
