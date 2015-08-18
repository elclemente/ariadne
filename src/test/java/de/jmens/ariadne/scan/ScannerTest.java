package de.jmens.ariadne.scan;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.jmens.ariadne.test.FileTest;

public class ScannerTest extends FileTest
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
		final Scanner scanner = Scanner
				.newScanner()
				.withEntrypoint(root)
				.applies(p -> System.out.println(p));

		assertThat("entrypoint initialized", scanner.entryPoint, is(root));
		assertThat("consumer initialized", scanner.consumer, notNullValue());
	}

	@Test
	public void testTreeTraversion() throws Exception
	{
		final List<Path> paths = new ArrayList<>();

		Scanner
				.newScanner()
				.withEntrypoint(root)
				.applies(p -> paths.add(p))
				.scan();

		assertThat(paths, hasSize(27));
	}

}
