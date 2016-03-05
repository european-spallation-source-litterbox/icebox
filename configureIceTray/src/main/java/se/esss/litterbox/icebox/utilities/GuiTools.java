package se.esss.litterbox.icebox.utilities;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import se.esss.litterbox.icebox.icetray.icecube.Signal;

public final class GuiTools {

	private GuiTools() {
		
	}

	public static List<Signal> getAllSigs(DefaultListModel<Signal> rModel, DefaultListModel<Signal> wModel) {
		List<Signal> allSigs = new ArrayList<Signal>();
		for (int i=0; i<rModel.getSize(); i++) {
			allSigs.add(rModel.elementAt(i));
		}
		for (int i=0; i<wModel.getSize(); i++) {
			allSigs.add(wModel.elementAt(i));
		}
		
		return allSigs;
	}
}
