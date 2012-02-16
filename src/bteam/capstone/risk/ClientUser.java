package bteam.capstone.risk;

import java.util.Scanner;

public class ClientUser {
	public static void main(String[] args){
		new ClientUser();
	}
	
	public ClientUser(){
		RiskClient c = new RiskClient(this);
		Scanner scan = new Scanner(System.in);
		c.connect("localhost", 1337);
		c.start();
		while(true){
			String data = scan.nextLine();
			c.sendData(data);
		}
	}

	public void displayData(String string) {
		System.out.println(string);
		
	}

}
