package de.jmens.ariadne.test;

import static java.text.MessageFormat.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class FileTest
{

	private static final Random RANDOM = new Random();

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	protected File provideTestfile(String name)
	{
		return new File(providePathFor(name).toString());
	}

	protected Path providePathFor(String name)
	{
		final String extension = FilenameUtils.getExtension(name);

		final Path tempfile = Paths.get(folder.getRoot().getAbsolutePath(), format("{0}.{1}", getRandomString(), extension));

		try (InputStream mp3file = getClass().getResourceAsStream(name))
		{
			Files.copy(mp3file, tempfile);
		}
		catch (final IOException e)
		{
			Assert.fail(format("Cannot initialize test: {0}", e.getMessage()));
		}

		return tempfile;
	}

	protected Path getTestPath()
	{
		return folder.getRoot().toPath();
	}

	public Path provideRecursively(String resource) throws Exception

	{
		final Path base = Paths.get(getClass().getResource("/").toURI()).toAbsolutePath();
		final Path root = Paths.get(getClass().getResource(resource).toURI()).toAbsolutePath();

		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
			{
				final Path targetBase = folder.getRoot().toPath();
				final Path target = targetBase.resolve(base.relativize(dir));

				if (!Files.exists(target))
				{
					Files.createDirectory(target);
				}

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
			{
				final Path targetBase = folder.getRoot().toPath();
				final Path target = targetBase.resolve(base.relativize(file));

				Files.copy(file, target);

				return FileVisitResult.CONTINUE;
			}
		});

		// FIXME: Das muss besser gehen...
		return Paths.get(folder.getRoot().toPath().toAbsolutePath().toString() +
				Paths.get(resource).toString());
	}

	private String getRandomString()
	{
		return String.valueOf(RANDOM.nextLong()) + String.valueOf(RANDOM.nextLong());
	}

	protected void providePathForTree(String string) throws Exception
	{
		final Path root = Paths.get(getClass().getResource(string).toURI());

		Files.copy(root, Paths.get("/tmp/foo"), StandardCopyOption.REPLACE_EXISTING);

	}
}
