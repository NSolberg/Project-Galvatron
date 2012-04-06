package bteam.capstone.guiTestArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GUIAvatarPanel extends JPanel {
	Image AvatarImage = new ImageIcon("Icons For Risk/army-officer-icon.png")
			.getImage();

	public GUIAvatarPanel(String Name, int c) {
		this.setLayout(null);
		this.setOpaque(false);
		this.setBorder(new TitledBorder(null, Name, TitledBorder.LEADING,
				TitledBorder.TOP, null, UIManager.getColor("infoText")));
		this.setSize(90, 100);
		JPanel TeamColorPannle = new JPanel();
		TeamColorPannle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		TeamColorPannle.setBackground(getColor(c));
		TeamColorPannle.setForeground(getColor(c));
		TeamColorPannle.setBounds(59, 60, 23, 19);
		this.add(TeamColorPannle);
	}

	private Color getColor(int i) {
		switch (i) {
		case 6:
			return Color.BLACK;
		case 0:
			return Color.RED;
		case 1:
			return Color.ORANGE;
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.BLUE;
		case 5:
			return new Color(143, 0, 255);
		}
		return Color.WHITE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(AvatarImage, 15, 15, this.getWidth() - 15,
				this.getHeight() - 10, 0, 0, AvatarImage.getWidth(null),
				AvatarImage.getHeight(null), null);
	}
}
