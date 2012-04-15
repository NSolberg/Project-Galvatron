package CompleteClient;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiCountry {
	public int troopCount;
	public int troopColor;
	public String name;
	public Point[] centers;
	public ArrayList<Point> points;
	public int left, top, bottom, right;
	public int lum;
	public int cardvalue;
	public String continent;

	public GuiCountry(String data, BufferedImage img) {
		cardvalue = -1;
		left = -1;
		top = -1;
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

	private void newCorners(Point p) {
		if (left == -1) {
			left = p.x;
			top = p.y;
			right = p.x;
			bottom = p.y;
		}
		if (p.x < left)
			left = p.x;
		if (p.y < top)
			top = p.y;
		if (p.x > right)
			right = p.x;
		if (p.y > bottom)
			bottom = p.y;
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
				newCorners(p);
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
