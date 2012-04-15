package CompleteClient;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUIWindow extends JFrame {
	private boolean fullscreen;
	private DisplayMode dm;
	private GraphicsEnvironment env = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private GraphicsDevice gDevice = env.getDefaultScreenDevice();

	public GUIWindow(Dimension size, boolean Fullscreen) {
		fullscreen = Fullscreen;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (Fullscreen) {
			dm = new DisplayMode(size.width, size.height, 32,
					DisplayMode.REFRESH_RATE_UNKNOWN);
			this.setUndecorated(true);
			this.setResizable(false);
			gDevice.setFullScreenWindow(this);
			if (dm != null && gDevice.isDisplayChangeSupported()) {
				gDevice.setDisplayMode(dm);
			}
		} else {
			this.setSize(size);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
	}

	public void changeSize(Dimension size) {
		if (fullscreen) {
			dm = new DisplayMode(size.width, size.height, 32,
					DisplayMode.REFRESH_RATE_UNKNOWN);
			if (dm != null && gDevice.isDisplayChangeSupported()) {
				gDevice.setDisplayMode(dm);
			}
		} else {
			this.setSize(size);
			this.setLocationRelativeTo(null);
		}
	}

	public boolean inFullscreen() {
		return fullscreen;
	}

	public void exitFullscreen() {
		Window w = gDevice.getFullScreenWindow();
		if (w != null) {
			w.dispose();
		}
		gDevice.setFullScreenWindow(null);
	}
}