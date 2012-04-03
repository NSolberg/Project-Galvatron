package bteam.capstone.gui;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Sprite {
	protected Point local;
	protected Point size;
	protected Image img;
	protected double scale;

	public Sprite(Image image) {
		img = image;
		local = new Point(0, 0);
		size = new Point(img.getWidth(null), img.getHeight(null));
		scale = 1;
	}

	public void draw(Graphics g) {
		g.drawImage(img, local.x, local.y, local.x + (int) (size.x * scale),
				(int) (local.y + size.y * scale), 0, 0, size.x, size.y, null);
	}

	public boolean onSprite(Point p) {
		if (p.x > local.x && p.x < local.x + size.x && p.y > local.y
				&& p.y < local.y + size.y)
			return true;
		return false;
	}

	public Point getLocal() {
		return local;
	}

	public void setLocal(Point local) {
		this.local = local;
	}

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

}
