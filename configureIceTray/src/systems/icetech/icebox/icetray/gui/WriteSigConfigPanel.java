package systems.icetech.icebox.icetray.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteSigConfigPanel extends SignalConfiguratorPanel {

	private static final long serialVersionUID = 4823931342303696278L;
	private ActionListener createBtnListener;
	private ActionListener delBtnListener;
	
	public WriteSigConfigPanel() {
		super();
		delBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addElement("Delete-write-button pressed!");
				
			}
		};
		createBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addElement("Create-write-button pressed!");
				
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
