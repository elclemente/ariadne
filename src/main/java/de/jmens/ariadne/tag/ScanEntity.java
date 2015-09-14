package de.jmens.ariadne.tag;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scan")
public class ScanEntity
{
	@Id
	@Column(name = "scan_id")
	private UUID scanId;

	@Column(name = "start")
	private Date start;

	@Column(name = "finish")
	private Date finish;

	@Column(name = "source")
	private File source;

	public File getSource()
	{
		return source;
	}

	public void setSource(File source)
	{
		this.source = source;
	}

	public UUID getScanId()
	{
		return scanId;
	}

	public void setScanId(UUID scanId)
	{
		this.scanId = scanId;
	}

	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public Date getFinish()
	{
		return finish;
	}

	public void setFinish(Date finish)
	{
		this.finish = finish;
	}

}
