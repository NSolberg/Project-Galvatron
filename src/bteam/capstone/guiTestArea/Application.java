package bteam.capstone.guiTestArea;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import bteam.capstone.gui.GuiMap;
import bteam.capstone.risk.Map;

public class Application implements ActionListener {
	private JMenuBar menubar;
	private GUIWindow window;
	private JCheckBoxMenuItem selRes, selMode;
	public JPanel[] panels;
	private JPanel mainpanel;
	public Client client;
	public Dimension size;
	public JMenu game;
	public JMenuItem start, quit;
	public String userName;
	public boolean fullscreen = false;
	public RiskGraphics graphics;
	public String gameFile = "";
	public JMenuItem logout;
	public boolean inGame = false;
	private Application app;

	public static void main(String[] args) {
		new Application();
	}

	public Application() {
		// line below opens web page
		// java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://www.google.com"));
		app = this;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

		}
		graphics = new RiskGraphics();
		createMenu();
		size = new Dimension(800, 600);
		createPanels();
		createWindow(false);
	}

	private void createPanels() {
		GUIGamePanel gp = new GUIGamePanel(this);
		GUILogOnPanel lp = new GUILogOnPanel(this);
		serverBrowser sp = new serverBrowser(this);
		GUILobby lobby = new GUILobby(this);
		panels = new JPanel[5];
		panels[0] = lp;
		panels[1] = sp;
		panels[2] = gp;
		panels[3] = lobby;
		panels[4] = new MapLoader(this);
		mainpanel = new JPanel();
		mainpanel.setLayout(new CardLayout());
		mainpanel.add(panels[0], "LogOn");
		mainpanel.add(panels[1], "Server");
		mainpanel.add(panels[2], "Game");
		mainpanel.add(panels[3], "Lobby");
		mainpanel.add(panels[4], "LoadMap");
		CardLayout c1 = (CardLayout) mainpanel.getLayout();
		c1.show(mainpanel, "LogOn");
		this.client = new Client((ClientUser)panels[0],this);
	}

	private void createWindow(boolean Fullscrean) {
		if (window != null)
			window.dispose();
		window = new GUIWindow(size, Fullscrean);
		window.setJMenuBar(menubar);
		window.setLayout(new CardLayout());
		window.add(mainpanel);
	}

	private void createMenu() {
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();
		menubar = new JMenuBar();
		JMenu view = new JMenu("Options");
		JMenu res = new JMenu("Resolution");
		ArrayList<String> items = new ArrayList<String>();
		for (DisplayMode mode : devices[0].getDisplayModes()) {
			if (mode.getWidth() >= 800) {
				String temp = mode.getWidth() + " " + mode.getHeight();
				if (!items.contains(temp)) {
					items.add(temp);
					JCheckBoxMenuItem t = new JCheckBoxMenuItem(temp);
					t.addActionListener(this);
					if (mode.getWidth() == 800 && mode.getHeight() == 600) {
						t.setSelected(true);
						this.selRes = t;
					}
					res.add(t);
				}
			}
		}
		view.add(res);
		JCheckBoxMenuItem win = new JCheckBoxMenuItem("Windowed");
		win.setSelected(true);
		win.addActionListener(this);
		selMode = win;
		JCheckBoxMenuItem full = new JCheckBoxMenuItem("Fullscreen");
		full.addActionListener(this);
		view.add(win);
		view.add(full);
		view.add(new JSeparator());
		logout = new JMenuItem("Log Out");
		logout.setEnabled(false);
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean cont = true;
				if (inGame) {
					int num= JOptionPane
							.showConfirmDialog(
									null,
									"You are in a game if you logout you will be removed and will not be able to rejoin. Are you sure you want to log out?",
								"Warning", JOptionPane.YES_NO_OPTION);
					if(num==JOptionPane.NO_OPTION)
						cont = false;
				}
				if(cont){
					client.sendData("exit");
					client = new Client((ClientUser)panels[0],app);
				}
			}

		});
		view.add(logout);
		menubar.add(view);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem m = (JCheckBoxMenuItem) e.getSource();
			if (m.getText().equals("Fullscreen")
					|| m.getText().equals("Windowed")) {
				this.selMode.setSelected(false);
				this.selMode = m;
				if (this.selMode.getText().equals("Fullscreen")) {
					this.createWindow(true);
					fullscreen = true;
				} else {
					window.exitFullscreen();
					this.createWindow(false);
					fullscreen = false;
				}

			} else {
				this.selRes.setSelected(false);
				this.selRes = (JCheckBoxMenuItem) e.getSource();
				Scanner scan = new Scanner(selRes.getText());
				int n1 = scan.nextInt();
				int n2 = scan.nextInt();
				this.size = new Dimension(n1, n2);
				this.window.changeSize(this.size);
			}
			((GUIGamePanel) this.panels[2]).updateUiPos(fullscreen);
		}
	}

	public void switchView(int view) {
		CardLayout c1 = (CardLayout) mainpanel.getLayout();
		switch (view) {
		case 0:
			c1.show(mainpanel, "LogOn");
			client.switchController((ClientUser) panels[0]);
			break;
		case 1:
			c1.show(mainpanel, "Server");
			client.switchController((ClientUser) panels[1]);
			((serverBrowser) panels[1]).refresh();
			break;
		case 2:
			c1.show(mainpanel, "Lobby");
			client.switchController((ClientUser) panels[3]);
			client.sendData("state");
			break;
		case 3:
			c1.show(mainpanel, "Game");
			// client.switchController((ClientUser) panels[2]);
			// client.sendData("state");
			break;
		case 4:
			c1.show(mainpanel, "LoadMap");
			client.switchController((ClientUser) panels[2]);
			break;
		}
	}

	public void setMap(MapLoader map) {
		((GUIGamePanel) (panels[2])).setMap(map);
	}
}
