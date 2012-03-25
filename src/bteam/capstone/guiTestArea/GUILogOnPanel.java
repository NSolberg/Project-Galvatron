package bteam.capstone.guiTestArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUILogOnPanel extends JPanel implements ClientUser {
	private Application app;

	public GUILogOnPanel(final Application app) {
		this.app = app;
		this.setLayout(null);
		JLabel lblUser = new JLabel("User Name:");
		lblUser.setBounds(0, 0, 75, 25);
		JLabel lblPass = new JLabel("Password :");
		lblPass.setBounds(0, 30, 75, 25);
		final JTextField txtUser = new JTextField();
		txtUser.setBounds(80, 0, 100, 25);
		JTextField txtPass = new JTextField();
		txtPass.setBounds(80, 30, 100, 25);
		JButton btnLog = new JButton("Log On");
		btnLog.setBounds(200, 30, 100, 25);
		btnLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String user = txtUser.getText();
				if (!user.equals("")) {
					app.client.connect("localhost", 1337);
					if (app.client.isConnected()) {
						app.client.start();
						app.client.sendData("user");
					} else {

					}
				}
			}
		});
		this.add(lblUser);
		this.add(txtUser);
		this.add(lblPass);
		this.add(txtPass);
		this.add(btnLog);
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		if (string.equals("Connected")) {
			app.switchView(1);
		}
	}
}