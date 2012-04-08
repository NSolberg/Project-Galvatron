package bteam.capstone.guiTestArea;

import java.awt.Image;

import javax.swing.ImageIcon;

public class RiskGraphics {
	public Image logOnPage;
	public Image joinWar;
	public Image phaseBegin;
	public Image phaseCard;
	public Image phaseTroops;
	public Image phaseAttack;
	public Image phaseFortify;
	public Image dice;
	public Image okCheck;
	public Image cancel;
	public Image avatar;
	public Image loadtitle;
	
	
	public RiskGraphics(){
		//used in logOnPanel
		logOnPage = loadImage("Icons For Risk/riskTitleScreen.png");
		//used in serverBrowser
		joinWar = loadImage("Icons For Risk/go.png");
		//used in GamePanel
		phaseBegin = loadImage("Icons For Risk/phaseBegin.png");
		phaseCard = loadImage("Icons For Risk/card.png");
		phaseTroops = loadImage("Icons For Risk/ammo-5-icon.png");
		phaseAttack = loadImage("Icons For Risk/Peters-Sword-icon.png");
		phaseFortify = loadImage("Icons For Risk/world-icon.png");
		dice = loadImage("Icons For Risk/dice-icon.png");
		okCheck = loadImage("Icons For Risk/SMALLActions-dialog-ok-apply-icon-1.png");
		cancel = loadImage("Icons For Risk/SMALLAlarm-Error-icon-1.png");
		avatar = loadImage("Icons For Risk/army-officer-icon.png");
		loadtitle = loadImage("Icons For Risk/risk.png");
	}
	
	private Image loadImage(String location){
		Image out = new ImageIcon(location).getImage();
		return out;
	}
	
}
