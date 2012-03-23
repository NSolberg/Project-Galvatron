package bteam.capstone.gui;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import javax.swing.JFrame;


public class RiskWindow extends JFrame {
	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice gDevice = env.getDefaultScreenDevice();
	RiskPanel rPanel;
	boolean inFullScreen;
	int w = 800;
	int h = 600;
	public RiskWindow(RiskPanel riskPanel, boolean FullScreenMode) {
		rPanel = riskPanel;
		this.inFullScreen = FullScreenMode;
		if(FullScreenMode){
			FullScreen();
		}else{
			windowed();
		}
	}
	
	public void FullScreen(){
		this.add(rPanel);
		DisplayMode dm = new DisplayMode(w,h,32,DisplayMode.REFRESH_RATE_UNKNOWN);
		this.setBackground(Color.BLUE);
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Arial",Font.PLAIN,24));
		this.setUndecorated(true);
		this.setResizable(false);
		gDevice.setFullScreenWindow(this);
		if(dm!=null && gDevice.isDisplayChangeSupported()){
			gDevice.setDisplayMode(dm);
		}
	}
	
	public Window getFullScreenWindow(){
		return gDevice.getFullScreenWindow();
	}
	
	public void exitFullScreen(){
		Window w = gDevice.getFullScreenWindow();
		if(w!=null){
			w.dispose();
		}
		gDevice.setFullScreenWindow(null);
	}
	
	public void windowed(){
		this.setSize(w,h);
		this.add(rPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
