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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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

import bteam.capstone.gui.GuiMap;

@SuppressWarnings("serial")
public class GUIGamePanel extends JPanel implements ClientUser, ActionListener,
		MouseListener, MouseMotionListener {
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
	private JSlider TroopPlaceSlider;

	public GUIGamePanel(Application application) {
		app = application;
		screensize = app.size;
		dragging = false;
		sel = -1;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		Timer t = new Timer((int) (1000 / 30), this);
		t.start();
		// initialize();
		init();
		updateUiPos();
	}

	private void init() {

		this.setLayout(null);
		// Phase pane
		Phase = new JTabbedPane(JTabbedPane.TOP);
		Phase.setBorder(new TitledBorder(null, "Phase", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		Phase.setSize(600 - Phase.getInsets().right - Phase.getInsets().left,
				170);
		Phase.setLocation(0, 0);
		this.add(Phase);
		this.createCardPane();
		this.createPlaceTroopPane();
		this.createAttackPane();
		this.createMoveTroopPane();
		this.createEndTurnPane();
		this.createChatPane();
	}

	private void createCardPane() {
		// create card Pane
		CardPane = new JPanel();
		CardPane.setForeground(SystemColor.menu);
		Phase.addTab("\n", new ImageIcon(
				"Icons For Risk/referee-cards-icon.png"), CardPane, null);
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
		TurnInButton.setIcon(new ImageIcon(
				"Icons For Risk/SMALLActions-dialog-ok-apply-icon-1.png"));
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
		SkipTurnInButton.setIcon(new ImageIcon(
				"Icons For Risk/SMALLAlarm-Error-icon-1.png"));
		SkipTurnInButton.setFont(new Font("Stencil", Font.BOLD, 14));
		SkipTurnInButton.setSize(75, 35);
		SkipTurnInButton.setLocation(
				cards[8].getLocation().x + 2 + cards[8].getWidth(), 37);
		CardPane.add(SkipTurnInButton);
	}

	private void createPlaceTroopPane() {
		PlaceTroopPane = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/ammo-5-icon.png"),
				PlaceTroopPane, "Displays the remaining troops\n");
		PlaceTroopPane.setLayout(null);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setStringPainted(true);
		progressBar.setValue(50);
		progressBar.setBounds(0, 0, 181, 70);
		PlaceTroopPane.add(progressBar);

		JLabel lable = new JLabel("TROOPS TO PLACE:");
		lable.setFont(new Font("Stencil", Font.BOLD, 12));
		lable.setBounds(199, 6, 130, 15);
		PlaceTroopPane.add(lable);

		TroopPlaceSlider = new JSlider();
		TroopPlaceSlider.setSnapToTicks(true);
		TroopPlaceSlider.setPaintTicks(true);
		TroopPlaceSlider.setPaintLabels(true);
		TroopPlaceSlider.setMinorTickSpacing(5);
		TroopPlaceSlider.setMaximum(50);
		TroopPlaceSlider.setBounds(199, 33, 200, 46);
		PlaceTroopPane.add(TroopPlaceSlider);

		JButton TroopPlaceCommitButton = new JButton();
		TroopPlaceCommitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("TROOP PLACEMENT COMMITED");
			}
		});
		TroopPlaceCommitButton.setIcon(new ImageIcon(
				"Icons For Risk/Actions-dialog-ok-apply-icon.png"));
		TroopPlaceCommitButton.setBounds(420, 0, 125, 70);
		PlaceTroopPane.add(TroopPlaceCommitButton);
	}

	private void createAttackPane() {
		AttackPane = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/Peters-Sword-icon.png"),
				AttackPane, null);
		AttackPane.setLayout(null);

		JSlider DiceSlider = new JSlider();
		DiceSlider.setFocusTraversalKeysEnabled(false);
		DiceSlider.setSnapToTicks(true);
		DiceSlider.setPaintTicks(true);
		DiceSlider.setPaintLabels(true);
		DiceSlider.setMinorTickSpacing(1);
		DiceSlider.setMinimum(1);
		DiceSlider.setMaximum(3);
		DiceSlider.setBounds(6, 6, 200, 53);
		AttackPane.add(DiceSlider);

		JLabel lblNumberOfTroops = new JLabel("NUMBER OF TROOPS");
		lblNumberOfTroops.setFont(new Font("Stencil", Font.BOLD, 14));
		lblNumberOfTroops.setBounds(42, 44, 184, 15);
		AttackPane.add(lblNumberOfTroops);

		JButton RollDiceButton = new JButton("");
		RollDiceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("NUMDICE ROLLED");
			}
		});
		RollDiceButton.setIcon(new ImageIcon("Icons For Risk/dice-icon.png"));
		RollDiceButton.setBounds(218, 6, 113, 70);
		AttackPane.add(RollDiceButton);

		JTextPane DeffRollDisp = new JTextPane();
		DeffRollDisp.setBounds(343, 22, 40, 37);
		AttackPane.add(DeffRollDisp);

		JTextPane AttackRollDisp = new JTextPane();
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

		JButton AttackSkipButton = new JButton("");
		AttackSkipButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("ATTACK PHASE SKIPPED");
			}
		});
		AttackSkipButton.setIcon(new ImageIcon(
				"Icons For Risk/Alarm-Error-icon.png"));
		AttackSkipButton.setBounds(472, 6, 70, 70);
		AttackPane.add(AttackSkipButton);
	}

	private void createMoveTroopPane() {
		MoveTroopPane = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/world-icon.png"),
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

		JTextPane PlaceTroopNationOneDisp = new JTextPane();
		PlaceTroopNationOneDisp.setBounds(6, 29, 76, 23);
		MoveTroopPane.add(PlaceTroopNationOneDisp);

		JTextPane PlaceTroopNation2Disp = new JTextPane();
		PlaceTroopNation2Disp.setBounds(112, 29, 82, 23);
		MoveTroopPane.add(PlaceTroopNation2Disp);

		JSlider NumTroopsMoveSlider = new JSlider();
		NumTroopsMoveSlider.setMinorTickSpacing(5);
		NumTroopsMoveSlider.setPaintTicks(true);
		NumTroopsMoveSlider.setSnapToTicks(true);
		NumTroopsMoveSlider.setPaintLabels(true);
		NumTroopsMoveSlider.setBounds(211, 31, 200, 21);
		MoveTroopPane.add(NumTroopsMoveSlider);

		JButton CommitTroopMove = new JButton("");
		CommitTroopMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TROOP REDEPLOY COMMITED");
			}
		});
		CommitTroopMove.setIcon(new ImageIcon(
				"Icons For Risk/SMALLActions-dialog-ok-apply-icon-1.png"));
		CommitTroopMove.setBounds(440, 0, 100, 35);
		MoveTroopPane.add(CommitTroopMove);

		JButton SkipTroopMoveButton = new JButton("");
		SkipTroopMoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TROOP REDEPLOYMENT SKIPPED");
			}
		});
		SkipTroopMoveButton.setIcon(new ImageIcon(
				"Icons For Risk/SMALLAlarm-Error-icon-1.png"));
		SkipTroopMoveButton.setBounds(440, 35, 100, 35);
		MoveTroopPane.add(SkipTroopMoveButton);
	}

	private void createEndTurnPane() {
		EndTurnPane = new JPanel();
		Phase.addTab("", new ImageIcon(
				"Icons For Risk/FUCKINGTINYActions-arrow-right-icon.png"),
				EndTurnPane, null);
		EndTurnPane.setLayout(null);

		JLabel lblEndTurn = new JLabel("END TURN?");
		lblEndTurn.setFont(new Font("Stencil", Font.BOLD | Font.ITALIC, 20));
		lblEndTurn.setBounds(202, 6, 131, 39);
		EndTurnPane.add(lblEndTurn);

		JButton ConfirmEndTurnButton = new JButton("");
		ConfirmEndTurnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TURN OVER NEXT TURN");
			}
		});
		ConfirmEndTurnButton.setIcon(new ImageIcon(
				"Icons For Risk/Actions-dialog-ok-apply-icon.png"));
		ConfirmEndTurnButton.setBounds(0, 6, 131, 70);
		EndTurnPane.add(ConfirmEndTurnButton);

		JButton CancelEndTurnButton = new JButton("");
		CancelEndTurnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TURN END CANCLED");
			}
		});
		CancelEndTurnButton.setIcon(new ImageIcon(
				"Icons For Risk/Alarm-Error-icon.png"));
		CancelEndTurnButton.setBounds(410, 6, 131, 70);
		EndTurnPane.add(CancelEndTurnButton);
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

	private void updateUiPos() {
		Phase.setLocation(app.size.width - Phase.getWidth()
				- Phase.getInsets().right, app.size.height - Phase.getHeight()
				- Phase.getInsets().top * 3 - 3);
		ChatContainer.setLocation(
				0,
				app.size.height - ChatContainer.getHeight()
						- ChatContainer.getInsets().top * 3 - 3);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private JTextField ChatField;

	private void initialize() {
		// MODIFIYED FOR DEMO

		JPanel PlayerPannle = new JPanel();
		PlayerPannle.setOpaque(false);
		PlayerPannle.setBorder(new TitledBorder(null, "PlayerNameHere",
				TitledBorder.LEADING, TitledBorder.TOP, null, UIManager
						.getColor("infoText")));
		PlayerPannle.setBounds(6, 6, 126, 140);
		this.add(PlayerPannle);
		PlayerPannle.setLayout(null);

		final Image AvatarImage = new ImageIcon(
				"Icons For Risk/army-officer-icon.png").getImage();
		JPanel AvatarPannle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(AvatarImage, 0, 0, this.getWidth(),
						this.getHeight(), 0, 0, AvatarImage.getWidth(null),
						AvatarImage.getHeight(null), null);
			}
		};
		AvatarPannle.setOpaque(false);
		AvatarPannle.setBounds(6, 23, 100, 100);
		PlayerPannle.add(AvatarPannle);
		AvatarPannle.setLayout(null);

		JPanel TeamColorPannle = new JPanel();
		TeamColorPannle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		TeamColorPannle.setBackground(Color.GREEN);
		TeamColorPannle.setForeground(Color.GREEN);
		TeamColorPannle.setBounds(59, 60, 23, 19);
		AvatarPannle.add(TeamColorPannle);
	}

	public void setMap(String map) {
		this.map = new GuiMap("Maps/" + map, false, app.size.width,
				app.size.height);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map != null)
			map.paint(g);
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!dragging) {
			int num = map.onCountry(e.getPoint());
			if (num > -1) {
				if (sel == -1 || sel == 42)
					sel = num;
				else if (sel == num)
					sel = 42;
				map.select(num);
				this.repaint();
			}
		}
		dragging = false;
		if (map.inAttack()) {
			map.play();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (map != null && map.inAttack()) {
			this.repaint();
		}
	}

}
