package bteam.capstone.guiTestArea;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Application implements ActionListener {
	private JMenuBar menubar;
	private GUIWindow window;
	private JCheckBoxMenuItem selRes, selMode;
	private JPanel[] panels;
	private JPanel mainpanel;
	public Client client;
	public Dimension size;
	public JMenu game;
	public JMenuItem start,quit;
	
	public static void main(String[] args) {
		new Application();
	}

	public Application() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

		}
		createMenu();
		size = new Dimension(800, 600);
		createPanels();
		createWindow(false);
	}

	private void createPanels() {
		GUIGamePanel gp = new GUIGamePanel(this);
		GUILogOnPanel lp = new GUILogOnPanel(this);
		GUIServerPanel sp = new GUIServerPanel(this);
		panels = new JPanel[3];
		panels[0] = lp;
		panels[1] = sp;
		panels[2] = gp;
		mainpanel = new JPanel();
		mainpanel.setLayout(new CardLayout());
		mainpanel.add(panels[0], "LogOn");
		mainpanel.add(panels[1], "Server");
		mainpanel.add(panels[2], "Game");
		CardLayout c1 = (CardLayout) mainpanel.getLayout();
		c1.show(mainpanel, "Game");
		this.client = new Client(lp);
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
		JMenu view = new JMenu("View");
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
		menubar.add(view);

		game = new JMenu("Game");
		game.setEnabled(false);
		quit = new JMenuItem("Quit");
		start = new JMenuItem("Start");
		game.add(start);
		game.add(quit);
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				client.sendData("start");
			}
			
		});
		quit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				client.sendData("leave");
			}
			
		});
		menubar.add(game);
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
				} else {
					window.exitFullscreen();
					this.createWindow(false);
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
			((GUIServerPanel) panels[1]).refresh();
			break;
		case 2:
			c1.show(mainpanel, "Game");
			client.switchController((ClientUser) panels[2]);
			break;
		}
	}
	
	public void setMap(String map){
		((GUIGamePanel)(panels[2])).setMap(map);
	}
}