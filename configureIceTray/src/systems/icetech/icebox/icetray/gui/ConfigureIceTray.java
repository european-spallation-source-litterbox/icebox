package systems.icetech.icebox.icetray.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigureIceTray {

	private JFrame frame;
	private JTextField txtIceTrayName;
	private JPanel primaryPanel;
	private ReadSigConfigPanel readSigPanel;
	private WriteSigConfigPanel writeSigPanel;
	private String helpAboutString = "Ice-Tray configuriser.\nFeb 2016";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigureIceTray window = new ConfigureIceTray();
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
	public ConfigureIceTray() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 331, 310);
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
	}

	private void makeMenuBar() {
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
                menu.add(item);
                item.addActionListener(new IceTrayActionListener(menuText[i] + "." +subMenuText[i][j], this));
            }
        }
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}

	private static class IceTrayActionListener implements ActionListener {
		ConfigureIceTray iceTrayConfigurizer;
		String actionString = "";
		IceTrayActionListener(String actionString, ConfigureIceTray configureIceTray) {
			this.actionString = actionString;
			this.iceTrayConfigurizer = configureIceTray;
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
		// TODO Auto-generated method stub
	}
	private void saveFile() {
		// TODO Auto-generated method stub
	}
	protected void quitProgram() {
		System.exit(0);
	}
	private void helpAbout() {
		JOptionPane.showMessageDialog(frame, helpAboutString);
	}
	private void helpHelp() {
		JOptionPane.showMessageDialog(frame, "You're beyond help.");
	}
}
