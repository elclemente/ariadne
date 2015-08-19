package de.jmens.ariadne.scan;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.tag.Tag;
import de.jmens.ariadne.tag.Tagger;

public class TagSynchronization
{
	private static final Logger LOG = LoggerFactory.getLogger(TagSynchronization.class);

	public void scan(Path root)
	{
		final UUID uuid = UUID.randomUUID();

		// TODO: Persist scan with uuid

		Scanner
				.newScanner()
				.withEntrypoint(root)
				.applies(null)
				.scan();

		class Synchronizer implements Consumer<Path>
		{
			@Override
			public void accept(Path file)
			{
				final Optional<Tagger> tagger = Tagger.load(file);

				if (tagger.isPresent())
				{
					synchronizeTags(tagger.get());
				}
				else
				{
					LOG.error("Error while reading {}", file);
				}
			}

			private void synchronizeTags(Tagger tagger)
			{
				final Tag tag = tagger.getTag();

				tag.setScanId(uuid);
				tag.setFileId(UUID.randomUUID());

				tagger.writeTags();

			}
		}
	}
}
