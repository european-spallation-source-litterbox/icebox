package se.esss.litterbox.icebox.icetray.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.esss.litterbox.icebox.exceptions.EpicsIOCException;
import se.esss.litterbox.icebox.exceptions.IceCubeException;
import se.esss.litterbox.icebox.icetray.EpicsIOC;
import se.esss.litterbox.icebox.icetray.IceCube;
import se.esss.litterbox.icebox.utilities.GuiTools;

public class DeployBuildIceCubePanel extends JPanel {

	private static final long serialVersionUID = -6782526862914742436L;
	private JButton btnBuildIcecube;
	private JButton btnBuildDeploy;
	private JTextField nameField;
	private ReadSigConfigPanel rsPanel;
	private WriteSigConfigPanel wsPanel;
	
	private ActionListener buildBtnListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			EpicsIOC epicsIOC;
			try {
				epicsIOC = makeEpicsIOC();
				System.out.println(makeIceCube());
				JOptionPane.showMessageDialog(
						btnBuildIcecube, 
						"Test-build successful", 
						"Success!", 
						JOptionPane.INFORMATION_MESSAGE
						);
			} catch (IceCubeException | EpicsIOCException e1) {
				JOptionPane.showMessageDialog(null,
						"Test-build failed:\n"
							+ e1.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	private ActionListener buildDeployBtnListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			IceCube iceCube;
			EpicsIOC epicsIOC;
			try {
				iceCube = makeIceCube();
				epicsIOC = makeEpicsIOC();
				System.out.println(iceCube);
			} catch (IceCubeException | EpicsIOCException e1) {
				JOptionPane.showMessageDialog(null,
						"IceCube test-build failed:\n"
							+ e1.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			int retVal = JOptionPane.showConfirmDialog(
					btnBuildIcecube, 
					"Continuing will make build &\ndeploy an IceCube, making changes\nto your hard drive.\n\nARE YOU SURE YOU WANT TO CONTINUE?", 
					"CONFIRM", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
					);
			if (retVal == JOptionPane.YES_OPTION) {
				System.out.println("The lunatic said yes!");
			}
			else if (retVal == JOptionPane.NO_OPTION) {
				System.out.println("Wise.  Very wise.");
			}
			JOptionPane.showMessageDialog(
					btnBuildIcecube, 
					"Build&Deploy IceCube functionality not implemented yet", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
					);
		}
	};

	public DeployBuildIceCubePanel(JTextField nameField, ReadSigConfigPanel rsPanel, WriteSigConfigPanel wsPanel) {
		this.nameField = nameField;
		this.rsPanel = rsPanel;
		this.wsPanel = wsPanel;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnBuildIcecube = new JButton("Test-build");
		btnBuildDeploy = new JButton("Build & Deploy IceCube");
		
		btnBuildIcecube.addActionListener(buildBtnListener);
		btnBuildDeploy.addActionListener(buildDeployBtnListener);
		
		add(btnBuildIcecube);
		add(btnBuildDeploy);
	}

	private IceCube makeIceCube() throws IceCubeException {
		return new IceCube(
				nameField.getText(), 
				GuiTools.getAllSigs(
						rsPanel.model, 
						wsPanel.model
						)
				);
	}
	
	private EpicsIOC makeEpicsIOC() throws EpicsIOCException, IceCubeException {
		return new EpicsIOC(makeIceCube());
	}
}
