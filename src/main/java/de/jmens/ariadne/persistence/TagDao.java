package de.jmens.ariadne.persistence;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.jmens.ariadne.tag.TagEntity;

public final class TagDao
{
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

	public TagEntity loadById(UUID id)
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

	public void store(TagEntity tagEntitiy)
	{
		entityManager.persist(tagEntitiy);
	}

	public static void main(String[] args)
	{
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.fromString("1e5822bf-a4fb-4175-8ee3-28f474a6f762"));
	}

}
