package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class GUICard extends JPanel {
	private Image img;
	private boolean selected;
	private String country;
	private int type;

	public GUICard() {
		this.setSize(50, 70);
		selected = false;
		img = null;
		country = "";
		setOpaque(false);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, null));
		this.setForeground(UIManager.getColor("List.dropLineColor"));
		this.setBackground(Color.BLUE);
	}

	public void select() {
		selected = !selected;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean val) {
		selected = val;
	}

	public int getType() {
		return type;
	}

	public void setImg(Image img, String name, int val) {
		this.img = img;
		this.country = name;
		this.type = val;
		selected = false;
	}

	public String getName() {
		return country;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null)
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					img.getWidth(null), img.getHeight(null), null);
		if (selected) {
			g.setColor(Color.YELLOW);
			for (int i = 0; i < 5; i++) {
				g.drawLine(0 + i+1, 0, 0 + i+1, this.getHeight());
				g.drawLine(this.getWidth() - i-3, 0, this.getWidth() - i-3,
						this.getHeight());
				g.drawLine(0, 0 + i+2, this.getWidth(), 0 + i+2);
				g.drawLine(0, this.getHeight() + i-7, this.getWidth(),
						this.getHeight() + i-7);
				// g.drawRect(0+i, 0+i, this.getWidth()-i*2,
				// this.getHeight()-i*2);
			}
		}
	}

}
