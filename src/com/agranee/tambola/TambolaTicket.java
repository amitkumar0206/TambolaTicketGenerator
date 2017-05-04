package com.agranee.tambola;

import java.io.Serializable;

public class TambolaTicket implements Serializable, Comparable
{
	private static final long serialVersionUID = 8972721508378139350L;

	private String[][] numbersArr;

	@Override
	public String toString()
	{
		if ( numbersArr != null )
		{
			return numbersArr.toString();
		}
		return "";
	}

	@Override
	public boolean equals( Object ticket2 )
	{
		return numbersArr.equals( ticket2.toString() );
	}

	public String[][] getNumbersArr()
	{
		return numbersArr;
	}

	public void setNumbersArr( String[][] numbersArr )
	{
		this.numbersArr = numbersArr;
	}

	@Override
	public int compareTo( Object o )
	{
		return this.equals( (TambolaTicket) o ) ? 0 : 1;
	}

}
