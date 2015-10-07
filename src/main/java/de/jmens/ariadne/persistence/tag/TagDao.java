package de.jmens.ariadne.persistence.tag;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmens.ariadne.service.Filter;
import de.jmens.ariadne.tag.TagEntity;

@Stateless
public class TagDao
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TagDao.class);

	@PersistenceContext(unitName = "ariadne")
	private EntityManager entityManager;

	public TagDao()
	{
		super();
	}

	TagDao(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public TagEntity loadById(int id)
	{
		try
		{
			final Query query = entityManager.createQuery("select t from TagEntity t where id = :id");

			query.setParameter("id", id);

			return (TagEntity) query.getSingleResult();
		}
		catch (final NoResultException e)
		{
			return null;
		}
	}

	public void saveOrUpdate(TagEntity tag)
	{
		try
		{
			entityManager.merge(tag);
		}
		catch (final IllegalArgumentException e)
		{
			entityManager.persist(tag);
		}
	}

	public List<TagEntity> findTags(Filter filter)
	{
		LOGGER.info("Query for TagEntities with filter: {}", filter);

		final Query query = QueryBuilder
				.withEntityManager(entityManager)
				.addRestriction("artist", filter.getArtist())
				.addRestriction("genre", filter.getGenre())
				.addRestriction("album", filter.getAlbum())
				.addRestriction("title", filter.getTitle())
				.addRestriction("year", filter.getYear())
				.addRestriction("track", filter.getTrack())
				.firstResult(filter.getFirstResult())
				.maxResults(filter.getMaxResults())
				.build();

		@SuppressWarnings("unchecked")
		final List<TagEntity> result = query.getResultList();

		LOGGER.info("Filter matches {} entries", result.size());

		return result;
	}
}
