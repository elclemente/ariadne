package de.jmens.ariadne.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import de.jmens.ariadne.tag.ID3TagEntity;

public class PersistenceTest
{

    @Test
    public void test()
    {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ariadne");

	final EntityManager em = emf.createEntityManager();
	final EntityTransaction trx = em.getTransaction();

	final ID3TagEntity entity = new ID3TagEntity();
	entity.setAlbum("foo");
	entity.setArtist("bar");

	trx.begin();
	em.persist(entity);
	trx.commit();

	final EntityTransaction transaction = em.getTransaction();

	transaction.begin();
	final List result = em.createQuery("Select t from de.jmens.ariadne.tag.ID3TagEntity t").getResultList();
	System.out.println(result);
	transaction.commit();

	em.close();
	emf.close();
    }

}
