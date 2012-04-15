package bteam.capstone.guiTestArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private DefaultListModel items;
	private JList ServerListView;
	private JComboBox MapTypeList;

	/**
	 * Create the panel.
	 */
	public serverBrowser(Application application) {
		app = application;
		updating = false;
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New Game",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// panel.setBounds(0, app.size.height-133-panel.getInsets().bottom*4-5,
		// app.size.width-panel.getInsets().right, 133);
		Dimension d = new Dimension(app.size.width - panel.getInsets().right,
				133);
		// panel.setPreferredSize(d);
		// panel.setMinimumSize(d);
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout());

		JPanel createPanel = new JPanel();
		createPanel.setLayout(new GridLayout(3, 2));
		panel.add(createPanel, BorderLayout.CENTER);

		JLabel MapTypeLable = new JLabel("Map Type:");
		MapTypeLable.setBounds(6, 22, 64, 16);
		createPanel.add(MapTypeLable);

		MapTypeList = new JComboBox();
		MapTypeList.setBounds(92, 18, 250, 27);
		createPanel.add(MapTypeList);
		File file = new File("Maps");
		String[] dir = file.list();
		for (String s : dir) {
			File temp = new File("Maps/"+s);
			
			if (temp.isDirectory())
				MapTypeList.addItem(s);
		}

		JLabel GameNameLable = new JLabel("Game Name:");
		GameNameLable.setBounds(6, 50, 79, 16);
		createPanel.add(GameNameLable);

		GameNameField = new JTextField();
		GameNameField.setBounds(92, 44, 250, 28);
		createPanel.add(GameNameField);
		GameNameField.setColumns(10);

		JLabel PasswordLable = new JLabel("Password:");
		PasswordLable.setBounds(6, 78, 79, 16);
		createPanel.add(PasswordLable);

		PasswordField = new JPasswordField();
		PasswordField.setBounds(92, 72, 250, 28);
		createPanel.add(PasswordField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		panel.add(buttonPanel, BorderLayout.EAST);

		JButton CreateGameButton = new JButton("");
		Image img2 = app.graphics.startWar;
		img2 = img2.getScaledInstance(200, 70, java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(img2);
		Dimension cgs = new Dimension(200, 82);
		CreateGameButton.setIcon(icon2);
		CreateGameButton.setPreferredSize(cgs);
		CreateGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (GameNameField.getText().length() > 0) {
					String out = "create l "
							+ MapTypeList.getSelectedItem().toString();
					if (PasswordField.getText().length() > 0)
						out += " p " + PasswordField.getText();
					out += " n " + GameNameField.getText();
					app.client.sendData(out);
				}
			}

		});
		buttonPanel.add(CreateGameButton);

		JButton ConfirmGameButton = new JButton("");
		Image img = app.graphics.joinWar;
		img = img.getScaledInstance(200, 70, java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(img);
		ConfirmGameButton.setBounds(452, 18, 200, 82);
		ConfirmGameButton.setIcon(icon);
		buttonPanel.add(ConfirmGameButton);
		ConfirmGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (ServerListView.getSelectedIndex() > -1) {
					String val = (String) items.get(ServerListView
							.getSelectedIndex());
					Scanner scan = new Scanner(val);
					int id = scan.nextInt();
					String gameFile = scan.next();
					String creator = scan.next();
					boolean legacy = scan.nextBoolean();
					boolean hasPass = scan.nextBoolean();
					String worldName = scan.nextLine();
					worldName = worldName.substring(1);
					String pass = "";
					if (hasPass) {
						pass = " "
								+ JOptionPane
										.showInputDialog("Enter game pass");
					}
					app.client.sendData("join " + id + pass);
				}
			}

		});

		items = new DefaultListModel();
		ServerListView = new JList(items);
		ServerListView.setBounds(0, 0, app.size.width, 302);
		add(ServerListView, BorderLayout.CENTER);

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
