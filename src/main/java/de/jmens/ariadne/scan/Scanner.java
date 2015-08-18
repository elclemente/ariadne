package de.jmens.ariadne.scan;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.exception.CannotReadFilesException;

public class Scanner
{
	protected Path entryPoint;
	protected Consumer<Path> consumer;

	private static final Logger LOG = LoggerFactory.getLogger(Scanner.class);

	Scanner()
	{
		super();
	}

	public Scanner withEntrypoint(Path entrypoint)
	{
		this.entryPoint = entrypoint;

		return this;
	}

	public Scanner applies(Consumer<Path> consumer)
	{
		this.consumer = consumer;

		return this;
	}

	public static Scanner newScanner()
	{
		return new Scanner();
	}

	public void scan()
	{
		try
		{
			Files.walkFileTree(entryPoint, new FileVisitor());
		}
		catch (final IOException e)
		{
			throw new CannotReadFilesException(e);
		}
	}

	private class FileVisitor extends SimpleFileVisitor<Path>
	{
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException
		{
			if (attr.isSymbolicLink())
			{
				LOG.debug("Skipping symlink {}", file);
			}
			else if (attr.isRegularFile())
			{
				final String type = Files.probeContentType(file);

				if ("audio/mpeg".equalsIgnoreCase(type))
				{
					consumer.accept(file);
				}
				else
				{
					LOG.info("Skipping non-music file {}", file);
				}
			}
			else
			{
				LOG.debug("Skipping {}", file);
			}

			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException e)
		{
			LOG.error("Error while reading {}: {}", file, e.getMessage());

			return CONTINUE;
		}
	}

}
