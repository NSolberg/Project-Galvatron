/**
*@Author Ian Paterson
*/
package pcs.cnu.git.risk;
public class AliensFaction extends Faction {

	private int[] PowerList;
	private int count;
	
	public AliensFaction(){
		count = 3;
		PowerList = new int[4];
		/*
		 * HEY DOUCHBAGS! 
		 * CHANGE THESE LATER TO THE ACTUAL RULES
		 */
		PowerList[0] = 1;
		PowerList[1] = 2;
		PowerList[2] = 3;
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
	
	public void addPower(int power){
		PowerList[count] = power;
		count++;
	}

}
