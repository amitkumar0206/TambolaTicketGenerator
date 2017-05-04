package com.agranee.tambola;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class TambolaMain
{
	private static int MAX_NUMBER = 75;
	private static int MAX_ROW = 3;

	private static Set<TambolaTicket> uniqueTickets = new TreeSet<>();

	private static int oneToTenCount = 0;
	private static int tenToTwentyCount = 0;
	private static int twentyToThirtyCount = 0;
	private static int thirtyToFortyCount = 0;
	private static int fortyToFiftyCount = 0;
	private static int fiftyToSixtyCount = 0;
	private static int sixtyToSeventyCount = 0;
	private static int seventyPlusCount = 0;
	private static TambolaArgsDto dto;
	private static JFrame f;

	public static void startWait()
	{
		f = new JFrame( "Waiting" );
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = f.getContentPane();
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue( 25 );
		progressBar.setStringPainted( true );
		Border border = BorderFactory.createTitledBorder( "Generating..." );
		progressBar.setBorder( border );
		content.add( progressBar, BorderLayout.NORTH );
		f.setSize( 300, 100 );
		f.setVisible( true );
	}

	public static void main( String[] args )
	{

		try
		{
			//get Argument
			ArgumentAcceptingPanel panel = new ArgumentAcceptingPanel();
			TambolaArgsDto dto = panel.getValues();

			//start ProgressBar
			startWait();

			SortedSet<Integer> ticketArr;

			Comparator<Integer> comp = new Comparator<Integer>()
			{
				@Override
				public int compare( Integer o1, Integer o2 )
				{
					if ( o1.intValue() > o2.intValue() )
					{
						return 1;
					}
					else if ( o1.intValue() == o2.intValue() )
					{
						return 0;
					}
					else
					{
						return -1;
					}
				}
			};

			int number = 0;

			while ( uniqueTickets.size() < dto.getNumberofTickets() )
			{
				oneToTenCount = 0;
				tenToTwentyCount = 0;
				twentyToThirtyCount = 0;
				thirtyToFortyCount = 0;
				fortyToFiftyCount = 0;
				fiftyToSixtyCount = 0;
				sixtyToSeventyCount = 0;
				seventyPlusCount = 0;
				ticketArr = new TreeSet<Integer>( comp );

				while ( ticketArr.size() < 15 )
				{
					number = getRandomNumber( 1, MAX_NUMBER );

					if ( isCountValid( number ) )
					{
						ticketArr.add( number );
					}
				}
				//check if at least 1 number is generated from each column
				TambolaTicket ticket = createTicket( ticketArr );
				if ( validateTicket( ticket ) )
				{
					uniqueTickets.add( ticket );
				}
			}
			PDFGenerator.createPDF( uniqueTickets, dto );
		}
		finally
		{
			f.dispose();
			JOptionPane.showMessageDialog( null, "Thanks for Using Tambola Ticket Generator! Enjoy your Game!", "Completed",
					JOptionPane.INFORMATION_MESSAGE, null );
			System.exit( 0 );
		}
	}

	private static boolean validateTicket( TambolaTicket ticket )
	{
		boolean flag = false;
		if ( oneToTenCount != 0 && tenToTwentyCount != 0 && twentyToThirtyCount != 0 && thirtyToFortyCount != 0 && fortyToFiftyCount != 0
				&& fiftyToSixtyCount != 0 && sixtyToSeventyCount != 0 && seventyPlusCount != 0 )
		{
			flag = true;
		}

		String[][] numbersArr = ticket.getNumbersArr();
		if ( flag )
		{
			for ( int i = 0; i < 3; ++i )
			{
				flag = true;
				int count = 0;
				for ( int j = 0; j < numbersArr[ i ].length; ++j )
				{
					if ( numbersArr[ i ][ j ] != null )
					{
						++count;
					}
				}

				if ( count != 5 )
				{
					flag = false;
					break;
				}
			}

		}

		return flag;
	}

	private static boolean isCountValid( int number )
	{
		boolean flag = false;

		if ( number > 0 && number < 10 && oneToTenCount < MAX_ROW )
		{
			oneToTenCount++;
			flag = true;
		}
		else if ( number > 9 && number < 20 && tenToTwentyCount < MAX_ROW )
		{
			tenToTwentyCount++;
			flag = true;
		}
		else if ( number > 19 && number < 30 && twentyToThirtyCount < MAX_ROW )
		{
			twentyToThirtyCount++;
			flag = true;
		}
		else if ( number > 29 && number < 40 && thirtyToFortyCount < MAX_ROW )
		{
			thirtyToFortyCount++;
			flag = true;
		}
		else if ( number > 39 && number < 50 && fortyToFiftyCount < MAX_ROW )
		{
			fortyToFiftyCount++;
			flag = true;
		}
		else if ( number > 49 && number < 60 && fiftyToSixtyCount < MAX_ROW )
		{
			fiftyToSixtyCount++;
			flag = true;
		}
		else if ( number > 59 && number < 70 && sixtyToSeventyCount < MAX_ROW )
		{
			sixtyToSeventyCount++;
			flag = true;
		}
		else if ( number > 69 && number < 76 && seventyPlusCount < MAX_ROW )
		{
			seventyPlusCount++;
			flag = true;
		}

		return flag;

	}

	private static void putInMap( String key, String value, Map<String, List<String>> map )
	{
		List<String> numList = map.get( key );
		if ( numList == null )
		{
			numList = new LinkedList<>();
		}
		numList.add( value );
		map.put( key, numList );
	}

	private static TambolaTicket createTicket( Set<Integer> numbersSet )
	{

		TambolaTicket ticket = new TambolaTicket();
		String[][] numbersArr = new String[ 3 ][ 8 ];

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> numList = null;

		//get the count of numbers in range
		for ( Integer number : numbersSet )
		{

			if ( number > 0 && number < 10 )
			{
				putInMap( "1-10", number.toString(), map );
			}
			else if ( number > 9 && number < 20 )
			{
				putInMap( "10-20", number.toString(), map );
			}
			else if ( number > 19 && number < 30 )
			{
				putInMap( "20-30", number.toString(), map );
			}
			else if ( number > 29 && number < 40 )
			{
				putInMap( "30-40", number.toString(), map );
			}
			else if ( number > 39 && number < 50 )
			{
				putInMap( "40-50", number.toString(), map );
			}
			else if ( number > 49 && number < 60 )
			{
				putInMap( "50-60", number.toString(), map );
			}
			else if ( number > 59 && number < 70 )
			{
				putInMap( "60-70", number.toString(), map );
			}
			else if ( number > 69 && number < 76 )
			{
				putInMap( "70+", number.toString(), map );
			}
		}

		int yIndex = 0;
		Set<String> keySet = map.keySet();
		for ( String key : keySet )
		{
			numList = map.get( key );

			if ( "1-10".equals( key ) )
			{
				yIndex = 0;
			}
			else if ( "10-20".equals( key ) )
			{
				yIndex = 1;
			}
			else if ( "20-30".equals( key ) )
			{
				yIndex = 2;
			}
			else if ( "30-40".equals( key ) )
			{
				yIndex = 3;
			}
			else if ( "40-50".equals( key ) )
			{
				yIndex = 4;
			}
			else if ( "50-60".equals( key ) )
			{
				yIndex = 5;
			}
			else if ( "60-70".equals( key ) )
			{
				yIndex = 6;
			}
			else
			{
				yIndex = 7;
			}

			putInArray( numList, yIndex, numbersArr );

		}

		ticket.setNumbersArr( numbersArr );

		return ticket;

	}

	private static void putInArray( List<String> numList, int yIndex, String[][] arr )
	{
		int listSize = numList.size();

		if ( listSize == 1 )
		{
			int xIndex = getRandomNumber( 0, 2 );
			arr[ xIndex ][ yIndex ] = numList.get( 0 );
		}
		else if ( listSize == 2 )
		{
			while ( true )
			{

				int xIndex1 = getRandomNumber( 0, 2 );
				int xIndex2 = getRandomNumber( 0, 2 );

				if ( xIndex1 < xIndex2 )
				{
					arr[ xIndex1 ][ yIndex ] = numList.get( 0 );
					arr[ xIndex2 ][ yIndex ] = numList.get( 1 );
					break;
				}
			}
		}
		else
		{
			arr[ 0 ][ yIndex ] = numList.get( 0 );
			arr[ 1 ][ yIndex ] = numList.get( 1 );
			arr[ 2 ][ yIndex ] = numList.get( 2 );
		}
	}

	private static Integer getRandomNumber( int min, int max )
	{
		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt( ( max - min ) + 1 ) + min;

		return randomNum;
	}
}
