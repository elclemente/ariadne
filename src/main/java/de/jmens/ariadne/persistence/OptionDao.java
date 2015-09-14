package de.jmens.ariadne.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class OptionDao
{
	@PersistenceContext(unitName = "ariadne")
	private EntityManager entityManager;

	public String loadByName(String name)
	{
		final String hql = "select o from OptionEntity o where name = :name";

		try
		{
			final OptionEntity option = entityManager
					.createQuery(hql, OptionEntity.class)
					.setParameter("name", name)
					.getSingleResult();

			return option.getValue();
		}
		catch (final NoResultException e)
		{
			return "";
		}
	}
}
