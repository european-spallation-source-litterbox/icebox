package se.esss.litterbox.icebox.icetray.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import se.esss.litterbox.icebox.icetray.icecube.ReadSignal;

public class ReadSigConfigPanel extends SignalConfiguratorPanel {
	
	private static final long serialVersionUID = -5939126923709442087L;
	private ActionListener createBtnListener;
	private ActionListener delBtnListener;
	int counterInt = 0;
	
	public ReadSigConfigPanel() {
		super();
		delBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.remove(sigList.getSelectedIndex());
			}
		};
		createBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadSignal newSignal = new ReadSignal(JOptionPane.showInputDialog("Choose a name for the signal"));
				if (model.contains(newSignal)) {
					JOptionPane.showMessageDialog(btnCreateNewSignal,  "No dupes please");
				}
				else {
					model.addElement(newSignal);
				}
			}
		};
		btnDeleteSel.addActionListener(delBtnListener);
		btnCreateNewSignal.addActionListener(createBtnListener);
	}

	@Override
	protected String buttonText() {
		return "Read Sigs";
	}

}
