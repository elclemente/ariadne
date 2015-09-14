package de.jmens.ariadne.persistence.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.jmens.ariadne.persistence.tag.TagDao;
import de.jmens.ariadne.service.Filter;
import de.jmens.ariadne.tag.TagEntity;
import de.jmens.ariadne.test.DbTest;

public class TagDaoTest extends DbTest
{
	private static final UUID FILEID_1 = UUID.fromString("1d582abf-a4fb-4175-8ee3-28f474a6f762");

	@Before
	public void setup() throws Exception
	{
		provideTestdata("tags.sql");
	}

	@Test
	public void testStoreEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		final TagEntity entity = new TagEntity();

		getTransaction().begin();
		dao.saveOrUpdate(entity);
		getTransaction().commit();

		assertThat(entity.getFileId(), notNullValue(UUID.class));
	}

	@Test
	public void testLoadEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		assertThat(dao.loadById(UUID.fromString("1d582abf-a4fb-4175-8ee3-28f474a6f762")).getFileId(), is(UUID.fromString("1d582abf-a4fb-4175-8ee3-28f474a6f762")));
		assertThat(dao.loadById(UUID.fromString("2d582abf-a4fb-4175-8ee3-28f474a6f762")).getFileId(), is(UUID.fromString("2d582abf-a4fb-4175-8ee3-28f474a6f762")));
		assertThat(dao.loadById(UUID.fromString("3d582abf-a4fb-4175-8ee3-28f474a6f762")).getFileId(), is(UUID.fromString("3d582abf-a4fb-4175-8ee3-28f474a6f762")));
		assertThat(dao.loadById(UUID.fromString("4d582abf-a4fb-4175-8ee3-28f474a6f762")).getFileId(), is(UUID.fromString("4d582abf-a4fb-4175-8ee3-28f474a6f762")));
		assertThat(dao.loadById(UUID.fromString("5d582abf-a4fb-4175-8ee3-28f474a6f762")).getFileId(), is(UUID.fromString("5d582abf-a4fb-4175-8ee3-28f474a6f762")));
		assertThat(dao.loadById(UUID.fromString("6d582abf-a4fb-4175-8ee3-28f474a6f762")), nullValue());
	}

	@Test
	public void testUpdateEntity() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		final TagEntity entity = dao.loadById(FILEID_1);
		final UUID id = UUID.randomUUID();
		entity.setAlbum("Foo");
		entity.setScanId(id);

		dao.saveOrUpdate(entity);

		final TagEntity result = dao.loadById(FILEID_1);

		assertThat(result.getAlbum(), is("Foo"));
		assertThat(result.getScanId(), is(id));
		assertThat(result.getFileId(), not(nullValue()));
	}

	@Test
	public void testUpdateDoesRespectFileId() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		final TagEntity tag = dao.loadById(FILEID_1);

		assertThat(tag.getFileId(), notNullValue());

		tag.setFileId(UUID.randomUUID());

		dao.saveOrUpdate(tag);
	}

	@Test
	public void testFindTags() throws Exception
	{
		final TagDao dao = new TagDao(getEntityManager());

		assertThat(dao.findTags(artistFilter("Demo")), hasSize(0));
		assertThat(dao.findTags(artistFilter("Demo Artist 1")), hasSize(1));
		assertThat(dao.findTags(artistFilter("Demo Artist 2")), hasSize(1));
		assertThat(dao.findTags(artistFilter("Demo Artist*")), hasSize(5));
	}

	private Filter artistFilter(String artist)
	{
		final Filter filter = new Filter();
		filter.setArtist(artist);
		return filter;
	}

}
