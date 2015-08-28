package de.jmens.ariadne.scan;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.persistence.ScanDao;
import de.jmens.ariadne.tag.ScanEntity;
import de.jmens.ariadne.tag.Tag;
import de.jmens.ariadne.tag.Tagger;

public class Importer
{
	private static final Logger LOG = LoggerFactory.getLogger(Importer.class);

	private final UUID scanId = UUID.randomUUID();

	@RequestScoped
	private ScanDao scanDao;

	public Importer()
	{
		super();
	}

	public Importer(ScanDao scanDao)
	{
		this.scanDao = scanDao;
	}

	public UUID scan(Path root)
	{
		final ScanEntity scan = scanDao.newScan(scanId);

		Scanner
				.newScanner()
				.withEntrypoint(root)
				.applies(p -> importFile(p))
				.scan();

		scanDao.finishScan(scan);

		return scanId;

	}

	private void importFile(Path file)
	{
		final Optional<Tagger> tagger = Tagger.load(file);

		if (tagger.isPresent())
		{
			updateTags(tagger.get());
		}
		else
		{
			LOG.error("Error while reading {}", file);
		}
	}

	private void updateTags(Tagger tagger)
	{
		final Tag tag = tagger.getTag();

		tag.setScanId(scanId);

		if (tag.getFileId() == null)
		{
			tag.setFileId(UUID.randomUUID());
		}

		tagger.writeTags();

	}

}
