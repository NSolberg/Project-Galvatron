package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class MapLoader extends JPanel {
	public int progress;
	public Image[] images;
	public String filename;
	private Application app;
	private JProgressBar pbar;
	private int curwid;

	public MapLoader(Application application) {
		progress = 0;
		app = application;
		init();
	}

	private void init() {
		pbar = new JProgressBar();
		pbar.setMaximum(50);
		pbar.setValue(0);
		pbar.setb
		pbar.setSize(app.size.width / 3 * 2, 50);
		pbar.setLocation(app.size.width / 2 - pbar.size().width / 2,
				app.size.height / 2 + pbar.size().height);
		curwid = app.size.width;
		this.setLayout(null);
		this.add(pbar);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (app.size.width != curwid) {
			pbar.setSize(app.size.width / 3 * 2, 50);
			pbar.setLocation(app.size.width / 2 - pbar.size().width / 2,
					app.size.height / 2 + pbar.size().height);
			curwid = app.size.width;
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, app.size.width, app.size.height);
		g.drawImage(app.graphics.loadtitle, 0, 0, app.size.width,
				app.size.height, 0, 0, app.graphics.loadtitle.getWidth(null),
				app.graphics.loadtitle.getHeight(null), null);
	}

	public void loadMap(String file, String[] pNames, int[] pC) {
		filename = "Maps/" + file;
		pbar.setValue(0);
		progress = 0;
		Timer t = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pbar.setValue(progress);
				if (pbar.getMaximum() == progress) {
					app.switchView(3);
				}
			}

		});
		t.start();
		images = new Image[6];
		images[0] = loadImage("Maps/" + file + "/map.png");
		progress++;
		images[1] = loadImage("Maps/" + file + "/marker.png");
		progress++;
		images[2] = loadImage("Maps/" + file + "/soldier.png");
		progress++;
		images[3] = loadImage("Maps/" + file + "/calvery.png");
		progress++;
		images[4] = loadImage("Maps/" + file + "/tank.png");
		progress++;
		images[5] = loadImage("Maps/" + file + "/battleground.png");
		progress++;
		app.setMap(this);
		GUIGamePanel g = (GUIGamePanel) app.panels[2];
		for (int i = 0; i < 6; i++) {
			if (pNames[i].length() > 0)
				g.addPlayer(pNames[i], pC[i]);
		}
		progress++;
	}

	private Image loadImage(String location) {
		Image out = new ImageIcon(location).getImage();
		return out;
	}

}
