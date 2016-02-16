package systems.icetech.icebox.icetray.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public abstract class SignalConfiguratorPanel extends JPanel {

	private static final long serialVersionUID = -9104767885807126408L;
	private JLabel lblSigs;
	private JPanel sigButtonPanel;
	public JList<String> sigList;
	public JButton btnDeleteSel;
	public JButton btnCreateNewSignal;

	public SignalConfiguratorPanel() {
		System.out.println("Testing");
		setLayout(new BorderLayout(0, 0));
		
		lblSigs = new JLabel(buttonText());
		lblSigs.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblSigs.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSigs, BorderLayout.NORTH);
		
		sigList = new JList<String>();
		sigList.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		sigList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1438563442699558208L;
			String[] values = new String[] {"A1", "A2", "A3"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane readSigScrollPane = new JScrollPane(sigList);
		add(readSigScrollPane, BorderLayout.CENTER);
		
		sigButtonPanel = new JPanel();
		add(sigButtonPanel, BorderLayout.SOUTH);
		sigButtonPanel.setLayout(new BorderLayout(0, 0));
		
		btnDeleteSel = new JButton("Delete Selected");
		sigButtonPanel.add(btnDeleteSel, BorderLayout.NORTH);
		
		btnCreateNewSignal= new JButton("Create New Signal");
		sigButtonPanel.add(btnCreateNewSignal, BorderLayout.SOUTH);
	}

	protected abstract String buttonText();
}
