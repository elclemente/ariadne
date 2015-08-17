package de.jmens.ariadne.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class FileTest {

    private static final Random RANDOM = new Random();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    protected File provideTestfile(String name) {
	return new File(providePathFor(name).toString());
    }

    protected Path providePathFor(String name) {
	final Path tempfile = Paths.get(folder.getRoot().getAbsolutePath(), getRandomString() + ".mp3");

	try (InputStream mp3file = getClass().getResourceAsStream(name)) {
	    Files.copy(mp3file, tempfile);
	} catch (final IOException e) {
	    Assert.fail("Cannot initialize test: " + e.getMessage());
	}

	return tempfile;
    }

    private String getRandomString()
    {
	return String.valueOf(RANDOM.nextLong()) + String.valueOf(RANDOM.nextLong());
    }

}
