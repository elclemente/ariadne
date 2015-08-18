package de.jmens.ariadne.scan;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class TagScannerTest
{

	private static Path root;

	@BeforeClass
	public static void setupClass() throws Exception
	{
		root = Paths.get(ScannerTest.class.getResource("/tree").toURI());
	}

	@Test
	public void test()
	{
		final TagSynchronization scanner = new TagSynchronization();

		Assert.fail("Move on here: Write uuid to db next.");
	}

}
