package com.agranee.tambola;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator
{

	public static void createPDF( Set<TambolaTicket> arr, TambolaArgsDto dto )
	{

		Document doc = new Document();
		PdfWriter docWriter = null;

		try
		{
			String temp = null;
			String text = null;

			//special font sizes
			Font bfBold12 = new Font( FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor( 0, 0, 0 ) );
			Font bf12 = new Font( FontFamily.TIMES_ROMAN, 30, Font.BOLD, new BaseColor( 0, 0, 0 ) );

			//file path
			docWriter = PdfWriter.getInstance( doc, new FileOutputStream( dto.getFile() ) );

			//document header attributes
			doc.addAuthor( "Amit Kumar" );
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator( "Agranee Solutions Ltd." );
			doc.addTitle( "Tambola Tickets" );
			doc.setPageSize( PageSize.LETTER );

			//open document
			doc.open();

			Paragraph paragraph = new Paragraph( "\n" );

			//specify column widths
			float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};

			int count = 0;

			List<List<TambolaTicket>> groupList = groupTickets( arr );

			for ( List<TambolaTicket> list : groupList )
			{
				for ( TambolaTicket ticket : list )
				{
					String[][] array = ticket.getNumbersArr();
					//create PDF table with the given widths
					PdfPTable table = new PdfPTable( columnWidths );

					// set table width a percentage of the page width
					table.setWidthPercentage( 100f );

					//insert column headings
					insertCell( table, "", Element.ALIGN_LEFT, 2, bfBold12 );
					insertCell( table, dto.getTopMessage(), Element.ALIGN_CENTER, 4, bfBold12 );
					insertCell( table, "Ticket Number: " + ( count + 1 ), Element.ALIGN_RIGHT, 2, bfBold12 );

					table.setHeaderRows( 1 );

					//just data to fill 

					for ( int x = 0; x < 3; x++ )
					{
						for ( int y = 0; y < 8; ++y )
						{
							text = "";
							temp = array[ x ][ y ];
							if ( temp != null )
							{
								text += temp;
							}
							insertCell( table, text, Element.ALIGN_CENTER, 1, bf12 );
						}
					}

					//merge the cells to create a footer for that section
					insertCell( table, dto.getBottomMessage(), Element.ALIGN_CENTER, 8, bfBold12 );
					// add the paragraph to the document
					paragraph.add( table );
					paragraph.add(
							"\n-----------------------------------------------------------------------------------------------------------------------------------\n\n" );

					count++;
				}

				doc.add( paragraph );
				paragraph = new Paragraph( "\n" );
				doc.newPage();
			}

		}
		catch ( DocumentException dex )
		{
			dex.printStackTrace();
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			if ( doc != null )
			{
				//close the document
				doc.close();
			}
			if ( docWriter != null )
			{
				//close the writer
				docWriter.close();
			}
		}
	}

	private static List<List<TambolaTicket>> groupTickets( Set<TambolaTicket> arr )
	{
		List<List<TambolaTicket>> tickets = new ArrayList<>();
		List<TambolaTicket> groupedTickets = new ArrayList<>();

		int count = 0;
		for ( TambolaTicket tambolaTicket : arr )
		{
			groupedTickets.add( tambolaTicket );
			++count;

			if ( count == 3 )
			{
				tickets.add( groupedTickets );
				count = 0;
				groupedTickets = new ArrayList<>();
			}

		}

		//added the remaining tickets
		if ( groupedTickets.size() < 3 )
		{
			tickets.add( groupedTickets );
		}

		return tickets;
	}

	private static void insertCell( PdfPTable table, String text, int align, int colspan, Font font )
	{

		//create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell( new Phrase( text.trim(), font ) );
		//set the cell alignment
		cell.setHorizontalAlignment( align );
		//set the cell column span in case you want to merge two or more cells
		cell.setColspan( colspan );
		//in case there is no text and you wan to create an empty row
		if ( text.trim().equalsIgnoreCase( "" ) )
		{
			cell.setMinimumHeight( 10f );
		}
		//add the call to the table
		table.addCell( cell );

	}
}
