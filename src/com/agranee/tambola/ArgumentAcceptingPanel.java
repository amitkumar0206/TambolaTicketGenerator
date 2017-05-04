package com.agranee.tambola;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;

public class ArgumentAcceptingPanel extends JPanel
{
	private static final long serialVersionUID = 3462658495396450390L;
	private static String FILE_NAME = "Tambola.pdf";

	private static JLabel rbText;
	private static JLabel rbTopMesgText;
	private static JLabel rbBottomMsgText;
	private static JLabel rbFile;
	private static JTextField fldFileName;
	private static JTextField fldText;

	private static JTextField fldBottomText;
	private static JTextField fldTopText;

	private static JButton browseFileButton;
	private static TambolaArgsDto dto;

	public ArgumentAcceptingPanel()
	{
		dto = new TambolaArgsDto();
		createLayout();
	}

	public TambolaArgsDto getValues()
	{
		try
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}

		int result;
		ArgumentAcceptingPanel userInputPane;
		do
		{
			userInputPane = new ArgumentAcceptingPanel();
			result = JOptionPane.showConfirmDialog( null, userInputPane, "Tambola Ticket Generator", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE );

			if ( result == JOptionPane.OK_OPTION )
			{
				if ( validateArgs( userInputPane ) )
				{
					dto = null;
				}
			}
			else if ( result == JOptionPane.CANCEL_OPTION )
			{
				JOptionPane.showMessageDialog( userInputPane, "Thanks for Using Tambola Tickt Generator!", "Terminated",
						JOptionPane.INFORMATION_MESSAGE, null );
				System.exit( 0 );
			}

		}
		while ( dto == null );

		return dto;
	}

	private boolean validateArgs( ArgumentAcceptingPanel userInputPane )
	{
		if ( StringUtils.isEmpty( fldText.getText() ) )
		{
			JOptionPane.showMessageDialog( userInputPane, "Please enter a numeric Value", "Error", JOptionPane.ERROR_MESSAGE, null );
			return true;
		}

		try
		{
			dto.setNumberofTickets( Integer.parseInt( fldText.getText() ) );
		}
		catch ( NumberFormatException ne )
		{
			JOptionPane.showMessageDialog( userInputPane, "Please enter a numeric Value", "Error", JOptionPane.ERROR_MESSAGE, null );
			return true;
		}

		if ( dto.getFile() == null )
		{
			JOptionPane.showMessageDialog( userInputPane, "Please choode a directoryto store file.", "Error", JOptionPane.ERROR_MESSAGE,
					null );
			return true;
		}

		dto.setBottomMessage( fldBottomText.getText() );
		dto.setTopMessage( fldTopText.getText() );

		return false;
	}

	private void createLayout()
	{
		setLayout( new GridBagLayout() );
		GridBagConstraints gbc = new GridBagConstraints();

		rbText = new JLabel( "Number of Tickets" );
		rbFile = new JLabel( "Directory to store File" );
		rbTopMesgText = new JLabel( "Message on top" );
		rbBottomMsgText = new JLabel( "Message on bottom" );
		fldFileName = new JTextField( 10 );

		fldText = new JTextField( "200", 3 );
		fldBottomText = new JTextField( "Best Of Luck" );
		fldTopText = new JTextField( "Tambola" );

		fldFileName.setEditable( false );
		browseFileButton = new JButton( "..." );

		fldText.setEnabled( true );
		fldFileName.setEnabled( true );
		browseFileButton.setEnabled( true );

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		add( rbText, gbc );
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( fldText, gbc );

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		add( rbTopMesgText, gbc );
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( fldTopText, gbc );

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		add( rbBottomMsgText, gbc );
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( fldBottomText, gbc );

		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		add( rbFile, gbc );
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( fldFileName, gbc );
		gbc.gridx++;
		gbc.fill = GridBagConstraints.NONE;
		add( browseFileButton, gbc );

		browseFileButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

				int returnValue = chooser.showOpenDialog( null );

				if ( returnValue == JFileChooser.APPROVE_OPTION )
				{
					String selectedFile = chooser.getSelectedFile().getPath() + File.separatorChar + FILE_NAME;
					dto.setFile( new File( selectedFile ) );
					fldFileName.setText( dto.getFile().getAbsolutePath() );
				}
			}
		} );
	}
}
