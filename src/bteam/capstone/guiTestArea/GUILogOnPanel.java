package bteam.capstone.guiTestArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUILogOnPanel extends JPanel {
	public GUILogOnPanel() {
		JLabel lblUser = new JLabel("User Name:");
		JLabel lblPass = new JLabel("Password :");
		JTextField txtUser = new JTextField();
		txtUser.setSize(100, 25);
		JTextField txtPass = new JTextField();
		txtPass.setSize(100, 25);
		JButton btnLog = new JButton("Log On");
		this.add(lblUser);
		this.add(txtUser);
		this.add(lblPass);
		this.add(txtPass);
		this.add(btnLog);
	}

}
