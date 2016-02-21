package se.esss.litterbox.icebox.icetray.gui;

import java.awt.EventQueue;

import javax.json.Json;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.icetray.IceCube;
import se.esss.litterbox.icebox.icetray.icecube.Signal;
import se.esss.litterbox.icebox.utilities.GuiTools;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigureIceCube {

	private JFrame frame;
	private JTextField txtIceTrayName;
	private JPanel primaryPanel;
	private ReadSigConfigPanel readSigPanel;
	private WriteSigConfigPanel writeSigPanel;
	private final String helpAboutString = "Ice-Tray configuriser.\nFeb 2016";
	private final String helpHelpString = "You're beyond help.";
	private DeployBuildIceCubePanel deploymentPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					ConfigureIceCube window = new ConfigureIceCube();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConfigureIceCube() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 537, 334);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		makeMenuBar();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		txtIceTrayName = new JTextField();
		txtIceTrayName.setHorizontalAlignment(SwingConstants.CENTER);
		txtIceTrayName.setText("Name of Ice-Tray...");
		panel.add(txtIceTrayName);
		txtIceTrayName.setColumns(10);
		
		primaryPanel = new JPanel();
		frame.getContentPane().add(primaryPanel, BorderLayout.CENTER);
		primaryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		readSigPanel = new ReadSigConfigPanel();
		primaryPanel.add(readSigPanel);
		
		writeSigPanel = new WriteSigConfigPanel();
		primaryPanel.add(writeSigPanel);
		
		deploymentPanel = new DeployBuildIceCubePanel(
				txtIceTrayName,
				readSigPanel, 
				writeSigPanel);
		frame.getContentPane().add(deploymentPanel, BorderLayout.SOUTH);
	}

	private void makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String menuText[] = {"File", "Help"};
        String subMenuText[][] =
        {
    		{"Open","Save","Exit"},
    		{"Help", "About"}
    	};
        Integer subMenuKeyEvent[][] = {
        		{KeyEvent.VK_O, KeyEvent.VK_S,KeyEvent.VK_Q},
        		{KeyEvent.VK_H,-1}
        };

        for (int i = 0; i < menuText.length; i++)
        {
            JMenu menu = new JMenu(menuText[i]);
            menuBar.add (menu);
            
            for (int j = 0; j < subMenuText[i].length; j++)
            {
                JMenuItem item = new JMenuItem(subMenuText[i][j]);
                menu.add(item);
                if (!subMenuKeyEvent[i][j].equals(-1)) {
                	item.setAccelerator(KeyStroke.getKeyStroke(subMenuKeyEvent[i][j], ActionEvent.CTRL_MASK));
                }
                item.addActionListener(new IceTrayActionListener(menuText[i] + "." +subMenuText[i][j], this));
            }
        }
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}

	private static class IceTrayActionListener implements ActionListener {
		ConfigureIceCube iceTrayConfigurizer;
		String actionString = "";
		IceTrayActionListener(String actionString, ConfigureIceCube configureIceCube) {
			this.actionString = actionString;
			this.iceTrayConfigurizer = configureIceCube;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			if (actionString.equals("File.Open")) iceTrayConfigurizer.openFile();
			if (actionString.equals("File.Save")) iceTrayConfigurizer.saveFile();
			if (actionString.equals("File.Exit")) iceTrayConfigurizer.quitProgram();
			if (actionString.equals("Help.About"))iceTrayConfigurizer.helpAbout();
			if (actionString.equals("Help.Help")) iceTrayConfigurizer.helpHelp();
		}
	}
	private void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				IceCube iceCubeObj = new IceCube(Json.createReader(new FileReader(chooser.getSelectedFile())).readObject());
				txtIceTrayName.setText(iceCubeObj.getName());
				readSigPanel.model.removeAllElements();
				writeSigPanel.model.removeAllElements();
				for (Signal i : iceCubeObj.getReadSignals()) {
					readSigPanel.model.addElement(i);
				}
				for (Signal i : iceCubeObj.getWriteSignals()) {
					writeSigPanel.model.addElement(i);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IceCubeException e) {
				JOptionPane.showMessageDialog(frame,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void saveFile() {
		IceCube iceCube = new IceCube(
				txtIceTrayName.getText(), 
				GuiTools.getAllSigs(
						readSigPanel.model, 
						writeSigPanel.model
						)
				);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int retrieval = chooser.showSaveDialog(null);
		if (retrieval==JFileChooser.APPROVE_OPTION) {
			try (FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".json")) {
				writer.write(iceCube.getJsonRep().toString());
				writer.flush();
				writer.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	protected void quitProgram() {
		System.exit(0);
	}
	private void helpAbout() {
		JOptionPane.showMessageDialog(frame, helpAboutString);
	}
	private void helpHelp() {
		JOptionPane.showMessageDialog(frame, helpHelpString);
	}
}
