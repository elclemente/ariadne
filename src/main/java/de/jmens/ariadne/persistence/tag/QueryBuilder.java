package de.jmens.ariadne.persistence.tag;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryBuilder
{
	public static QueryBuilder query()
	{
		return new QueryBuilder();
	}

	public static QueryBuilder withEntityManager(EntityManager entityManager)
	{
		return new QueryBuilder(entityManager);

	}

	private final List<Parameter> parameters = new ArrayList<>();
	private final StringBuilder hqlBuilder = new StringBuilder("select t from TagEntity t ");
	private boolean firstFilter = true;
	private final EntityManager entityManager;
	private int counter = 0;
	private int firstResult;
	private int maxResults;

	private QueryBuilder()
	{
		this(null);
	}

	private QueryBuilder(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public QueryBuilder addRestriction(String field, String pattern)
	{
		final String parameterName = "param" + ++counter;

		final String value = pattern.replaceAll("\\*", "%");
		final String filter = buildFilter(field, value, parameterName);

		if (firstFilter)
		{
			firstFilter = false;
			hqlBuilder.append("where ");
		}
		else
		{
			hqlBuilder.append("and ");
		}

		hqlBuilder.append(filter);

		parameters.add(new Parameter(parameterName, value));

		return this;
	}

	private String buildFilter(String field, final String expression, String parameterName)
	{
		final String comparator;

		if (expression.contains("%") || expression.contains("*"))
		{
			comparator = "like";
		}
		else
		{
			comparator = "=";
		}

		return format("{0} {1} :{2} ", field, comparator, parameterName);
	}

	public String buildHql()
	{
		return trimToEmpty(hqlBuilder.toString());
	}

	public Query build()
	{
		if (entityManager == null)
		{
			return null;
		}

		final Query query = entityManager.createQuery(hqlBuilder.toString());

		for (final Parameter parameter : parameters)
		{
			query.setParameter(parameter.getName(), parameter.getValue());
		}

		if (firstResult > 0)
		{
			query.setFirstResult(firstResult);
		}

		if (maxResults > 0)
		{
			query.setMaxResults(maxResults);
		}

		return query;
	}

	public QueryBuilder firstResult(int firstResult)
	{
		if (firstResult > 0)
		{
			this.firstResult = firstResult;
		}

		return this;
	}

	public QueryBuilder maxResults(int maxResults)
	{
		if (maxResults > 0)
		{
			this.maxResults = maxResults;
		}

		return this;
	}

	static class Parameter
	{
		private final String name;
		private final String value;

		public Parameter(String name, String value)
		{
			super();
			this.name = name;
			this.value = value;
		}

		public String getName()
		{
			return name;
		}

		public String getValue()
		{
			return value;
		}

	}

}
