package de.jmens.ariadne.test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.BeforeClass;

public class DbTest extends FileTest
{

	private static final String SCHEMA_RESOURCE = "/db/schema.sql";

	private static EntityManagerFactory entityManagerFactory;
	private final EntityManager entityManager;

	private final EntityTransaction transaction;

	@BeforeClass
	public static void setupClass() throws Exception
	{
		initializeEntitymanager();
	}

	protected static void initializeEntitymanager() throws Exception
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

		entityManagerFactory = Persistence.createEntityManagerFactory("ariadne-test", properties);

		installSchema();
	}

	protected static void installSchema() throws Exception
	{
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.createNativeQuery(readResource(DbTest.class, SCHEMA_RESOURCE)).executeUpdate();
		transaction.commit();

		entityManager.close();
	}

	public DbTest()
	{
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
	}

	protected void provideTestdata(String resource) throws Exception
	{
		getTransaction().begin();
		executeSqlResource(resource);
		getTransaction().commit();
	}

	private void executeSqlResource(String resource) throws Exception
	{
		final Query query = entityManager.createNativeQuery(readResource(resource));

		query.executeUpdate();
	}

	private String readResource(String resource) throws Exception
	{
		return readResource(getClass(), resource);
	}

	private static String readResource(Class<?> clazz, String resource) throws Exception
	{
		final URL resourceUrl = clazz.getResource(resource);
		final byte[] bytes = Files.readAllBytes(Paths.get(resourceUrl.toURI()));
		return new String(bytes);
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public EntityTransaction getTransaction()
	{
		return transaction;
	}
}
