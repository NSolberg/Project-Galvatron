package bteam.capstone.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GuiTroop {
	Image img;
	int type;
	Random ran;
	int shiftTime;
	int x, y, num;
	int color;
	BufferedImage buffer;
	int time = -1;
	int shift = 25;
	public boolean reverse;

	public GuiTroop(Image image, int type, int num, int color) {
		this.color = color;
		img = image;
		this.type = type;
		ran = new Random();
		shiftTime = ran.nextInt(100);
		y = num * 100;
		x = type * 100;
		this.num = num;
		buffer = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_4BYTE_ABGR_PRE);
		alter();
	}

	public void alter() {
		Color c = null;
		switch (color) {
		case 6:
			c = Color.BLACK;
			break;
		case 0:
			c = Color.RED;
			break;
		case 1:
			c = Color.ORANGE;
			break;
		case 2:
			c = Color.YELLOW;
			break;
		case 3:
			c = Color.GREEN;
			break;
		case 4:
			c = Color.BLUE;
			break;
		case 5:
			c = new Color(143, 0, 255);
			break;
		}
		Graphics g = buffer.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		int col = ((255 & 0xff) << 24) + ((c.getRed() & 0x00ff) << 16)
				+ ((c.getGreen() & 0x0000ff) << 8)
				+ ((c.getBlue() & 0x000000ff));
		for (int i = 0; i < buffer.getWidth(); i++) {
			for (int j = 0; j < buffer.getHeight(); j++) {
				int oc = buffer.getRGB(i, j);
				int a = (oc & 0xff000000) >> 24;
				if (a < 0)
					buffer.setRGB(i, j, col);
			}
		}
	}

	public void drawMe(Graphics g) {
		if (!reverse)
			g.drawImage(buffer, x, y, 100 + x, 100 + y, 0, 0,
					buffer.getWidth(null), buffer.getHeight(null), null);
		else
			g.drawImage(buffer, x, y, x-100 , 100 + y, 0, 0,
					buffer.getWidth(null), buffer.getHeight(null), null);
		if (time > -1) {
			time++;
			if (time == shiftTime) {
				if(this.reverse){
					x -=shift;
				}else
					x+=shift;
			}else if(time==shiftTime+1){
				time = -1;
				if(this.reverse)
					x+=shift;
				else
					x-=shift;
			}
		}
	}

	public void play() {
		// TODO Auto-generated method stub
		time = 0;
	}
	
}
