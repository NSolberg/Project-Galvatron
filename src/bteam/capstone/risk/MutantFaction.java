/**
 * @author Ian Paterson
 * @author Austin Langhorn
 */

package bteam.capstone.risk;
public class MutantFaction extends Faction {

	private int[] PowerList;
	private int count;
	
	public MutantFaction(){
		count = 4;
		PowerList = new int[6];
		/*
		 * HEY DOUCHBAGS! 
		 * CHANGE THESE LATER TO THE ACTUAL RULES
		 */
		PowerList[0] = 1;
		PowerList[1] = 2;
		PowerList[2] = 3;
		PowerList[4] = 4;
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
