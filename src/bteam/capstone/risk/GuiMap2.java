package bteam.capstone.risk;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class GuiMap2 {
	private String filename;
	private boolean legacy;
	private Image[] images;
	private int sWid, sHit;
	private double scale;
	private Point cen;
	private BufferedImage buffer, beforbuffer;
	private ArrayList<GuiCountry> countries;
	private String[] continents;

	public GuiMap2(String Filename, boolean Legacy, int SCREENWIDTH,
			int SCREENHEIGHT) {
		filename = Filename;
		legacy = Legacy;
		sWid = SCREENWIDTH;
		sHit = SCREENHEIGHT;
		scale = 1.0;
		initImages();
		initFromFile();
		cen = new Point(images[0].getWidth(null) / 2,
				images[0].getHeight(null) / 2);
		beforbuffer = new BufferedImage(images[0].getWidth(null),
				images[0].getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		buffer = new BufferedImage(images[0].getWidth(null),
				images[0].getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}

	private void initImages() {
		if (!legacy) {
			images = new Image[4];
			images[0] = new ImageIcon(filename + "/map.png").getImage();
			images[1] = new ImageIcon(filename + "/soldier.png").getImage();
			images[2] = new ImageIcon(filename + "/tank.png").getImage();
			images[3] = new ImageIcon(filename + "/marker.png").getImage();
		}
	}

	private void initFromFile() {
		countries = new ArrayList<GuiCountry>();
		File mapInfo = new File(filename + "/map.txt");
		try {
			Scanner scan = new Scanner(mapInfo);
			int num = scan.nextInt();
			scan.nextLine();
			if (legacy)
				num++;
			num = scan.nextInt();
			scan.nextLine();
			continents = new String[num];
			BufferedImage tbuffer = new BufferedImage(images[0].getWidth(null),
					images[0].getHeight(null),
					BufferedImage.TYPE_4BYTE_ABGR_PRE);
			Graphics g = tbuffer.getGraphics();
			g.drawImage(images[0], 0, 0, null);
			g.dispose();
			for (int i = 0; i < num; i++) {
				String s = scan.nextLine();
				continents[i] = s;
				int num2 = scan.nextInt();
				scan.nextLine();
				for (int j = 0; j < num2; j++) {
					String temp = scan.nextLine();
					GuiCountry gc = new GuiCountry(temp, tbuffer);
					countries.add(gc);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void adjScale(double amt, Point center) {
		if (amt < 0 && scale > 0.6 || amt > 0 && scale < 1.0)
			scale += amt;
		if (scale < 0.6)
			scale = 0.6;
		if (scale > 1.0)
			scale = 1.0;
	}

	public void adjCenter(int x, int y) {
		int newx, newy;
		if (cen.x + x * scale < 0)
			newx = buffer.getWidth();
		else if (cen.x + x * scale > buffer.getWidth())
			newx = 0;
		else
			newx = (int) (cen.x + x * scale);
		if (cen.y + y * scale < 0)
			newy = buffer.getHeight();
		else if (cen.y + y * scale > buffer.getHeight())
			newy = 0;
		else
			newy = (int) (cen.y + y * scale);
		cen.setLocation(newx, newy);
	}

	public void adjLum(int num, boolean brighten) {
		ArrayList<Point> pts = new ArrayList<Point>();
		for (Point c : countries.get(num).centers) {
			pts.add(c);
		}
		Point p = pts.remove(0);
		int origc = beforbuffer.getRGB(p.x, p.y);
		Color orig = new Color(origc);
		float olum = Color.RGBtoHSB(orig.getRed(), orig.getGreen(),
				orig.getBlue(), null)[2];
		do {
			int curc = beforbuffer.getRGB(p.x, p.y);
			Color cur = new Color(curc);
			float clum = Color.RGBtoHSB(cur.getRed(), cur.getGreen(),
					cur.getBlue(), null)[2];
			if (clum <= olum && clum >= olum - .23) {
				if (brighten)
					cur = cur.brighter();
				else
					cur = cur.darker();
				int newc = ((cur.getAlpha() & 0xff) << 24)
						+ ((cur.getRed() & 0x00ff) << 16)
						+ ((cur.getGreen() & 0x0000ff) << 8)
						+ ((cur.getBlue() & 0x000000ff));
				beforbuffer.setRGB(p.x, p.y, newc);
				if (p.x > 0)
					pts.add(new Point(p.x - 1, p.y));
				if (p.y > 0)
					pts.add(new Point(p.x, p.y - 1));
				if (p.x < beforbuffer.getWidth() - 1)
					pts.add(new Point(p.x + 1, p.y));
				if (p.y < beforbuffer.getHeight() - 1)
					pts.add(new Point(p.x, p.y + 1));
			}
			if (!pts.isEmpty())
				p = pts.remove(0);
		} while (!pts.isEmpty());
	}

	public void paint(Graphics g) {
		updateBeforBuffer();
		updateBuffer();
		g.drawImage(buffer, 0, 0, sWid, sHit,
				(int) (buffer.getWidth() - buffer.getWidth() * scale),
				(int) (buffer.getHeight() - buffer.getHeight() * scale),
				(int) (buffer.getWidth() * scale),
				(int) (buffer.getHeight() * scale), null);
	}

	public void select(int num) {
		int i = countries.get(num).lum;
		if (i == 1)
			i = 0;
		if (i == 0)
			i = 1;
		countries.get(num).lum = i;
	}

	private void drawSelections() {
		for (int i = 0; i < countries.size(); i++) {
			if (countries.get(i).lum == 1)
				adjLum(i, true);
			else if (countries.get(i).lum == -1)
				adjLum(i, false);
		}
	}

	private void drawMarkers(Graphics g) {
		g.setColor(Color.BLACK);
		Font f = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(f);
		int wid = images[3].getWidth(null);
		int hit = images[3].getHeight(null);
		for (int i = 0; i < countries.size(); i++) {
			int x = countries.get(i).centers[0].x - wid / 5 / 2;
			int y = countries.get(i).centers[0].y - hit / 5;
			g.drawImage(images[3], x, y, wid / 5 + x, hit / 5 + y, 0, 0, wid,
					hit, null);
			switch (countries.get(i).troopColor) {
			case -1:
				g.setColor(Color.BLACK);
				break;
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.ORANGE);
				break;
			case 2:
				g.setColor(Color.YELLOW);
				break;
			case 3:
				g.setColor(Color.GREEN);
				break;
			case 4:
				g.setColor(Color.BLUE);
				break;
			case 5:
				g.setColor(new Color(143, 0, 255));
				break;
			default:
				if (countries.get(i).troopColor >= 0) {
					g.fillOval(x + 7, y + 6, wid / 7 + 5, hit / 7 + 3);
					g.setColor(Color.WHITE);
					g.drawString(countries.get(i).troopCount + "", x + wid / 5
							/ 2 - 22, y + hit / 5 / 2 + 8);
				}
			}
		}
	}

	private void updateBeforBuffer() {
		Graphics g = beforbuffer.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, beforbuffer.getWidth(), beforbuffer.getHeight());
		g.drawImage(images[0], 0, 0, null);
		drawSelections();
		drawMarkers(g);
		g.dispose();
	}

	public void updateBuffer() {
		Graphics g = buffer.getGraphics();
		int x1, y1, x2, y2;
		int wid, hit;
		wid = (int) (buffer.getWidth() / 2);
		hit = (int) (buffer.getHeight() / 2);
		x1 = cen.x - wid;
		x2 = cen.x + wid;
		y1 = cen.y - hit;
		y2 = cen.y + hit;
		g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
				beforbuffer.getHeight(), x1, y1, x2, y2, null);
		if (x1 < 0) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), beforbuffer.getWidth() + x1, y1,
					beforbuffer.getWidth() * 2 + x1, y2, null);
		}
		if (x2 > beforbuffer.getWidth()) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x2 - beforbuffer.getWidth() * 2,
					y1, x2 - beforbuffer.getWidth(), y2, null);
		}
		if (y1 < 0) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x1, beforbuffer.getHeight() + y1,
					x2, beforbuffer.getHeight() * 2 + y1, null);
		}
		if (y2 > beforbuffer.getHeight()) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x1, y2 - beforbuffer.getHeight()
							* 2, x2, y2 - beforbuffer.getHeight(), null);
		}
		if (x1 < 0 && y1 < 0) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x2, y2, beforbuffer.getWidth()
							+ x2, beforbuffer.getHeight() + y2, null);
		}
		if (x1 < 0 && y2 > beforbuffer.getHeight()) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x2, y1 - beforbuffer.getHeight(),
					x2 + beforbuffer.getWidth(), y1, null);
		}
		if (x2 > beforbuffer.getWidth() && y1 < 0) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x1 - beforbuffer.getWidth(), y2,
					x1, y2 + beforbuffer.getHeight(), null);
		}
		if (x2 > beforbuffer.getWidth() && y2 > beforbuffer.getHeight()) {
			g.drawImage(beforbuffer, 0, 0, beforbuffer.getWidth(),
					beforbuffer.getHeight(), x1 - beforbuffer.getWidth(), y1
							- beforbuffer.getHeight(), x1, y1, null);
		}
	}

	public int onCountry(Point p) {
		double x = scale * buffer.getWidth() / sWid * p.x;
		double y = scale * buffer.getHeight() / sHit * p.y;
		Point temp = new Point((int) x, (int) y);
		for (int i = 0; i < countries.size(); i++) {
			if (countries.get(i).points.contains(temp)) {
				return i;
			}
		}
		return -1;
	}
}
