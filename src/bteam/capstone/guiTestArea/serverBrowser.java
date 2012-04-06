package bteam.capstone.guiTestArea;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class serverBrowser extends JPanel implements ClientUser {
	private JTextField GameNameField;
	private JPasswordField PasswordField;
	private Application app;
	private boolean updating;
	private DefaultListModel items;;

	/**
	 * Create the panel.
	 */
	public serverBrowser(Application application) {
		app = application;
		updating = false;
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New Game",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 343, 708, 133);
		add(panel);
		panel.setLayout(null);

		JComboBox MapTypeList = new JComboBox();
		MapTypeList.setBounds(92, 18, 250, 27);
		panel.add(MapTypeList);

		JLabel MapTypeLable = new JLabel("Map Type:");
		MapTypeLable.setBounds(6, 22, 64, 16);
		panel.add(MapTypeLable);

		JLabel GameNameLable = new JLabel("Game Name:");
		GameNameLable.setBounds(6, 50, 79, 16);
		panel.add(GameNameLable);

		GameNameField = new JTextField();
		GameNameField.setBounds(92, 44, 250, 28);
		panel.add(GameNameField);
		GameNameField.setColumns(10);

		JLabel PasswordLable = new JLabel("Password:");
		PasswordLable.setBounds(6, 78, 79, 16);
		panel.add(PasswordLable);

		PasswordField = new JPasswordField();
		PasswordField.setBounds(92, 72, 250, 28);
		panel.add(PasswordField);

		JButton ConfirmGameButton = new JButton("");
		ConfirmGameButton.setIcon(new ImageIcon(serverBrowser.class
				.getResource("/Icons For Risk/go.png")));
		ConfirmGameButton.setBounds(452, 18, 213, 82);
		panel.add(ConfirmGameButton);

		items = new DefaultListModel();
		JList ServerListView = new JList(items);
		ServerListView.setBounds(12, 343, 702, -302);
		add(ServerListView);

	}
	
	public void refresh() {
		app.client.sendData("list");
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		if (string.substring(0, 3).equals("yes")) {
			app.switchView(2);
		} else if (string.substring(0, 3).equals("no ")) {
			System.out.println(string);
		} else if (string.equals("<list>")) {
			updating = true;
			items.removeAllElements();
		} else if (string.equals("</list>")) {
			updating = false;
		} else if (updating) {
			items.addElement(string);
		}
	}
}
