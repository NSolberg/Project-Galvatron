package bteam.capstone.guiTestArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GUIServerPanel extends JPanel implements ClientUser {
	Application app;
	@SuppressWarnings("rawtypes")
	JList games;
	boolean updating;
	@SuppressWarnings("rawtypes")
	DefaultListModel items;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GUIServerPanel(Application application) {
		this.setLayout(new BorderLayout());
		app = application;
		createNewGamePanel();
		updating = false;
		items = new DefaultListModel();
		games = new JList(items);
		this.add(games, BorderLayout.CENTER);
		JButton join = new JButton("Join");
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (games.getSelectedIndex() > -1) {
					String val = (String) items.get(games.getSelectedIndex());
					Scanner scan = new Scanner(val);
					int num = scan.nextInt();
					scan.next();
					scan.next();
					scan.next();
					boolean hasPass = scan.nextBoolean();
					String pass = "";
					if (hasPass) {
						pass = " "
								+ JOptionPane
										.showInputDialog("Enter game pass");
					}
					app.client.sendData("join "+num + pass);
				}
			}

		});
		this.add(join, BorderLayout.EAST);
	}

	private void createNewGamePanel() {
		JPanel newGamePanel = new JPanel();
		newGamePanel.setBorder(new TitledBorder(null, "New Game",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		newGamePanel.setLayout(new GridLayout(4, 1));
		// create list of maps
		@SuppressWarnings("rawtypes")
		final JComboBox maps = new JComboBox();
		File file = new File("Maps");
		String[] dir = file.list();
		for (String s : dir) {
			maps.addItem(s);
		}
		newGamePanel.add(maps);
		// password area
		JLabel lblPass = new JLabel("Password");
		newGamePanel.add(lblPass);
		final JTextField txtPass = new JTextField();
		txtPass.setToolTipText("Not Required");
		newGamePanel.add(txtPass);
		// create button;
		JButton create = new JButton("create");
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = "create l " + maps.getSelectedItem().toString();
				if (txtPass.getText().length() > 0)
					cmd += " p " + txtPass.getText();
				app.client.sendData(cmd);
			}

		});
		newGamePanel.add(create);
		this.add(newGamePanel, BorderLayout.SOUTH);
	}

	public void refresh() {
		// TODO Auto-generated method stub
		app.client.sendData("list");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		if (string.substring(0,3).equals("yes")) {
			Scanner scan = new Scanner(string);
			scan.next();
			String map = scan.next();
			app.setMap(map);
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
