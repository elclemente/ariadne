package de.jmens.ariadne.scan;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.persistence.ScanDao;
import de.jmens.ariadne.persistence.tag.TagDao;
import de.jmens.ariadne.tag.ScanEntity;
import de.jmens.ariadne.tag.SoundFile;
import de.jmens.ariadne.tag.Tagger;

@Stateless
public class Importer
{
	private static final Logger LOG = LoggerFactory.getLogger(Importer.class);

	@Inject
	private ScanDao scanDao;

	@Inject
	private TagDao tagDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(Importer.class);

	public Importer()
	{
		super();
	}

	public Importer(ScanDao scanDao)
	{
		this.scanDao = scanDao;
	}

	public ScanEntity scan(Path root)
	{
		final ScanEntity scan = scanDao.newScan();

		Scanner
				.newScanner()
				.withEntrypoint(root)
				.applies(p -> importFile(p, scan.getScanId()))
				.scan();

		scanDao.finishScan(scan);

		return scan;

	}

	private void importFile(Path file, UUID scanId)
	{
		final Optional<Tagger> tagger = Tagger.load(file);

		if (tagger.isPresent())
		{
			updateTags(tagger.get(), scanId);
		}
		else
		{
			LOG.error("Error while reading {}", file);
		}
	}

	private void updateTags(Tagger tagger, UUID scanId)
	{
		final SoundFile tag = tagger.getSoundFile();

		tag.setScanId(scanId);
		tag.setPath(tagger.getFilepath());

		if (tag.getFileId() == null)
		{
			final UUID fileId = UUID.randomUUID();

			LOGGER.debug("Updating file id to: {}", fileId);

			tag.setFileId(fileId);
		}
		else
		{
			LOGGER.debug("Keeping existing file id: {}", tag.getFileId());
		}

		tagDao.saveOrUpdate(tag.toEntity());

		tagger.writeTags();

	}

}
