package de.jmens.ariadne.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.jmens.ariadne.tag.ScanEntity;
import de.jmens.ariadne.test.DbTest;

public class ScanDaoTest extends DbTest
{
	private ScanDao dao;

	@Before
	public void setUp() throws Exception
	{
		dao = new ScanDao(getEntityManager());

		provideTestdata("scans.sql");
	}

	@Test
	public void testNewScan()
	{

		final Date testStart = new Date();
		final UUID scanId = UUID.randomUUID();

		getTransaction().begin();
		final ScanEntity scan = new ScanDao(getEntityManager()).newScan(scanId);
		getTransaction().commit();

		assertThat(scan.getScanId(), not(nullValue()));
		assertThat(scan.getScanId(), is(scanId));
		assertThat(scan.getStart().before(testStart), is(false));
		assertThat(scan.getFinish(), nullValue());
	}

	@Test
	public void testNewScanWithNonUniqueId()
	{
		final UUID scanId = UUID.randomUUID();

		getTransaction().begin();
		final ScanEntity scan1 = dao.newScan(scanId);
		final ScanEntity scan2 = dao.newScan(scanId);
		getTransaction().rollback();

		assertThat(scan1, not(nullValue()));
		assertThat(scan2, nullValue());
	}

	@Test
	public void testFinish() throws Exception
	{
		assertThat(dao.finishScan(null), nullValue());

		final ScanEntity scan = dao.newScan();

		final long finish1 = dao.finishScan(scan).getFinish().getTime();
		Thread.sleep(5);
		final long finish2 = dao.finishScan(scan).getFinish().getTime();

		assertThat(finish1, is(finish2));
	}

	@Test
	public void testGetLastScan() throws Exception
	{
		assertThat(dao.getLastScan().getScanId(), is(UUID.fromString("32345678-90ab-cdef-1234-567890abcdef")));
	}
}
