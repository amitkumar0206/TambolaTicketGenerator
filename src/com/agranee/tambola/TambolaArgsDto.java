package com.agranee.tambola;

import java.io.File;

public class TambolaArgsDto
{

	private int numberofTickets;
	private File file;
	private String topMessage;
	private String bottomMessage;

	public int getNumberofTickets()
	{
		return numberofTickets;
	}

	public void setNumberofTickets( int numberofTickets )
	{
		this.numberofTickets = numberofTickets;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile( File file )
	{
		this.file = file;
	}

	public String getTopMessage()
	{
		return topMessage;
	}

	public void setTopMessage( String topMessage )
	{
		this.topMessage = topMessage;
	}

	public String getBottomMessage()
	{
		return bottomMessage;
	}

	public void setBottomMessage( String bottomMessage )
	{
		this.bottomMessage = bottomMessage;
	}

}
