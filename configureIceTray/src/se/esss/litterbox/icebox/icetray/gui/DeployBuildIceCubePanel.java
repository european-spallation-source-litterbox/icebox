package se.esss.litterbox.icebox.icetray.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DeployBuildIceCubePanel extends JPanel {

	private static final long serialVersionUID = -6782526862914742436L;
	private JButton btnBuildIcecube;
	private JButton btnBuildDeploy;

	public DeployBuildIceCubePanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnBuildIcecube = new JButton("Build IceCube");
		btnBuildDeploy = new JButton("Build & Deploy IceCube");
		add(btnBuildIcecube);
		add(btnBuildDeploy);		
	}

}
