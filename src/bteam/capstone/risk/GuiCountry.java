package bteam.capstone.risk;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiCountry {
	int troopCount;
	int troopColor;
	String name;
	Point[] centers;
	ArrayList<Point> points;
	int lum;

	public GuiCountry(String data, BufferedImage img) {
		lum = 0;
		troopCount = 0;
		troopColor = -1;
		Scanner scan = new Scanner(data);
		scan.useDelimiter("/");
		name = scan.next();
		int num = scan.nextInt();
		centers = new Point[num];
		for (int i = 0; i < num; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			centers[i] = new Point(x, y);
		}
		init(img);
	}

	private void init(BufferedImage img) {
		points = new ArrayList<Point>();
		ArrayList<Point> pts = new ArrayList<Point>();
		for (Point c : centers) {
			pts.add(c);
		}
		Point p = pts.remove(0);
		int origc = img.getRGB(p.x, p.y);
		Color orig = new Color(origc);
		float olum = Color.RGBtoHSB(orig.getRed(), orig.getGreen(),
				orig.getBlue(), null)[2];
		do {
			int curc = img.getRGB(p.x, p.y);
			Color cur = new Color(curc);
			float clum = Color.RGBtoHSB(cur.getRed(), cur.getGreen(),
					cur.getBlue(), null)[2];
			if (clum <= olum && clum >= olum - .23) {
				points.add(p);
				cur = cur.brighter();
				int newc = ((cur.getAlpha() & 0xff) << 24)
						+ ((cur.getRed() & 0x00ff) << 16)
						+ ((cur.getGreen() & 0x0000ff) << 8)
						+ ((cur.getBlue() & 0x000000ff));
				img.setRGB(p.x, p.y, newc);
				if (p.x > 0)
					pts.add(new Point(p.x - 1, p.y));
				if (p.y > 0)
					pts.add(new Point(p.x, p.y - 1));
				if (p.x < img.getWidth() - 1)
					pts.add(new Point(p.x + 1, p.y));
				if (p.y < img.getHeight() - 1)
					pts.add(new Point(p.x, p.y + 1));
			}
			if (!pts.isEmpty())
				p = pts.remove(0);
		} while (!pts.isEmpty());
	}
}
