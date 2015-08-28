package de.jmens.ariadne.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/service")
public class JRSConfiguration extends Application
{
	public JRSConfiguration()
	{
		super();
	}
}
