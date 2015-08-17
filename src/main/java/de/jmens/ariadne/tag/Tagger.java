package de.jmens.ariadne.tag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;

import de.jmens.ariadne.exception.CannotLoadFileException;
import de.jmens.ariadne.exception.CannotWriteTagsException;

public class Tagger
{
    private Mp3File mp3File;
    private ID3Tag tag;

    Tagger()
    {

    }

    Tagger(Path path) throws CannotLoadFileException
    {
	try
	{
	    mp3File = new Mp3File(path.toFile());

	    if (mp3File.hasId3v1Tag())
	    {
		this.tag = ID3Tag.of(mp3File.getId3v1Tag());
	    }
	    else if (mp3File.hasId3v2Tag())
	    {
		this.tag = ID3Tag.of(mp3File.getId3v2Tag());
	    }
	    else
	    {
		this.tag = ID3Tag.emtpyTag();
	    }
	}
	catch (final Exception e)
	{
	    throw new CannotLoadFileException(e);
	}
    }

    public ID3Tag getTag()
    {
	return tag;
    }

    public static Optional<Tagger> load(Path testfile)
    {
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
	if (! Files.isWritable(Paths.get(mp3File.getFilename())))
	{
	    return false;
	}

	try
	{
	    final Path directory = Files.createTempDirectory("ariadne-temp-");
	    final Path tempFilepath = directory.resolve(Paths.get(mp3File.getFilename()).getFileName());

	    mp3File.removeId3v1Tag();
	    mp3File.removeId3v2Tag();
	    mp3File.setId3v2Tag(ID3Tag.toId3v2Tag(tag));
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
