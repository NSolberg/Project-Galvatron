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
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Application implements ActionListener {
	private JMenuBar menubar;
	private GUIWindow window;
	private JCheckBoxMenuItem selRes, selMode;
	private Dimension size;
	private JPanel[] panels;

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
		GUIGamePanel gp = new GUIGamePanel();
		GUILogOnPanel lp = new GUILogOnPanel();
		GUIServerPanel sp = new GUIServerPanel();
		panels = new JPanel[3];
		panels[0] = lp;
		panels[1] = sp;
		panels[2] = gp;
	}

	private void createWindow(boolean Fullscrean) {
		if (window != null)
			window.dispose();
		window = new GUIWindow(size, Fullscrean);
		window.setJMenuBar(menubar);
		window.setLayout(new CardLayout());
		window.add(panels[0],"LogOn");
		window.add(panels[1],"Server");
		window.add(panels[2],"Game");
	}

	private void createMenu() {
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();
		menubar = new JMenuBar();
		JMenu file = new JMenu("View");
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
		file.add(res);
		JCheckBoxMenuItem win = new JCheckBoxMenuItem("Windowed");
		win.setSelected(true);
		win.addActionListener(this);
		selMode = win;
		JCheckBoxMenuItem full = new JCheckBoxMenuItem("Fullscreen");
		full.addActionListener(this);
		file.add(win);
		file.add(full);
		menubar.add(file);
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
}