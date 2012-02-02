/*
 * @Author Ian Paterson 
 * @Author Austin Langhorne
 * @Author Nick Solberg
 */
package pcs.cnu.git.risk;
public class StandardFaction extends Faction{
private int[] PowerList;
private int count;
/**
 * @return Gets PowerListArray
 */
public StandardFaction(){
	count = 0;
	PowerList = new int[6];
}

public int getCount(){
	return count;
}

public void setCount(int count){
	this.count = count;
}

public int[] getPowerList() {
	return PowerList;
}

/**
 * @param powerList the powerList to set
 */
public void setPowerList(int[] powerList) {
	PowerList = powerList;
}

public void addPower(int power){
	PowerList[count] = power;
	count++;
}

	
}
