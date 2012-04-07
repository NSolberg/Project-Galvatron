package bteam.capstone.guiTestArea;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MapGraphics {
	public int progress;
	
	public MapGraphics(String file){
		
	}

	
	private Image loadImage(String location){
		Image out = new ImageIcon(location).getImage();
		return out;
	}
	
}
