package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class GUICard extends JPanel {
	private Image img;
	private boolean selected;

	public GUICard() {
		this.setSize(50, 70);
		selected = false;
		img = null;
		setOpaque(false);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE,
				null));
		this.setForeground(UIManager.getColor("List.dropLineColor"));
		this.setBackground(Color.BLUE);
	}

	public void select() {
		selected = !selected;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null)
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					img.getWidth(null), img.getHeight(null), null);
		if (selected) {
			g.setColor(Color.YELLOW);
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
		}
	}

}
