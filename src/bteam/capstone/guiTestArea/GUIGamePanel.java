package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bteam.capstone.gui.GuiMap;

@SuppressWarnings("serial")
public class GUIGamePanel extends JPanel implements ClientUser, MouseListener,
		MouseMotionListener {
	private Dimension screensize;
	private Application app;
	private GuiMap map;
	private int sel;
	private boolean dragging;
	private Point local;
	// GUI components
	private JTabbedPane Phase;
	private GUICard[] cards;
	private JPanel CardPane, PlaceTroopPane, AttackPane, MoveTroopPane,
			EndTurnPane, ChatContainer;
	private JSlider TroopPlaceSlider, DiceSlider, NumTroopsMoveSlider;
	JProgressBar progressBar;
	JButton RollDiceButton, AttackSkipButton;
	JTextPane DeffRollDisp, AttackRollDisp, PlaceTroopNation1Disp,
			PlaceTroopNation2Disp;
	// player data
	private ArrayList<String> players;
	private ArrayList<Integer> color;
	ArrayList<String> pTurn;
	boolean def = false;

	public GUIGamePanel(Application application) {
		app = application;
		screensize = app.size;
		dragging = false;
		sel = -1;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		init();
		updateUiPos(app.fullscreen);
		// this.Phase.setEnabled(false);
		// Phase.setEnabled(false);
		this.switchPhase("begin");
	}

	private void switchPhase(String p) {
		this.disablePhases();
		if (p.equals("card")) {
			Phase.setEnabledAt(1, true);
			Phase.setSelectedComponent(CardPane);
		} else if (p.equals("recruit")) {
			Phase.setEnabledAt(2, true);
			Phase.setSelectedComponent(PlaceTroopPane);
		} else if (p.equals("attack")) {
			Phase.setEnabledAt(3, true);
			Phase.setSelectedComponent(AttackPane);
			this.RollDiceButton.setEnabled(true);
			this.DiceSlider.setMaximum(3);
			this.AttackRollDisp.setText("");
			this.DeffRollDisp.setText("");
			if (!def)
				this.AttackSkipButton.setEnabled(true);
		} else if (p.equals("fortify")) {
			Phase.setEnabledAt(4, true);
			Phase.setSelectedComponent(MoveTroopPane);
			this.PlaceTroopNation1Disp.setText("");
			this.PlaceTroopNation2Disp.setText("");
		} else if (p.equals("begin")) {
			Phase.setEnabledAt(0, true);
			Phase.setSelectedComponent(EndTurnPane);
		}
	}

	private void disablePhases() {
		for (int i = 0; i < 5; i++) {
			Phase.setEnabledAt(i, false);
		}
	}

	private void init() {
		this.setLayout(null);
		// Phase pane
		Phase = new JTabbedPane(JTabbedPane.TOP);
		Phase.setBorder(new TitledBorder(null, "Phase", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		Phase.setSize(600 - Phase.getInsets().right - Phase.getInsets().left,
				190);
		Phase.setLocation(0, 0);
		this.add(Phase);
		this.createEndTurnPane();
		this.createCardPane();
		this.createPlaceTroopPane();
		this.createAttackPane();
		this.createMoveTroopPane();
		this.createChatPane();
	}

	private void createCardPane() {
		// create card Pane
		CardPane = new JPanel();
		CardPane.setForeground(SystemColor.menu);
		Image img = app.graphics.phaseCard;
		img = img.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH);

		Phase.addTab("\n", new ImageIcon(img), CardPane, null);
		CardPane.setLayout(null);
		// create cards
		cards = new GUICard[9];
		for (int i = 0; i < 9; i++) {
			cards[i] = new GUICard();
			CardPane.add(cards[i]);
			cards[i].setLocation(0 + i * (2 + cards[i].getWidth()), 2);
		}
		// create buttons
		JButton TurnInButton = new JButton("");
		TurnInButton.setIcon(new ImageIcon(app.graphics.okCheck));
		TurnInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("CARDS TURNED IN");
			}
		});
		TurnInButton.setFont(new Font("Stencil", Font.BOLD, 15));
		TurnInButton.setSize(75, 35);
		TurnInButton.setLocation(
				cards[8].getLocation().x + 2 + cards[8].getWidth(), 2);
		CardPane.add(TurnInButton);
		JButton SkipTurnInButton = new JButton("");
		SkipTurnInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("SKIPPED CARD TURN IN");
			}
		});
		SkipTurnInButton.setIcon(new ImageIcon(app.graphics.cancel));
		SkipTurnInButton.setFont(new Font("Stencil", Font.BOLD, 14));
		SkipTurnInButton.setSize(75, 35);
		SkipTurnInButton.setLocation(
				cards[8].getLocation().x + 2 + cards[8].getWidth(), 37);
		CardPane.add(SkipTurnInButton);
	}

	private void createPlaceTroopPane() {
		PlaceTroopPane = new JPanel();
		Phase.addTab("", new ImageIcon(app.graphics.phaseTroops),
				PlaceTroopPane, "Displays the remaining troops\n");
		PlaceTroopPane.setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setStringPainted(true);
		progressBar.setValue(20);
		progressBar.setBounds(0, 5, 181, 70);
		PlaceTroopPane.add(progressBar);

		JLabel lable = new JLabel("TROOPS TO PLACE:");
		lable.setFont(new Font("Stencil", Font.BOLD, 12));
		lable.setBounds(199, 6, 130, 15);
		PlaceTroopPane.add(lable);

		TroopPlaceSlider = new JSlider();
		TroopPlaceSlider.setSnapToTicks(true);
		TroopPlaceSlider.setPaintTicks(true);
		TroopPlaceSlider.setPaintLabels(true);
		TroopPlaceSlider.setMinorTickSpacing(1);
		TroopPlaceSlider.setMajorTickSpacing(1);
		TroopPlaceSlider.setMaximum(5);
		TroopPlaceSlider.setBounds(199, 25, 200, 46);
		PlaceTroopPane.add(TroopPlaceSlider);

		JButton TroopPlaceCommitButton = new JButton();
		TroopPlaceCommitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (TroopPlaceSlider.getValue() > 0) {
					app.client.sendData("for " + " "
							+ TroopPlaceSlider.getValue());
				}
			}
		});
		TroopPlaceCommitButton.setIcon(new ImageIcon(app.graphics.okCheck));
		TroopPlaceCommitButton.setBounds(420, 5, 125, 70);
		PlaceTroopPane.add(TroopPlaceCommitButton);
	}

	private void createAttackPane() {
		AttackPane = new JPanel();
		Phase.addTab("", new ImageIcon(app.graphics.phaseAttack), AttackPane,
				null);
		AttackPane.setLayout(null);

		DiceSlider = new JSlider();
		DiceSlider.setFocusTraversalKeysEnabled(false);
		DiceSlider.setSnapToTicks(true);
		DiceSlider.setPaintTicks(true);
		DiceSlider.setPaintLabels(true);
		DiceSlider.setMinimum(1);
		DiceSlider.setMaximum(3);
		DiceSlider.setMinorTickSpacing(1);
		DiceSlider.setMajorTickSpacing(1);
		DiceSlider.setBounds(6, 20, 200, 60);
		AttackPane.add(DiceSlider);

		JLabel lblNumberOfTroops = new JLabel("NUMBER OF TROOPS");
		lblNumberOfTroops.setFont(new Font("Stencil", Font.BOLD, 14));
		lblNumberOfTroops.setBounds(30, 15, 184, 15);
		AttackPane.add(lblNumberOfTroops);

		RollDiceButton = new JButton("");
		RollDiceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (RollDiceButton.isEnabled())
					app.client.sendData("atk " + DiceSlider.getValue());
				RollDiceButton.setEnabled(false);
				AttackSkipButton.setEnabled(false);
			}
		});
		RollDiceButton.setIcon(new ImageIcon(app.graphics.dice));
		RollDiceButton.setBounds(218, 6, 113, 70);
		AttackPane.add(RollDiceButton);

		DeffRollDisp = new JTextPane();
		DeffRollDisp.setBounds(343, 22, 40, 37);
		AttackPane.add(DeffRollDisp);

		AttackRollDisp = new JTextPane();
		AttackRollDisp.setBounds(408, 22, 40, 37);
		AttackPane.add(AttackRollDisp);

		JLabel lblDef = new JLabel("DEF");
		lblDef.setFont(new Font("Stencil", Font.PLAIN, 12));
		lblDef.setBounds(343, 64, 57, 15);
		AttackPane.add(lblDef);

		JLabel lblAttk = new JLabel("ATTK");
		lblAttk.setFont(new Font("Stencil", Font.PLAIN, 12));
		lblAttk.setBounds(408, 64, 57, 15);
		AttackPane.add(lblAttk);

		AttackSkipButton = new JButton("");
		AttackSkipButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!def)
					if (!map.inAttack())
						app.client.sendData("fin");
					else
						app.client.sendData("end");
			}
		});
		AttackSkipButton.setIcon(new ImageIcon(app.graphics.cancel));
		AttackSkipButton.setBounds(472, 6, 70, 70);
		AttackPane.add(AttackSkipButton);
	}

	private void createMoveTroopPane() {
		MoveTroopPane = new JPanel();
		Phase.addTab("", new ImageIcon(app.graphics.phaseFortify),
				MoveTroopPane, null);
		MoveTroopPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("NATION 1");
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 14));
		lblNewLabel.setBounds(6, 64, 76, 15);
		MoveTroopPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("NATION 2");
		lblNewLabel_1.setFont(new Font("Stencil", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(112, 63, 82, 15);
		MoveTroopPane.add(lblNewLabel_1);

		PlaceTroopNation1Disp = new JTextPane();
		PlaceTroopNation1Disp.setBounds(6, 29, 76, 23);
		MoveTroopPane.add(PlaceTroopNation1Disp);

		PlaceTroopNation2Disp = new JTextPane();
		PlaceTroopNation2Disp.setBounds(112, 29, 82, 23);
		MoveTroopPane.add(PlaceTroopNation2Disp);

		NumTroopsMoveSlider = new JSlider();
		NumTroopsMoveSlider.setSnapToTicks(true);
		NumTroopsMoveSlider.setPaintTicks(true);
		NumTroopsMoveSlider.setPaintLabels(true);
		NumTroopsMoveSlider.setMinorTickSpacing(1);
		NumTroopsMoveSlider.setMajorTickSpacing(1);
		NumTroopsMoveSlider.setMaximum(5);
		NumTroopsMoveSlider.setBounds(211, 20, 200, 46);
		MoveTroopPane.add(NumTroopsMoveSlider);

		JButton CommitTroopMove = new JButton("");
		CommitTroopMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				app.client.sendData("for " + NumTroopsMoveSlider.getValue());
			}
		});
		CommitTroopMove.setIcon(new ImageIcon(app.graphics.okCheck));
		CommitTroopMove.setBounds(440, 5, 100, 35);
		MoveTroopPane.add(CommitTroopMove);

		JButton SkipTroopMoveButton = new JButton("");
		SkipTroopMoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				app.client.sendData("fin");
			}
		});
		SkipTroopMoveButton.setIcon(new ImageIcon(app.graphics.cancel));
		SkipTroopMoveButton.setBounds(440, 40, 100, 35);
		MoveTroopPane.add(SkipTroopMoveButton);
	}

	private void createEndTurnPane() {
		EndTurnPane = new JPanel();
		Phase.addTab("", new ImageIcon(app.graphics.phaseBegin), EndTurnPane,
				null);
		EndTurnPane.setLayout(null);
	}

	private void populatePlayerPane() {
		EndTurnPane.removeAll();
		for (int i = 0; i < players.size(); i++) {
			GUIAvatarPanel p = new GUIAvatarPanel(players.get(i), color.get(i),
					app.graphics.avatar);
			p.setLocation(i * (1 + p.getWidth()), 0);
			EndTurnPane.add(p);
		}
		EndTurnPane.repaint();
	}

	private void createChatPane() {
		ChatContainer = new JPanel();
		ChatContainer.setOpaque(false);
		ChatContainer.setBorder(new TitledBorder(null, "Chat",
				TitledBorder.LEADING, TitledBorder.TOP, null, UIManager
						.getColor("infoText")));
		ChatContainer.setSize(220, 190);
		this.add(ChatContainer);
		ChatContainer.setLayout(null);

		ChatField = new JTextField();
		ChatField.setBounds(6, 20, 155, 27);
		ChatContainer.add(ChatField);
		ChatField.setColumns(10);

		JButton SayButton = new JButton("SAY");
		SayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("CHAT MESSEGE SENT");
			}
		});
		SayButton.setBounds(161, 20, 50, 27);
		ChatContainer.add(SayButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 46, 205, 138);
		ChatContainer.add(scrollPane);

		JTextArea ServerChat = new JTextArea();
		ServerChat.setLineWrap(true);
		scrollPane.setViewportView(ServerChat);
	}

	public void updateUiPos(boolean full) {
		if (!full) {
			Phase.setLocation(
					app.size.width - Phase.getWidth() - Phase.getInsets().right,
					app.size.height - Phase.getHeight() - Phase.getInsets().top
							* 3 - 3);
			ChatContainer.setLocation(
					0,
					app.size.height - ChatContainer.getHeight()
							- ChatContainer.getInsets().top * 3 - 3);
		}
		if (map != null)
			map.setSize(app.size.width, app.size.height);
	}

	private JTextField ChatField;

	public void setMap(MapLoader map) {
		this.map = new GuiMap(this, map, false, app.size.width, app.size.height);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map != null)
			map.paint(g);
	}

	private void swap(int one, int two) {
		String temp = players.get(one);
		players.set(one, players.get(two));
		players.set(two, temp);
		int c = color.get(one);
		color.set(one, color.get(two));
		color.set(two, c);
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(string);
		try {
			String cmd = scan.next();
			if (cmd.equals("order")) {
				scan.useDelimiter("/");
				scan.next();
				for (int i = 0; i < players.size(); i++) {
					String name = scan.next();
					int pos = players.indexOf(name);
					swap(pos, i);
				}
				this.populatePlayerPane();
				((MapLoader) app.panels[4]).progress++;
				System.out.println("I should be loaded "+((MapLoader) app.panels[4]).progress);
			} else if (cmd.equals("set")) {
				scan.useDelimiter("/");
				String name = scan.next();
				name = name.substring(1);
				String ctry = scan.next();
				int count = scan.nextInt();
				int pos = players.indexOf(name);
				map.set(count, color.get(pos), ctry);
			} else if (cmd.equals("turn")) {
				String p = scan.next();
				if (app.userName.equals(p)) {
					myTurn = true;

				} else {
					myTurn = false;
					this.switchPhase("begin");
				}
				if (!players.get(0).equals(p)) {
					players.add(players.remove(0));
					color.add(color.remove(0));
				}
				this.populatePlayerPane();
			} else if (cmd.equals("phase")) {
				cmd = scan.next();
				if (cmd.equals("recruit")) {
					this.switchPhase("recruit");
					int val = scan.nextInt();
					this.progressBar.setValue(val);
					this.TroopPlaceSlider.setValue(0);
					this.TroopPlaceSlider.setMaximum(val);
				} else if (cmd.equals("attack")) {
					this.switchPhase("attack");
					def = false;
				} else if (cmd.equals("fortify")) {
					this.switchPhase("fortify");
					// TODO fortify
				}
			} else if (cmd.equals("delum")) {
				this.map.delum();
			} else if (cmd.equals("normlum")) {
				this.map.normlum();
				this.repaint();
			} else if (cmd.equals("lum")) {
				cmd = scan.next();
				while (scan.hasNext())
					cmd += " " + scan.next();
				this.map.lum(cmd);
			} else if (cmd.equals("lum2")) {
				cmd = scan.next();
				while (scan.hasNext())
					cmd += " " + scan.next();
				this.map.lum(cmd);
				this.map.lum(cmd);
			} else if (cmd.equals("paint")) {
				this.repaint();
			} else if (cmd.equals("atk")) {
				scan.useDelimiter("/");
				String u1 = scan.next();
				u1 = u1.substring(1);
				String u2 = scan.next();
				String c1 = scan.next();
				String c2 = scan.next();
				if (u1.equals(app.userName) || u2.equals(app.userName)) {
					map.enterAttack(map.countryNames.indexOf(c1),
							map.countryNames.indexOf(c2));
					if (u1.equals(app.userName)) {
						this.updateDiceSlider(c1);
					} else {
						def = true;
						this.switchPhase("attack");
						this.updateDiceSlider(c2);
					}
				}
			} else if (cmd.equals("dmg")) {
				int n1 = scan.nextInt();
				int n2 = scan.nextInt();
				map.play(n1, n2);
				while (map.playing) {
				}
				if (!def) {
					this.RollDiceButton.setEnabled(true);
					this.AttackSkipButton.setEnabled(true);
				}
				this.AttackRollDisp.setText(n1 + "");
				this.DeffRollDisp.setText(n2 + "");
			} else if (cmd.equals("win")) {
				JOptionPane.showMessageDialog(null, "You Won the Match");
				map.exitAttack();
				this.switchPhase("attack");
				if (def) {
					this.switchPhase("begin");
				}
			} else if (cmd.equals("lose")) {
				JOptionPane.showMessageDialog(null, "You Lost the Match");
				map.exitAttack();
				if (def) {
					this.switchPhase("begin");
				}
			} else if (cmd.equals("end")) {
				map.exitAttack();
				if (def) {
					def = false;
					this.repaint();
				} else {
					this.RollDiceButton.setEnabled(true);
				}
			}
		} catch (Exception e) {
			System.out.println(string);
			e.printStackTrace();
		}
	}

	private void updateDiceSlider(String ctry) {
		int num = map.countryNames.indexOf(ctry);
		int val = map.countries.get(num).troopCount;
		if (def && val > 2)
			val = 2;
		else if (!def && val > 3)
			val = 3;
		this.DiceSlider.setMaximum(val);
	}

	public void reset() {
		players = null;
		color = null;
		pTurn = null;
	}

	public void addPlayer(String text, int i) {
		System.out.println(text);
		if (this.players == null) {
			players = new ArrayList<String>();
			this.color = new ArrayList<Integer>();
			pTurn = new ArrayList<String>();
		}
		players.add(text);
		color.add(i);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragging = true;
		Point cur = e.getPoint();
		int x = local.x - cur.x;
		int y = local.y - cur.y;
		local = cur;
		map.adjCenter(x * 5, y * 5);
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		local = e.getPoint();
	}

	boolean myTurn = false;

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!dragging) {
			int num = map.onCountry(e.getPoint());
			if (num > -1) {
				if (map.canSelect(num) && myTurn) {
					if (Phase.isEnabledAt(3)) {
						String name = map.countryNames.get(num);
						if (this.PlaceTroopNation1Disp.getText().length() == 0) {
							this.PlaceTroopNation1Disp.setText(name);
							int val = map.countries.get(num).troopCount - 1;
							this.NumTroopsMoveSlider.setMaximum(val);
						} else if (this.PlaceTroopNation1Disp.getText().equals(
								name)) {
							this.PlaceTroopNation1Disp.setText("");
							this.PlaceTroopNation2Disp.setText("");
						} else if (this.PlaceTroopNation2Disp.getText()
								.length() == 0) {
							this.PlaceTroopNation2Disp.setText(name);
							int val = map.countries.get(num).troopCount - 1;
							this.NumTroopsMoveSlider.setMinimum(val * -1);
							this.NumTroopsMoveSlider.setValue(0);
						} else if (this.PlaceTroopNation2Disp.getText().equals(
								name)) {
							this.PlaceTroopNation2Disp.setText("");
						}
					}
					app.client.sendData("sel " + map.getCountryName(num));
				}
			}
		}
		dragging = false;
	}
}
