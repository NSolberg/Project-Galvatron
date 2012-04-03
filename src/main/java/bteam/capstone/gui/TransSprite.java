package bteam.capstone.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TransSprite extends Sprite {

	protected boolean dim;
	protected Color transColor;
	protected int transLevel;
	protected BufferedImage buffer;

	public TransSprite(Image image) {
		super(image);
		dim = false;
		transColor = new Color(0);
		transLevel = 0;
		buffer = new BufferedImage((int) this.size.getX(),
				(int) this.size.getY(), BufferedImage.TYPE_4BYTE_ABGR);
	}

	public void setTransparentColor(Color color) {
		transColor = color;
	}

	public void setTransparentLevel(int num) {
		if (num < 0)
			transLevel = 0;
		else if (num > 255)
			transLevel = 255;
		else
			transLevel = num;
	}

	public void flip() {
		dim = !dim;
	}

	private int dimColor(int color) {
		int alpha = (color & 0xff000000) >> 24;
		int red = (color & 0x00ff0000) >> 16;
		int green = (color & 0x0000ff00) >> 8;
		int blue = color & 0x000000ff;
		Color c = null;
		try {
			c = new Color(red, green, blue);
		} catch (Exception e) {
			System.out.println(alpha);
		}
		c = c.darker();
		return ((alpha & 0xff) << 24) + ((c.getRed() & 0x00ff) << 16)
				+ ((c.getGreen() & 0x0000ff) << 8)
				+ ((c.getBlue() & 0x000000ff));
	}

	public BufferedImage dimImage(BufferedImage b) {
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				int color = dimColor(b.getRGB(i, j));
				b.setRGB(i, j, color);
			}
		}
		return b;
	}

	public BufferedImage addTransparency(BufferedImage b) {
		int trans = ((transColor.getAlpha() & 0xff) << 24)
				+ ((transColor.getRed() & 0x00ff) << 16)
				+ ((transColor.getGreen() & 0x0000ff) << 8)
				+ ((transColor.getBlue() & 0x000000ff));
		int num = ((transLevel & 0xff) << 24)
				+ ((transColor.getRed() & 0x00ff) << 16)
				+ ((transColor.getGreen() & 0x0000ff) << 8)
				+ ((transColor.getBlue() & 0x000000ff));
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if (b.getRGB(i, j) == trans) {
					b.setRGB(i, j, num);
				}
			}
		}
		return b;
	}

	@Override
	public void draw(Graphics g) {
		buffer = new BufferedImage(size.x, size.y,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D bg = (Graphics2D) buffer.getGraphics();
		bg.drawImage(img, 0, 0, null);
		buffer = addTransparency(buffer);
		if (dim)
			buffer = dimImage(buffer);
		g.drawImage(buffer, local.x, local.y, local.x + size.x, local.y
				+ size.y, 0, 0, size.x, size.y, null);
	}
}
