package bteam.capstone.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class RiskMap {
	Image Orig;
	Image Cont;
	BufferedImage buffer, contBuffer;

	public RiskMap() {
		Orig = new ImageIcon("C:/Users/Austin/Desktop/riskMap.png").getImage();
		Cont = new ImageIcon("C:/Users/Austin/Desktop/riskMapContrast.png")
				.getImage();
		createBuffer();
	}

	public void createBuffer() {
		buffer = new BufferedImage(Orig.getWidth(null), Orig.getHeight(null),
				BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics temp = buffer.getGraphics();
		temp.drawImage(Orig, 0, 0, null);
		contBuffer = new BufferedImage(Cont.getWidth(null),
				Cont.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		temp = contBuffer.getGraphics();
		temp.drawImage(Cont, 0, 0, null);
	}

	public void upDateBuffer() {

	}

	public void select(int x, int y) {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(x, y));
		Color origin = new Color(buffer.getRGB(x, y));
		float[] ori = Color.RGBtoHSB(origin.getRed(), origin.getGreen(),
				origin.getBlue(), null);
		while (!points.isEmpty()) {
			Point p = points.remove(0);
			Color cur = new Color(buffer.getRGB(p.x, p.y));
			Color alt = new Color(contBuffer.getRGB(p.x, p.y));
			float[] c = Color.RGBtoHSB(cur.getRed(), cur.getGreen(),
					cur.getBlue(), null);
			if (!cur.equals(alt) && c[2] > 0.5) {
				buffer.setRGB(p.x, p.y, contBuffer.getRGB(p.x, p.y));
				if (p.x > 0)
					points.add(new Point(p.x - 1, p.y));
				if (p.x < buffer.getWidth() - 1)
					points.add(new Point(p.x + 1, p.y));
				if (p.y > 0)
					points.add(new Point(p.x, p.y - 1));
				if (p.y < buffer.getHeight() - 1)
					points.add(new Point(p.x, p.y + 1));
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(buffer, 0, 0, null);
	}

}
