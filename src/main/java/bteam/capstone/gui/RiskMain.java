package bteam.capstone.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class RiskMain implements ActionListener {
	
	public static void main(String[] args) {
		new RiskMain();
	}
	
	RiskWindow w;
	RiskPanel rPanel;
	public RiskMain(){
		rPanel = new RiskPanel(this);
		w= new RiskWindow(rPanel,false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()instanceof JButton){
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("Exit")){
				if(w.inFullScreen){
					w.exitFullScreen();
				}else{
					w.dispose();
				}
			}else if(b.getText().equals("Switch")){
				if(w.inFullScreen){
					w.exitFullScreen();
					w = new RiskWindow(rPanel,false);
				}else{
					w.dispose();
					w = new RiskWindow(rPanel,true);
				}
			}
		}
	}
}
