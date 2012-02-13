/**
 * @Author Ian Paterson 
 * @Author Austin Langhorne
 */
package bteam.capstone.risk;

import java.util.Scanner;

public class StandardFaction extends Faction {
	private int[] PowerList;
	private int count;

	/**
	 * @return Gets PowerListArray
	 */
	public StandardFaction(String data) {
		count = 0;
		PowerList = new int[6];

		// String in for loading
		Scanner scan = new Scanner(data);
		this.PlayerName = scan.next();
		this.startingTerritory = scan.next();
		this.isWinner = scan.nextBoolean();
		this.wasEliminated = scan.nextBoolean();
		this.count = scan.nextInt();
		PowerList[0] = scan.nextInt();
		PowerList[1] = scan.nextInt();
		PowerList[2] = scan.nextInt();
		PowerList[3] = scan.nextInt();
		PowerList[4] = scan.nextInt();
		PowerList[5] = scan.nextInt();
	}

	// To String for saveing
	@Override
	public String toString() {
		String out = "";
		out += this.PlayerName + " ";
		out += this.startingTerritory + " ";
		out += this.isWinner + " ";
		out += this.wasEliminated + " ";
		out += this.count + " ";
		out += this.PowerList[0] + " ";
		out += this.PowerList[1] + " ";
		out += this.PowerList[2] + " ";
		out += this.PowerList[3] + " ";
		out += this.PowerList[4] + " ";
		out += this.PowerList[5] + " ";
		return out;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getPowerList() {
		return PowerList;
	}

	/**
	 * @param powerList
	 *            the powerList to set
	 */
	public void setPowerList(int[] powerList) {
		PowerList = powerList;
	}

	public void addPower(int power) {
		PowerList[count] = power;
		count++;
	}

}
