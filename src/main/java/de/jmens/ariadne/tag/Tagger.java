package de.jmens.ariadne.tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.jmens.ariadne.exception.CannotLoadFileException;
import de.jmens.ariadne.exception.CannotWriteTagsException;

public class Tagger
{
	private Mp3File mp3File;
	private SoundFile tag;

	Tagger()
	{
		super();
	}

	Tagger(Path path) throws CannotLoadFileException
	{
		try
		{
			mp3File = new Mp3File(path.toFile());

			this.tag = SoundFile.of(mp3File);
		}
		catch (final IOException | UnsupportedTagException | InvalidDataException e)
		{
			throw new CannotLoadFileException(e);
		}
	}

	public SoundFile getSoundFile()
	{
		return tag;
	}

	public File getFilepath()
	{
		return new File(mp3File.getFilename());
	}

	public static Optional<Tagger> load(Path testfile)
	{
		if (testfile == null)
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(new Tagger(testfile));
		}
		catch (final CannotLoadFileException e)
		{
			return Optional.empty();
		}
	}

	public boolean writeTags()
	{
		if (!Files.isWritable(Paths.get(mp3File.getFilename())))
		{
			return false;
		}

		try
		{
			final Path directory = Files.createTempDirectory("ariadne-temp-");
			final Path tempFilepath = directory.resolve(Paths.get(mp3File.getFilename()).getFileName());

			mp3File.removeId3v1Tag();
			mp3File.removeId3v2Tag();
			mp3File.setId3v2Tag(tag.toFileTag());
			mp3File.save(tempFilepath.toString());

			Files.delete(Paths.get(mp3File.getFilename()));
			Files.move(tempFilepath, Paths.get(mp3File.getFilename()));

			return true;
		}
		catch (final IOException | NotSupportedException e)
		{
			throw new CannotWriteTagsException(e);
		}

	}
}
