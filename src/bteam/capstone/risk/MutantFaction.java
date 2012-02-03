/**
 * @author Ian Paterson
 * @author Austin Langhorn
 */

package bteam.capstone.risk;

import java.util.Scanner;

public class MutantFaction extends Faction {

	private int[] PowerList;
	private int count;
	
	public MutantFaction(String data){
		count = 4;
		PowerList = new int[6];
		/*
		 * HEY DOUCHBAGS! 
		 * CHANGE THESE LATER TO THE ACTUAL RULES
		 */
		PowerList[0] = 1;
		PowerList[1] = 2;
		PowerList[2] = 3;
		PowerList[3] = 4;
		
		//String in for loading
		Scanner scan = new Scanner(data);
		this.PlayerName=scan.next();
		this.startingTerritory=scan.next();
		this.isWinner=scan.nextBoolean();
		this.wasEliminated=scan.nextBoolean();
		PowerList[4]=scan.nextInt();
		PowerList[5]=scan.nextInt();
		
	}
	
	@Override
	public String toString() {
		String out ="";
		out += this.PlayerName + " ";
		out += this.startingTerritory + " ";
		out += this.isWinner + " ";
		out += this.wasEliminated + " ";
		out += this.count + " ";
		out += this.PowerList[0] +" ";
		out += this.PowerList[1] +" ";
		out += this.PowerList[2] +" ";
		out += this.PowerList[3] +" ";
		out += this.PowerList[4] +" ";
		out += this.PowerList[5] +" ";
		return out;
	}
	/**
	 * @return the powerList
	 */
	public int[] getPowerList() {
		return PowerList;
	}
	/**
	 * @param powerList the powerList to set
	 */
	public void setPowerList(int[] powerList) {
		PowerList = powerList;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	

}
