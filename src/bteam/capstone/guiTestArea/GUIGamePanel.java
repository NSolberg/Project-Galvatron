package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import bteam.capstone.gui.GuiMap;

@SuppressWarnings("serial")
public class GUIGamePanel extends JPanel {
	private Dimension screensize;
	public GUIGamePanel(Dimension SCREENSIZE){
		screensize = SCREENSIZE;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	Point local;
	private JTextField ChatField;
	private void initialize() {
		//frmRiskOnlinepre = new JFrame();
		//frmRiskOnlinepre.setTitle("RISK ONLINE (PRE ALPHA VIS TEST VER 1.46.43)");
		//frmRiskOnlinepre.setBounds(100, 100, 837, 622);
		//frmRiskOnlinepre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmRiskOnlinepre.getContentPane().setLayout(null);
		
		final GuiMap map = new GuiMap("Maps/Earth",false,(int)(screensize.getWidth()), (int)(screensize.getHeight()));
		//Overiding paintComponent without overiiding class
		
		/*JPanel this = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
			super.paintComponent(g);
				map.paint(g);
			}
		};*/
		
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				local = e.getLocationOnScreen();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Point cur = e.getLocationOnScreen();
				int  x = local.x-cur.x;
				int y = local.y-cur.y;
				map.adjCenter(x*4, x*4);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.setBounds(6, 6, 825, 600);
		//frmRiskOnlinepre.getContentPane().add(ORIGIONPANNLE);
		this.setLayout(null);
		
		
		JTabbedPane Phase = new JTabbedPane(JTabbedPane.TOP);
		Phase.setBorder(new TitledBorder(null, "Phase", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		//237, 404, 594, 190
		Phase.setBounds(237, 404, 594, 190);
		this.add(Phase);
		
		JPanel CardPanle = new JPanel();
		CardPanle.setForeground(SystemColor.menu);
		Phase.addTab("\n", new ImageIcon("Icons For Risk/referee-cards-icon.png"), CardPanle, null);
		CardPanle.setLayout(null);
		
		JPanel Card2 = new JPanel();
		Card2.setOpaque(false);
		Card2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Card2.setBackground(Color.BLUE);
		Card2.setForeground(UIManager.getColor("nimbusFocus"));
		Card2.setBounds(53, 0, 51, 71);
		CardPanle.add(Card2);
		
		JPanel Card3 = new JPanel();
		Card3.setOpaque(false);
		Card3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Card3.setBackground(Color.BLUE);
		Card3.setForeground(UIManager.getColor("List.dropLineColor"));
		Card3.setBounds(106, 0, 51, 71);
		CardPanle.add(Card3);
		
		JPanel Card4 = new JPanel();
		Card4.setOpaque(false);
		Card4.setOpaque(false);
		Card4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card4.setForeground(UIManager.getColor("List.dropLineColor"));
		Card4.setBackground(Color.BLUE);
		Card4.setBounds(159, 0, 51, 71);
		CardPanle.add(Card4);
		
		JPanel Card8 = new JPanel();
		Card8.setOpaque(false);
		Card8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card8.setForeground(UIManager.getColor("List.dropLineColor"));
		Card8.setBackground(Color.BLUE);
		Card8.setBounds(367, 0, 51, 71);
		CardPanle.add(Card8);
		
		JPanel Card5 = new JPanel();
		Card5.setOpaque(false);
		Card5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card5.setForeground(UIManager.getColor("List.dropLineColor"));
		Card5.setBackground(Color.BLUE);
		Card5.setBounds(211, 0, 51, 71);
		CardPanle.add(Card5);
		
		JPanel Card6 = new JPanel();
		Card6.setOpaque(false);
		Card6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card6.setForeground(Color.WHITE);
		Card6.setBackground(Color.BLUE);
		Card6.setBounds(263, 0, 51, 71);
		CardPanle.add(Card6);
		
		JPanel Card7 = new JPanel();
		Card7.setOpaque(false);
		Card7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card7.setForeground(Color.WHITE);
		Card7.setBackground(Color.BLUE);
		Card7.setBounds(315, 0, 51, 71);
		CardPanle.add(Card7);
		
		JButton TurnInButton = new JButton("");
		TurnInButton.setIcon(new ImageIcon("Icons For Risk/SMALLActions-dialog-ok-apply-icon-1.png"));
		TurnInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("CARDS TURNED IN");
			}
		});
		TurnInButton.setFont(new Font("Stencil", Font.BOLD, 15));
		TurnInButton.setBounds(470, 0, 90, 38);
		CardPanle.add(TurnInButton);
		final Image card1img = new ImageIcon("Icons For Risk/Card1.png").getImage();
		JPanel Card1 = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
			super.paintComponent(g);
				g.drawImage(card1img, 0, 0,this.getWidth(), this.getHeight(), 0,0 ,card1img.getWidth(null), card1img.getHeight(null), null);
			}
		};
		Card1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Card1.setForeground(Color.WHITE);
		Card1.setBackground(Color.BLUE);
		Card1.setBounds(0, 0, 51, 71);
		CardPanle.add(Card1);
		
		JPanel Card9 = new JPanel();
		Card9.setOpaque(false);
		Card9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		Card9.setForeground(Color.WHITE);
		Card9.setBackground(Color.BLUE);
		Card9.setBounds(419, 0, 51, 71);
		CardPanle.add(Card9);
		
		JButton SkipTurnInButton = new JButton("");
		SkipTurnInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("SKIPPED CARD TURN IN");
			}
		});
		SkipTurnInButton.setIcon(new ImageIcon("Icons For Risk/SMALLAlarm-Error-icon-1.png"));
		SkipTurnInButton.setFont(new Font("Stencil", Font.BOLD, 14));
		SkipTurnInButton.setBounds(470, 36, 94, 43);
		CardPanle.add(SkipTurnInButton);
		
		JPanel PlaceTroopsPannle = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/ammo-5-icon.png"), PlaceTroopsPannle, "Displays the remaining troops\n");
		PlaceTroopsPannle.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setStringPainted(true);
		progressBar.setValue(50);
		progressBar.setBounds(6, 6, 181, 73);
		PlaceTroopsPannle.add(progressBar);
		
		JLabel lable = new JLabel("TROOPS TO PLACE:");
		lable.setFont(new Font("Stencil", Font.BOLD, 12));
		lable.setBounds(199, 6, 112, 15);
		PlaceTroopsPannle.add(lable);
		
		JSlider TroopPlaceSlider = new JSlider();
		TroopPlaceSlider.setSnapToTicks(true);
		TroopPlaceSlider.setPaintTicks(true);
		TroopPlaceSlider.setPaintLabels(true);
		TroopPlaceSlider.setMinorTickSpacing(5);
		TroopPlaceSlider.setMaximum(50);
		TroopPlaceSlider.setBounds(199, 33, 200, 46);
		PlaceTroopsPannle.add(TroopPlaceSlider);
		
		JButton TroopPlaceCommitButton = new JButton("\n");
		TroopPlaceCommitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("TROOP PLACEMENT COMMITED");
			}
		});
		TroopPlaceCommitButton.setIcon(new ImageIcon("Icons For Risk/Actions-dialog-ok-apply-icon.png"));
		TroopPlaceCommitButton.setBounds(420, 6, 132, 73);
		PlaceTroopsPannle.add(TroopPlaceCommitButton);
		
		//MODIFIYED FOR DEMO
		
		
		JPanel AttackPannle = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/Peters-Sword-icon.png"), AttackPannle, null);
		AttackPannle.setLayout(null);
		
		JSlider DiceSlider = new JSlider();
		DiceSlider.setFocusTraversalKeysEnabled(false);
		DiceSlider.setSnapToTicks(true);
		DiceSlider.setPaintTicks(true);
		DiceSlider.setPaintLabels(true);
		DiceSlider.setMinorTickSpacing(1);
		DiceSlider.setMinimum(1);
		DiceSlider.setMaximum(3);
		DiceSlider.setBounds(6, 6, 200, 53);
		AttackPannle.add(DiceSlider);
		
		JLabel lblNumberOfTroops = new JLabel("NUMBER OF TROOPS");
		lblNumberOfTroops.setFont(new Font("Stencil", Font.BOLD, 14));
		lblNumberOfTroops.setBounds(42, 44, 134, 15);
		AttackPannle.add(lblNumberOfTroops);
		
		JButton RollDiceButton = new JButton("");
		RollDiceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("NUMDICE ROLLED");
			}
		});
		RollDiceButton.setIcon(new ImageIcon("Icons For Risk/dice-icon.png"));
		RollDiceButton.setBounds(218, 6, 113, 73);
		AttackPannle.add(RollDiceButton);
		
		JTextPane DeffRollDisp = new JTextPane();
		DeffRollDisp.setBounds(343, 22, 40, 37);
		AttackPannle.add(DeffRollDisp);
		
		JTextPane AttackRollDisp = new JTextPane();
		AttackRollDisp.setBounds(408, 22, 40, 37);
		AttackPannle.add(AttackRollDisp);
		
		JLabel lblDef = new JLabel("DEF");
		lblDef.setFont(new Font("Stencil", Font.PLAIN, 12));
		lblDef.setBounds(343, 64, 57, 15);
		AttackPannle.add(lblDef);
		
		JLabel lblAttk = new JLabel("ATTK");
		lblAttk.setFont(new Font("Stencil", Font.PLAIN, 12));
		lblAttk.setBounds(408, 64, 57, 15);
		AttackPannle.add(lblAttk);
		
		JButton AttackSkipButton = new JButton("");
		AttackSkipButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("ATTACK PHASE SKIPPED");
			}
		});
		AttackSkipButton.setIcon(new ImageIcon("Icons For Risk/Alarm-Error-icon.png"));
		AttackSkipButton.setBounds(472, 22, 94, 57);
		AttackPannle.add(AttackSkipButton);
		
		JPanel MoveTroopsTab = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/world-icon.png"), MoveTroopsTab, null);
		MoveTroopsTab.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NATION 1");
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 14));
		lblNewLabel.setBounds(6, 64, 76, 15);
		MoveTroopsTab.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NATION 2");
		lblNewLabel_1.setFont(new Font("Stencil", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(112, 63, 82, 15);
		MoveTroopsTab.add(lblNewLabel_1);
		
		JTextPane PlaceTroopNationOneDisp = new JTextPane();
		PlaceTroopNationOneDisp.setBounds(6, 29, 76, 23);
		MoveTroopsTab.add(PlaceTroopNationOneDisp);
		
		JTextPane PlaceTroopNation2Disp = new JTextPane();
		PlaceTroopNation2Disp.setBounds(112, 29, 82, 23);
		MoveTroopsTab.add(PlaceTroopNation2Disp);
		
		JSlider NumTroopsMoveSlider = new JSlider();
		NumTroopsMoveSlider.setMinorTickSpacing(5);
		NumTroopsMoveSlider.setPaintTicks(true);
		NumTroopsMoveSlider.setSnapToTicks(true);
		NumTroopsMoveSlider.setPaintLabels(true);
		NumTroopsMoveSlider.setBounds(211, 31, 200, 21);
		MoveTroopsTab.add(NumTroopsMoveSlider);
		
		JButton CommitTroopMove = new JButton("");
		CommitTroopMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TROOP REDEPLOY COMMITED");
			}
		});
		CommitTroopMove.setIcon(new ImageIcon("Icons For Risk/SMALLActions-dialog-ok-apply-icon-1.png"));
		CommitTroopMove.setBounds(460, 6, 100, 46);
		MoveTroopsTab.add(CommitTroopMove);
		
		JButton SkipTroopMoveButton = new JButton("");
		SkipTroopMoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TROOP REDEPLOYMENT SKIPPED");
			}
		});
		SkipTroopMoveButton.setIcon(new ImageIcon("Icons For Risk/SMALLAlarm-Error-icon-1.png"));
		SkipTroopMoveButton.setBounds(460, 49, 100, 46);
		MoveTroopsTab.add(SkipTroopMoveButton);
		
		JPanel EndTurnPannle = new JPanel();
		Phase.addTab("", new ImageIcon("Icons For Risk/FUCKINGTINYActions-arrow-right-icon.png"), EndTurnPannle, null);
		EndTurnPannle.setLayout(null);
		
		JLabel lblEndTurn = new JLabel("END TURN?");
		lblEndTurn.setFont(new Font("Stencil", Font.BOLD | Font.ITALIC, 20));
		lblEndTurn.setBounds(202, 6, 131, 39);
		EndTurnPannle.add(lblEndTurn);
		
		JButton ConfirmEndTurnButton = new JButton("");
		ConfirmEndTurnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TURN OVER NEXT TURN");
			}
		});
		ConfirmEndTurnButton.setIcon(new ImageIcon("Icons For Risk/Actions-dialog-ok-apply-icon.png"));
		ConfirmEndTurnButton.setBounds(0, 6, 131, 73);
		EndTurnPannle.add(ConfirmEndTurnButton);
		
		JButton CancelEndTurnButton = new JButton("");
		CancelEndTurnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("TURN END CANCLED");
			}
		});
		CancelEndTurnButton.setIcon(new ImageIcon("Icons For Risk/Alarm-Error-icon.png"));
		CancelEndTurnButton.setBounds(429, 6, 131, 73);
		EndTurnPannle.add(CancelEndTurnButton);
		
		JPanel ChatContainer = new JPanel();
		ChatContainer.setOpaque(false);
		ChatContainer.setBorder(new TitledBorder(null, "Chat", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("infoText")));
		ChatContainer.setBounds(6, 404, 231, 190);
		this.add(ChatContainer);
		ChatContainer.setLayout(null);
		
		ChatField = new JTextField();
		ChatField.setBounds(6, 20, 162, 27);
		ChatContainer.add(ChatField);
		ChatField.setColumns(10);
		
		JButton SayButton = new JButton("SAY");
		SayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("CHAT MESSEGE SENT");
			}
		});
		SayButton.setBounds(171, 20, 54, 27);
		ChatContainer.add(SayButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 46, 219, 138);
		ChatContainer.add(scrollPane);
		
		JTextArea ServerChat = new JTextArea();
		ServerChat.setLineWrap(true);
		scrollPane.setViewportView(ServerChat);
		
		JPanel PlayerPannle = new JPanel();
		PlayerPannle.setOpaque(false);
		PlayerPannle.setBorder(new TitledBorder(null, "PlayerNameHere", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("infoText")));
		PlayerPannle.setBounds(6, 6, 126, 140);
		this.add(PlayerPannle);
		PlayerPannle.setLayout(null);
		
		final Image AvatarImage = new ImageIcon("Icons For Risk/army-officer-icon.png").getImage();
		JPanel AvatarPannle = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
			super.paintComponent(g);
				g.drawImage(AvatarImage, 0, 0,this.getWidth(), this.getHeight(), 0,0 ,AvatarImage.getWidth(null), AvatarImage.getHeight(null), null);
			}
		};
		AvatarPannle.setOpaque(false);
		AvatarPannle.setBounds(6, 23, 100, 100);
		PlayerPannle.add(AvatarPannle);
		AvatarPannle.setLayout(null);
		
		JPanel TeamColorPannle = new JPanel();
		TeamColorPannle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TeamColorPannle.setBackground(Color.GREEN);
		TeamColorPannle.setForeground(Color.GREEN);
		TeamColorPannle.setBounds(59, 60, 23, 19);
		AvatarPannle.add(TeamColorPannle);		
	}

}
