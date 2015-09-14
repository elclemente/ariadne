package de.jmens.ariadne.persistence;

import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.tag.ScanEntity;

@Stateless
public class ScanDao
{
	@PersistenceContext(unitName = "ariadne")
	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ScanDao.class);

	public ScanDao()
	{
		super();
	}

	ScanDao(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public ScanEntity newScan()
	{
		return newScan(UUID.randomUUID());
	}

	public ScanEntity newScan(UUID scanId)
	{
		final ScanEntity entity = new ScanEntity();

		entity.setScanId(scanId);
		entity.setStart(new Date());

		try
		{
			entityManager.persist(entity);

			return entity;
		}
		catch (final PersistenceException e)
		{
			LOGGER.warn("Cannot persist scan. ScanID {} is propably not unique and exists in databse.", scanId);

			return null;
		}
	}

	public ScanEntity finishScan(ScanEntity scan)
	{
		if (scan == null)
		{
			return null;
		}

		if (scan.getFinish() == null)
		{
			scan.setFinish(new Date());
			entityManager.persist(scan);
		}

		return scan;
	}

	public ScanEntity getLastScan()
	{
		ScanEntity result;

		try
		{
			result = entityManager
					.createQuery("select s from ScanEntity s order by start desc", ScanEntity.class)
					.setMaxResults(1)
					.getSingleResult();
		}
		catch (final NoResultException e)
		{
			LOGGER.debug("No ScanEntity found");

			return null;
		}
		catch (final NonUniqueResultException e)
		{
			LOGGER.warn("More than one last ScanEntitiy found");
			return null;
		}

		LOGGER.debug("Returning last ScanEntity: {}", result.getScanId());

		return result;
	}

}
