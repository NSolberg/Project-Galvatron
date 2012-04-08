package bteam.capstone.guiTestArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUILobby extends JPanel implements ClientUser {
	// private String[][] items;
	private JLabel[] pNames;
	private JLabel lblMapName;
	private JButton[] pColor;
	private int[] pC;
	private JButton[] pConfirm;
	private JButton leave, start;
	private ImageIcon yes, no;
	private Application app;
	private String map;
	private int pos;

	@Override
	public void displayData(String string) {
		Scanner scan = new Scanner(string);
		if (scan.hasNextInt()) {
			int num = scan.nextInt();
			String cmd = scan.next();
			if (cmd.equals("remove")) {
				if (pNames[num].equals(app.userName))
					start.setEnabled(false);
				pNames[num].setText("");
				pColor[num].setEnabled(false);
				pConfirm[num].setEnabled(false);
			} else if (cmd.equals("add")) {
				cmd = scan.next();
				pNames[num].setText(cmd);
				if (pNames[num].getText().equals(app.userName))
					pos = num;
				int c = scan.nextInt();
				pC[num] = c;
				switch (c) {
				case 0:
					pColor[num].setBackground(Color.RED);
					break;
				case 1:
					pColor[num].setBackground(Color.ORANGE);
					break;
				case 2:
					pColor[num].setBackground(Color.YELLOW);
					break;
				case 3:
					pColor[num].setBackground(Color.GREEN);
					break;
				case 4:
					pColor[num].setBackground(Color.BLUE);
					break;
				case 5:
					pColor[num].setBackground(new Color(143, 0, 255));
					break;
				case 6:
					pColor[num].setBackground(Color.BLACK);
					break;
				}
				pColor[num].setEnabled(true);
				pConfirm[num].setEnabled(true);
				boolean val = scan.nextBoolean();
				if (val)
					pConfirm[num].setIcon(yes);
				else
					pConfirm[num].setIcon(no);
				leave.setEnabled(true);
			} else if (cmd.equals("rdy")) {
				pConfirm[num].setIcon(yes);
			} else if (cmd.equals("nrdy")) {
				pConfirm[num].setIcon(no);
			} else if (cmd.equals("col")) {
				int c = scan.nextInt();
				pC[num] = c;
				switch (c) {
				case 0:
					pColor[num].setBackground(Color.RED);
					break;
				case 1:
					pColor[num].setBackground(Color.ORANGE);
					break;
				case 2:
					pColor[num].setBackground(Color.YELLOW);
					break;
				case 3:
					pColor[num].setBackground(Color.GREEN);
					break;
				case 4:
					pColor[num].setBackground(Color.BLUE);
					break;
				case 5:
					pColor[num].setBackground(new Color(143, 0, 255));
					break;
				case 6:
					pColor[num].setBackground(Color.BLACK);
					break;
				}
			}
		} else {
			String cmd = scan.next();
			if (cmd.equals("leave"))
				app.switchView(1);
			else if (cmd.equals("goodbye"))
				app.switchView(0);
			else if (cmd.equals("map")) {
				map = scan.next();
				this.lblMapName.setText(map);
			} else if (cmd.equals("master")) {
				this.start.setEnabled(true);
			} else if (cmd.equals("Alert")) {
				cmd = scan.nextLine();
				// JOptionPane.showMessageDialog(null, cmd, "Alert", ERROR);
			} else if (cmd.equals("start")) {
				app.gameFile = map;
				String[] names = new String[pNames.length];
				for (int i = 0; i < 6; i++) {
					names[i] = pNames[i].getText();
				}
				app.switchView(4);
				((MapLoader) app.panels[4]).loadMap(map, names, pC);
			}
		}
	}

	public GUILobby(Application application) {
		app = application;
		this.setLayout(new BorderLayout());
		this.createTitleArea();
		this.createPlayerArea();
		this.createOtherButtons();
	}

	private void createTitleArea() {
		JPanel titleArea = new JPanel();

		this.add(titleArea, BorderLayout.NORTH);
		// titleArea.setLayout(new FlowLayout());
		JLabel lblTitle = new JLabel("MAP:");
		titleArea.add(lblTitle);
		lblTitle.setBounds(20, 0, 100, 25);
		lblTitle.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
		lblTitle.setForeground(Color.RED);
		lblMapName = new JLabel("Map Name Here");
		titleArea.add(lblMapName);
		lblMapName.setBounds(120, 0, 300, 25);
		lblMapName.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
		lblMapName.setForeground(Color.RED);
		lblMapName.setAlignmentX(LEFT_ALIGNMENT);
	}

	private void createPlayerArea() {
		JPanel playerArea = new JPanel(new GridLayout(7, 3));
		playerArea.add(new JLabel("Player Name"));
		playerArea.add(new JLabel("Color"));
		playerArea.add(new JLabel("Ready"));
		pNames = new JLabel[6];
		pColor = new JButton[6];
		pC = new int[6];
		pConfirm = new JButton[6];
		yes = new ImageIcon(app.graphics.okCheck);
		no = new ImageIcon(app.graphics.cancel);
		for (int i = 0; i < 6; i++) {
			pNames[i] = new JLabel();
			pNames[i].setPreferredSize(new Dimension(400, 30));
			pColor[i] = new JButton();
			pColor[i].setName(i + "");
			pColor[i].setPreferredSize(new Dimension(50, 50));
			pColor[i].setMaximumSize(new Dimension(50, 50));
			pColor[i].setMinimumSize(new Dimension(50, 50));
			pColor[i].setEnabled(false);
			pColor[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton) e.getSource();
					if (b.getName().equals(pos + ""))
						app.client.sendData("col");
				}
			});
			pConfirm[i] = new JButton();
			pConfirm[i].setName(i + "");
			pConfirm[i].setIcon(no);
			pConfirm[i].setEnabled(false);
			pConfirm[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton) e.getSource();
					if (b.getName().equals(pos + ""))
						app.client.sendData("rdy");
				}
			});
			playerArea.add(pNames[i]);
			playerArea.add(pColor[i]);
			playerArea.add(pConfirm[i]);
		}
		this.add(playerArea, BorderLayout.CENTER);
	}

	private void createOtherButtons() {
		JPanel other = new JPanel();
		leave = new JButton("leave");
		leave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				app.client.sendData("leave");
			}
		});
		start = new JButton("start game");
		start.setEnabled(false);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				app.client.sendData("start");
			}
		});
		other.add(leave);
		other.add(start);
		this.add(other, BorderLayout.SOUTH);
	}
}
