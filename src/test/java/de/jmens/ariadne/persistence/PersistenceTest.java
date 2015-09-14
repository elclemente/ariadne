package de.jmens.ariadne.persistence;

import static java.text.MessageFormat.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jmens.ariadne.tag.TagEntity;

public class PersistenceTest
{
	private static EntityManagerFactory emf;

	private EntityManager em;

	@BeforeClass
	public static void setupClass()
	{
		final Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.hbm2ddl.auto", "verify");
		properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
		properties.put("javax.persistence.jdbc.url", "jdbc:h2:~/ariadne");
		properties.put("javax.persistence.jdbc.user", "");
		properties.put("javax.persistence.jdbc.password", "");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");

		emf = Persistence.createEntityManagerFactory("ariadne-test", properties);
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

		final TagEntity entity = new TagEntity();
		entity.setAlbum("foo");
		entity.setArtist("bar");

		transaction.begin();
		em.persist(entity);
		transaction.commit();

		assertThat(entity.getFileId(), notNullValue(UUID.class));

		final Query query = em.createQuery(format("Select t from {0} t where file_id = :id", TagEntity.class.getSimpleName()));

		query.setParameter("id", entity.getFileId());
		@SuppressWarnings("unchecked")
		final List<TagEntity> result = query.getResultList();

		assertThat(result, hasSize(1));
		assertThat(result.get(0).getAlbum(), is("foo"));
		assertThat(result.get(0).getArtist(), is("bar"));
		assertThat(result.get(0).getTrack(), isEmptyOrNullString());
	}

}
