package systems.icetech.icebox.icetray.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import systems.icetech.icebox.icetray.icecube.WriteSignal;

public class WriteSigConfigPanel extends SignalConfiguratorPanel {

	private static final long serialVersionUID = 4823931342303696278L;
	private ActionListener createBtnListener;
	private ActionListener delBtnListener;
	int counterInt = 0;
	
	public WriteSigConfigPanel() {
		super();
		delBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//model.addElement("Delete-write-button pressed!");
			}
		};
		createBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//model.addElement("Create-write-button pressed!");
				model.addElement(new WriteSignal("sigWrite" + ++counterInt));
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
