package se.esss.litterbox.icebox.icetray.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import se.esss.litterbox.icebox.icetray.icecube.WriteSignal;

public class WriteSigConfigPanel extends SignalConfiguratorPanel {

	private static final long serialVersionUID = 4823931342303696278L;
	private ActionListener createBtnListener;
	private ActionListener delBtnListener;
	int counterInt = 0;
	
	public WriteSigConfigPanel() {
		super();
		delBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.remove(sigList.getSelectedIndex());
			}
		};
		createBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addElement(
						new WriteSignal(JOptionPane.showInputDialog("Choose a name for the signal"))
						);
			}
		};
		btnDeleteSel.addActionListener(delBtnListener);
		btnCreateNewSignal.addActionListener(createBtnListener);
	}

	@Override
	protected String buttonText() {
		return "Write Sigs";
	}
}
