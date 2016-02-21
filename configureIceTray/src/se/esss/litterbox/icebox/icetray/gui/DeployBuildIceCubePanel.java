package se.esss.litterbox.icebox.icetray.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DeployBuildIceCubePanel extends JPanel {

	private static final long serialVersionUID = -6782526862914742436L;
	private JButton btnBuildIcecube;
	private JButton btnBuildDeploy;
	
	private ActionListener buildBtnListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(
					btnBuildIcecube, 
					"Build IceCube functionality not implemented yet", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
					);
		}
	};
	private ActionListener buildDeployBtnListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(
					btnBuildIcecube, 
					"Build&Deploy IceCube functionality not implemented yet", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
					);
		}
	};

	public DeployBuildIceCubePanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnBuildIcecube = new JButton("Build IceCube");
		btnBuildDeploy = new JButton("Build & Deploy IceCube");
		
		btnBuildIcecube.addActionListener(buildBtnListener);
		btnBuildDeploy.addActionListener(buildDeployBtnListener);
		
		add(btnBuildIcecube);
		add(btnBuildDeploy);
	}
	
}
