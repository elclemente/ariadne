package de.jmens.ariadne.tag;

import java.nio.file.Path;
import java.util.Optional;

import com.mpatric.mp3agic.Mp3File;

import de.jmens.ariadne.exception.CannotLoadFileException;

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
}
