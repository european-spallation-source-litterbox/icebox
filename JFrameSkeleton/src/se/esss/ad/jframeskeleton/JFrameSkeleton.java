/*
Copyright (c) 2014 European Spallation Source

This file is part of LinacLego.
LinacLego is free software: you can redistribute it and/or modify it under the terms of the 
GNU General Public License as published by the Free Software Foundation, either version 2 
of the License, or any newer version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. 
If not, see https://www.gnu.org/licenses/gpl-2.0.txt
*/
package se.esss.ad.jframeskeleton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class JFrameSkeleton extends JFrame
{
	private static final  String version = "v1.0";
	private static final  String versionDate = "January 22, 2016";
	private static final String iconLocation = "se/esss/ad/jframeskeleton/files/skullIcon.png";
	private static final String frametitle = "JFrame Skeleton";
	private static final String statusBarTitle = "Info";
	private static final int numStatusLines = 4;
	
	private JMenuBar mainMenuBar;
	private JPanel mainPane;
	private StatusPanel statusBar;
	private String lastFileDirectory = "./";
	
	private JLabel samplePictureLabel;


	public JFrameSkeleton()
	{
		super(frametitle);
		statusBar = new StatusPanel(numStatusLines, statusBarTitle);
		
		ImageIcon  logoIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(iconLocation));
        setIconImage(logoIcon.getImage());
        try 
        {
//            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        mainMenuBar = makeMenu();
        setJMenuBar(mainMenuBar);
       
		mainPane= new JPanel();
        setupMainPanel();
        getContentPane().setLayout(new BorderLayout(5,5));
		getContentPane().add(mainPane, BorderLayout.CENTER);
		getContentPane().add(statusBar.getScrollPane(), java.awt.BorderLayout.SOUTH);  
		statusBar.setText("Welcome");
		pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        
        // Move the window
        this.setLocation(x, y);
		setVisible(true);
		statusBar.getScrollPane().setMinimumSize(statusBar.getScrollPane().getSize());
		statusBar.getScrollPane().setPreferredSize(statusBar.getScrollPane().getSize());

        
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
            public void windowClosing(WindowEvent winEvt) 
            {
            	quitProgram();            
            }
        });

	}
	protected void setupMainPanel()
	{
		mainPane.setLayout(new FlowLayout());
		String intialSamplePictureLocation = "se/esss/ad/jframeskeleton/files/skeleton.gif";
		ImageIcon  samplePictureIcon = null;
		samplePictureLabel = new JLabel();
		try
		{
			samplePictureIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(intialSamplePictureLocation));
			samplePictureLabel.setIcon(samplePictureIcon);
		}catch (java.lang.NullPointerException e)
		{
			SwingUtilities.messageDialog("Error on loading picture \n" + intialSamplePictureLocation + "\nExiting Program!", this);
			quitProgram(); 
		}
		samplePictureLabel = new JLabel(samplePictureIcon);
        
        JButton pressButton = new JButton("Press me!");
        pressButton.addActionListener(new JFrameSkeletonActionListeners("pressButton.pressed", this));
        mainPane.add(samplePictureLabel);
        mainPane.add(pressButton);
	}
	private void openFile()
	{
		String[] fileExtensions = {"jpg","png","gif"};
		
		File newSamplePictureFile = SwingUtilities.chooseFile(lastFileDirectory, "Open New Image file", "", false, fileExtensions, this);
		if (newSamplePictureFile != null)
		{
			ImageIcon  samplePictureIcon = new ImageIcon(newSamplePictureFile.getPath());
// a little rescaling
			int oldImageWidth = samplePictureLabel.getIcon().getIconWidth();
			int newImageHeight = samplePictureIcon.getIconHeight() * oldImageWidth;
			newImageHeight = newImageHeight / samplePictureIcon.getIconWidth();
			Image image = samplePictureIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(oldImageWidth, newImageHeight,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			samplePictureIcon = new ImageIcon(newimg);  // transform it back			samplePictureLabel.setIcon(samplePictureIcon);
			samplePictureLabel.setIcon(samplePictureIcon);
			mainPane.setSize(mainPane.getWidth(), newImageHeight);
			pack();
			lastFileDirectory = newSamplePictureFile.getParent();
			
		}
	}
	private void saveFile()
	{
		String[] fileExtensions = {"jpg"};
		File saveFile = SwingUtilities.chooseFile(lastFileDirectory, "Save Image File", "image.jpg", true, fileExtensions, this);
		if (saveFile != null)
		{
			ImageIcon  samplePictureIcon = (ImageIcon) samplePictureLabel.getIcon();
			Image img = samplePictureIcon.getImage();
			BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			g2.drawImage(img, 0, 0, null);
			g2.dispose();
			try 
			{
				ImageIO.write(bi, "jpg", saveFile);
			} catch (IOException e) 
			{
				SwingUtilities.messageDialog("Error on loading picture \n" + saveFile.getPath() + "\n" + e.getMessage(), this);
			}	
		}
	}
	protected void quitProgram()
	{
		dispose();
		System.exit(0);
	}
	private void helpHelp()
	{
		SwingUtilities.messageDialog("No Help for you!", this);
	}
	private void helpAbout()
	{
		SwingUtilities.messageDialog(frametitle + " " + version + "\n" + "Last Updated " + versionDate, this);
	}
	private void pressButtonAction()
	{
		int choice  = SwingUtilities.optionDialog("Please....", "Must you keep pressing my buttons?", "Yes", "No", 1, this);
		if (choice == 1) SwingUtilities.messageDialog("Well, I find that quite annoying!\nPlease Stop!", this);
		if (choice == 2) SwingUtilities.messageDialog("Then stop doing it!", this);
	}
	protected  JMenuBar makeMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		String menuText[] = {"File", "Help"};
        String subMenuText[][] =
        {
    		{"Open","Save","Exit"},
    		{"Help", "About"}
    	};

        for (int i = 0; i < menuText.length; i++)
        {
            JMenu menu = new JMenu(menuText[i]);
            menuBar.add (menu);
            
            for (int j = 0; j < subMenuText[i].length; j++)
            {
                JMenuItem item = new JMenuItem(subMenuText[i][j]);
                menu.add (item);
                item.addActionListener(new JFrameSkeletonActionListeners(menuText[i] + "." +subMenuText[i][j], this));
            }
        }
        
        return menuBar;
    }
	private static class JFrameSkeletonActionListeners implements ActionListener
	{
		JFrameSkeleton jFrameSkeleton;
		String actionString = "";
		JFrameSkeletonActionListeners(String actionString, JFrameSkeleton jFrameSkeleton)
		{
			this.actionString = actionString;
			this.jFrameSkeleton = jFrameSkeleton;
		}
		public void actionPerformed(ActionEvent arg0) 
		{
			if (actionString.equals("File.Open")) jFrameSkeleton.openFile();
			if (actionString.equals("File.Save")) jFrameSkeleton.saveFile();
			if (actionString.equals("File.Exit")) jFrameSkeleton.quitProgram();
			if (actionString.equals("Help.About"))jFrameSkeleton.helpAbout();
			if (actionString.equals("Help.Help")) jFrameSkeleton.helpHelp();
			if (actionString.equals("pressButton.pressed")) jFrameSkeleton.pressButtonAction();
		}
		
	}
	public static void main(String[] args) 
	{
		new JFrameSkeleton();
	}

}
