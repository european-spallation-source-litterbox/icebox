package systems.icetech.icebox.icetray.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import systems.icetech.icebox.icetray.icecube.ReadSignal;

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
				model.addElement(new ReadSignal("sigRead" + ++counterInt));
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
