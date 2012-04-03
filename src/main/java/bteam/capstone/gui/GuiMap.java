package bteam.capstone.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class GuiMap {
	private String filename;
	private boolean legacy;
	private Image[] images;
	private ArrayList<Image> cards;
	private int sWid, sHit;
	private double scale;
	private Point cen;
	private BufferedImage buffer, beforbuffer;
	public ArrayList<GuiCountry> countries;
	public ArrayList<String> countryNames;
	private String[] continents;
	private boolean inattack;

	private int cty1, cty2;

	public GuiMap(String Filename, boolean Legacy, int SCREENWIDTH,
			int SCREENHEIGHT) {
		filename = Filename;
		legacy = Legacy;
		sWid = SCREENWIDTH;
		sHit = SCREENHEIGHT;
		scale = 1.0;
		inattack = false;
		cty1 = -1;
		cty2 = -1;
		initImages();
		initFromFile();
		initCards();
		cen = new Point(images[0].getWidth(null) / 2,
				images[0].getHeight(null) / 2);
		beforbuffer = new BufferedImage(images[0].getWidth(null),
				images[0].getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		buffer = new BufferedImage(images[0].getWidth(null),
				images[0].getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}

	private void test() {
		countries.get(0).troopCount = 70;
		countries.get(0).troopColor = 0;
		countries.get(1).troopCount = 53;
		countries.get(1).troopColor = 1;
		//this.enterAttack(0, 1);
	}
	
	public void delum(){
		for(GuiCountry c:countries){
			c.lum = -1;
		}
	}
	
	public void normlum(){
		for(GuiCountry c:countries){
			c.lum = 0;
		}
	}
	
	public void lum(String ctry){
		int pos = this.countryNames.indexOf(ctry);
		if(pos>-1){
			this.countries.get(pos).lum = this.countries.get(pos).lum+1;
		}
	}
	
 	public void set(int troopCount, int color, String country){
		int num = countryNames.indexOf(country);
		countries.get(num).troopColor = color;
		countries.get(num).troopCount=troopCount;
	}
 	
 	public boolean canSelect(int num){
		int i = countries.get(num).lum;
 		if(i>-1)
 			return true;
 		return false;
 	}
 	
 	public void select(int num) {
		int i = countries.get(num).lum;
		if (i == 1)
			i = 0;
		else if (i == 0)
			i = 1;
		countries.get(num).lum = i;
	}
 	
 	public String getCountryName(int val){
 		return this.countryNames.get(val);
 	}

	private void initCards() {
		BufferedImage tbuffer = new BufferedImage(images[0].getWidth(null),
				images[0].getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics g = tbuffer.getGraphics();
		g.drawImage(images[0], 0, 0, null);
		g.dispose();

		cards = new ArrayList<Image>();
		int pos = 0;

		for (int i = 0; i < countries.size(); i++) {
			countries.get(i).cardvalue = pos;
			pos++;
			if (pos > 2)
				pos = 0;
			GuiCountry t = countries.get(i);
			int x = t.left;
			int y = t.top;
			int x2 = t.right;
			int y2 = t.bottom;
			BufferedImage temp = new BufferedImage(x2 - x, y2 - y,
					BufferedImage.TYPE_4BYTE_ABGR_PRE);
			g = temp.getGraphics();
			for (Point p : t.points) {
				g.setColor(new Color(tbuffer.getRGB(p.x, p.y)));
				g.fillRect(p.x - x, p.y - y, 1, 1);
			}
			g.dispose();
			BufferedImage card = new BufferedImage(300, 420,
					BufferedImage.TYPE_4BYTE_ABGR_PRE);
			g = card.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, card.getWidth(), card.getHeight());
			g.setColor(Color.BLACK);
			Font f = new Font("Times New Roman", Font.BOLD, 30);
			g.setFont(f);
			FontMetrics fm = g.getFontMetrics();
			int w = fm.stringWidth(t.name);
			g.drawString(t.name, 300 / 2 - w / 2, 25);
			g.drawImage(temp, 25, 30, 250, 205, 0, 0, temp.getWidth(),
					temp.getHeight(), null);
			g.drawImage(images[2 + pos], 25, 215, 250, 410, 0, 0,
					images[2 + pos].getWidth(null),
					images[2 + pos].getHeight(null), null);
			cards.add(card);
		}
		if (!legacy) {
			for (int i = 0; i < 2; i++) {
				BufferedImage card = new BufferedImage(300, 420,
						BufferedImage.TYPE_4BYTE_ABGR_PRE);
				g = card.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, card.getWidth(), card.getHeight());
				g.drawImage(images[2], 50, 3, 250, 138, 0, 0,
						images[2].getWidth(null), images[2].getHeight(null),
						null);
				g.drawImage(images[3], 50, 143, 250, 278, 0, 0,
						images[3].getWidth(null), images[3].getHeight(null),
						null);
				g.drawImage(images[4], 50, 283, 250, 418, 0, 0,
						images[4].getWidth(null), images[4].getHeight(null),
						null);
				g.dispose();
				cards.add(card);
			}
		}
	}

	public Image getCard(int num) {
		return cards.get(num);
	}

	private void initImages() {
		if (!legacy) {
			images = new Image[6];
			images[0] = new ImageIcon(filename + "/map.png").getImage();
			images[1] = new ImageIcon(filename + "/marker.png").getImage();
			images[2] = new ImageIcon(filename + "/soldier.png").getImage();
			images[3] = new ImageIcon(filename + "/calvery.png").getImage();
			images[4] = new ImageIcon(filename + "/tank.png").getImage();
			images[5] = new ImageIcon(filename + "/battleground.png")
					.getImage();
		}
	}

	private void initFromFile() {
		countries = new ArrayList<GuiCountry>();
		countryNames = new ArrayList<String>();
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
					gc.continent = continents[i];
					countries.add(gc);
					countryNames.add(gc.name);
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
		for (Point p : countries.get(num).points) {
			Color color = new Color(beforbuffer.getRGB(p.x, p.y));
			if (brighten)
				color = color.brighter();
			else
				color = color.darker();
			int value = ((color.getAlpha() & 0xff) << 24)
					+ ((color.getRed() & 0x00ff) << 16)
					+ ((color.getGreen() & 0x0000ff) << 8)
					+ ((color.getBlue() & 0x000000ff));
			beforbuffer.setRGB(p.x, p.y, value);
		}

	}

	public void paint(Graphics g) {
		if (!inattack) {
			updateBeforBuffer();
			updateBuffer();
			g.drawImage(buffer, 0, 0, sWid, sHit,
					(int) (buffer.getWidth() - buffer.getWidth() * scale),
					(int) (buffer.getHeight() - buffer.getHeight() * scale),
					(int) (buffer.getWidth() * scale),
					(int) (buffer.getHeight() * scale), null);
		} else {
			this.drawAttack(g);
			if(rampup <3700){
				rampup++;
			}else if(rampup==3700){
				this.buildTroops();
			}
		}
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
		int wid = images[1].getWidth(null);
		int hit = images[1].getHeight(null);
		for (int i = 0; i < countries.size(); i++) {
			int x = countries.get(i).centers[0].x - wid / 5 / 2;
			int y = countries.get(i).centers[0].y - hit / 5;
			g.drawImage(images[1], x, y, wid / 5 + x, hit / 5 + y, 0, 0, wid,
					hit, null);
			switch (countries.get(i).troopColor) {
			case 6:
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

			}
			if (countries.get(i).troopCount > 0) {
				g.fillOval(x + 7, y + 6, wid / 7 + 5, hit / 7 + 3);
				g.setColor(Color.WHITE);
				g.drawString(countries.get(i).troopCount + "", x + wid / 5 / 2
						- 22, y + hit / 5 / 2 + 8);
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
		double x = 1.0 * buffer.getWidth() / sWid * p.x - buffer.getWidth() / 2
				+ cen.x;
		double y = 1.0 * buffer.getHeight() / sHit * p.y - buffer.getHeight()
				/ 2 + cen.y;
		if (x < 0) {
			x = buffer.getWidth() + x;
		}
		if (x > buffer.getWidth()) {
			x = x - buffer.getWidth();
		}
		if (y < 0) {
			y = buffer.getHeight() + y;
		}
		if (y > buffer.getHeight()) {
			y = y - buffer.getHeight();
		}
		Point temp = new Point((int) x, (int) y);
		System.out.println(temp);
		for (int i = 0; i < countries.size(); i++) {
			if (countries.get(i).points.contains(temp)) {
				return i;
			}
		}
		return -1;
	}

	public boolean inAttack() {
		return inattack;
	}

	public void exitAttack() {
		inattack = false;
		cty1 = -1;
		cty2 = -1;
	}

	public void enterAttack(int att, int def) {
		cty1 = att;
		cty2 = def;
		rampup = 4000;
		inattack = true;
		buildTroops();
	}

	public void buildTroops() {
		GuiCountry c1 = countries.get(cty1);
		GuiCountry c2 = countries.get(cty2);
		int soldiers = c1.troopCount;
		int calvery = 0;
		int tank = 0;
		if (soldiers > 5) {
			calvery = soldiers / 5;
			soldiers = soldiers - calvery * 5;
		}
		if (calvery > 5) {
			tank = calvery / 5 / 2;
			calvery = calvery - tank * 2;
		}
		gtroops = new ArrayList<GuiTroop>();
		for (int i = 0; i < tank && i < 5; i++) {
			gtroops.add(new GuiTroop(images[4], 0, i, c1.troopColor));
		}
		for (int i = 0; i < calvery && i < 5; i++) {
			gtroops.add(new GuiTroop(images[3], 1, i, c1.troopColor));
		}
		for (int i = 0; i < soldiers && i < 5; i++) {
			gtroops.add(new GuiTroop(images[2], 2, i, c1.troopColor));
		}
		soldiers = c2.troopCount;
		calvery = 0;
		tank = 0;
		if (soldiers > 5) {
			calvery = soldiers / 5;
			soldiers = soldiers - calvery * 5;
		}
		if (calvery > 5) {
			tank = calvery / 5 / 2;
			calvery = calvery - tank * 2;
		}
		for (int i = 0; i < tank && i < 5; i++) {
			GuiTroop tr = new GuiTroop(images[4], 0, i, c2.troopColor);
			tr.reverse = true;
			tr.x = sWid;
			gtroops.add(tr);
		}
		for (int i = 0; i < calvery && i < 5; i++) {
			GuiTroop tr = new GuiTroop(images[3], 1, i, c2.troopColor);
			tr.reverse = true;
			tr.x = sWid - 100;
			gtroops.add(tr);
		}
		for (int i = 0; i < soldiers && i < 5; i++) {
			GuiTroop tr = new GuiTroop(images[2], 2, i, c2.troopColor);
			tr.reverse = true;
			tr.x = sWid - 200;
			gtroops.add(tr);
		}
	}

	int rampup = -1;
	ArrayList<GuiTroop> gtroops;

	public void drawAttack(Graphics g) {
		g.drawImage(images[5], 0, 0, null);

		for (GuiTroop gt : gtroops) {
			gt.drawMe(g);
		}
	}

	public void play() {
		for(GuiTroop gt: gtroops){
			gt.play();
		}
		rampup = 0;
	}
}
