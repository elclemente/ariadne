package de.jmens.ariadne.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import de.jmens.ariadne.tag.TagEntity;
import de.jmens.ariadne.test.DbTest;

public class TagDaoTest extends DbTest
{
	@Before
	public void setup() throws Exception
	{
		getTransaction().begin();
		provideTestdata("tags.sql");
		getTransaction().commit();
	}

	@Test
	public void testStoreEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		final TagEntity entity = new TagEntity();

		getTransaction().begin();
		dao.store(entity);
		getTransaction().commit();

		assertThat(entity.getId(), notNullValue(Integer.class));
	}

	@Test
	public void testLoadEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		assertThat(dao.loadById(1).getId(), is(1));
		assertThat(dao.loadById(2).getId(), is(2));
		assertThat(dao.loadById(3).getId(), is(3));
		assertThat(dao.loadById(4).getId(), is(4));
		assertThat(dao.loadById(5).getId(), is(5));
		assertThat(dao.loadById(6), nullValue());
	}

	@Test
	public void testUpdateEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());
		final TagEntity entity = dao.loadById(1);
		entity.setAlbum("Foo");
		dao.store(entity);

		assertThat(dao.loadById(1).getAlbum(), is("Foo"));
	}

}
