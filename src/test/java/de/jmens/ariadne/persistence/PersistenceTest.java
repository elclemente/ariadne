package de.jmens.ariadne.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jmens.ariadne.tag.ID3TagEntity;

public class PersistenceTest
{
    private static EntityManagerFactory emf;

    private EntityManager em;

    @BeforeClass
    public static void setupClass()
    {
	emf = Persistence.createEntityManagerFactory("ariadne");
    }

    @AfterClass
    public static void tearDownClass()
    {
	emf.close();
    }

    @Before
    public void setUp()
    {
	em = emf.createEntityManager();
    }

    @After
    public void tearDown()
    {
	em.close();
    }

    @Test
    public void testPersistenceSetup()
    {
	final EntityTransaction transaction = em.getTransaction();

	final ID3TagEntity entity = new ID3TagEntity();
	entity.setAlbum("foo");
	entity.setArtist("bar");

	transaction.begin();
	em.persist(entity);
	transaction.commit();

	assertThat(entity.getId(), notNullValue(Integer.class));

	@SuppressWarnings("unchecked")
	final List<ID3TagEntity> result = em.createQuery("Select t from de.jmens.ariadne.tag.ID3TagEntity t").getResultList();

	assertThat(result, hasSize(1));
	assertThat(result.get(0).getAlbum(), is("foo"));
	assertThat(result.get(0).getArtist(), is("bar"));
	assertThat(result.get(0).getTrack(), isEmptyOrNullString());
    }

}
