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
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigureIceTray {

	private JFrame frame;
	private JTextField txtNameOfIcetray;
	private JPanel primaryPanel,readSigPanel,writeSigPanel,readSigButtonPanel,writeSigButtonPanel;
	private JLabel lblReadSigs_1,lblWriteSigs_1;
	private JList<String> writeSigList,readSigList;
	private JButton btnDeleteSelRead,btnCreateNewRSignal,btnDeleteSelWrite,btnCreateNewWSignal;
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
		
		txtNameOfIcetray = new JTextField();
		txtNameOfIcetray.setHorizontalAlignment(SwingConstants.CENTER);
		txtNameOfIcetray.setText("Name of Ice-Tray...");
		panel.add(txtNameOfIcetray);
		txtNameOfIcetray.setColumns(10);
		
		primaryPanel = new JPanel();
		frame.getContentPane().add(primaryPanel, BorderLayout.CENTER);
		primaryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		readSigPanel = new JPanel();
		primaryPanel.add(readSigPanel);
		readSigPanel.setLayout(new BorderLayout(0, 0));
		
		lblReadSigs_1 = new JLabel("Read Sigs");
		lblReadSigs_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblReadSigs_1.setHorizontalAlignment(SwingConstants.CENTER);
		readSigPanel.add(lblReadSigs_1, BorderLayout.NORTH);
		
		readSigList = new JList<String>();
		readSigList.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		readSigList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1438563442699558208L;
			String[] values = new String[] {"Arse1", "Arse2", "Arse3"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane readSigScrollPane = new JScrollPane(readSigList);
		readSigPanel.add(readSigScrollPane, BorderLayout.CENTER);
		
		readSigButtonPanel = new JPanel();
		readSigPanel.add(readSigButtonPanel, BorderLayout.SOUTH);
		readSigButtonPanel.setLayout(new BorderLayout(0, 0));
		
		btnDeleteSelRead = new JButton("Delete Selected");
		readSigButtonPanel.add(btnDeleteSelRead, BorderLayout.NORTH);
		
		btnCreateNewRSignal= new JButton("Create New Signal");
		readSigButtonPanel.add(btnCreateNewRSignal, BorderLayout.SOUTH);
		
		writeSigPanel = new JPanel();
		primaryPanel.add(writeSigPanel);
		writeSigPanel.setLayout(new BorderLayout(0, 0));
		
		lblWriteSigs_1 = new JLabel("Write Sigs");
		lblWriteSigs_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblWriteSigs_1.setHorizontalAlignment(SwingConstants.CENTER);
		writeSigPanel.add(lblWriteSigs_1, BorderLayout.NORTH);
		
		writeSigList = new JList<String>();
		writeSigList.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		writeSigList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1438563442699558208L;
			String[] values = new String[] {"Blah1", "Blah2", "Blah3"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane writeSigScrollPane = new JScrollPane(writeSigList);
		writeSigPanel.add(writeSigScrollPane, BorderLayout.CENTER);
		
		writeSigButtonPanel = new JPanel();
		writeSigPanel.add(writeSigButtonPanel, BorderLayout.SOUTH);
		writeSigButtonPanel.setLayout(new BorderLayout(0, 0));
		
		btnDeleteSelWrite = new JButton("Delete Selected");
		writeSigButtonPanel.add(btnDeleteSelWrite, BorderLayout.NORTH);
		
		btnCreateNewWSignal = new JButton("Create New Signal");
		writeSigButtonPanel.add(btnCreateNewWSignal, BorderLayout.SOUTH);
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
		IceTrayActionListener(String actionString, ConfigureIceTray jFrameSkeleton) {
			this.actionString = actionString;
			this.iceTrayConfigurizer = jFrameSkeleton;
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
