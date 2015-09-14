package de.jmens.ariadne.persistence.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class QueryBuilderTest
{
	private EntityManager entityManagerMock;
	private Query query;

	@Before
	public void setUp() throws Exception
	{
		query = mock(Query.class);
		entityManagerMock = mock(EntityManager.class);

		doReturn(query)
				.when(entityManagerMock)
				.createQuery(Mockito.anyString());
	}

	@Test
	public void testNoFilter() throws Exception
	{
		assertThat(QueryBuilder.query().buildHql().toLowerCase(), is("select t from tagentity t"));
	}

	@Test
	public void testSimpleFilter()
	{
		final String hql = QueryBuilder
				.query()
				.addRestriction("artist", "*metal*")
				.buildHql();

		assertThat(hql.toLowerCase(), stringContainsInOrder(listOf("select t from tagentity t where artist like :param")));
	}

	@Test
	public void testComplexFilter() throws Exception
	{
		final String hql = QueryBuilder
				.query()
				.addRestriction("artist", "foo*")
				.addRestriction("title", "bar")
				.buildHql();

		assertThat(hql.toLowerCase(), stringContainsInOrder(listOf("artist like :param", "and", "title = :param")));
	}

	@Test
	public void testBuildQueryWithoutEntityManager()
	{
		final Query query = QueryBuilder
				.query()
				.addRestriction("artist", "*metal*")
				.build();

		assertThat(query, nullValue());
	}

	@Test
	public void testBuildQuery()
	{

		final Query result = QueryBuilder
				.withEntityManager(entityManagerMock)
				.addRestriction("artist", "*metal*")
				.build();

		assertThat(result, is(query));
	}

	private List<String> listOf(String... strings)
	{
		return Arrays.asList(strings);
	}

	@Test
	public void testPagination() throws Exception
	{
		QueryBuilder
				.withEntityManager(entityManagerMock)
				.firstResult(1)
				.maxResults(5)
				.build();

		verify(query).setFirstResult(1);
		verify(query).setMaxResults(5);
	}
}
