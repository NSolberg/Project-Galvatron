/**
 * @Author Ian Paterson
 * Faction class
 * 
 */
package bteam.capstone.faction;

public class Faction {
	protected String PlayerName;
	protected String startingTerritory;
	protected Boolean isWinner;
	protected Boolean wasEliminated;

	
/**
 * @author Ian Paterson
 * @param PlayerName: The player playing the faction last game
 * @param startingTerritory: The territory this faction starts in
 * @param isWinner: Weather or not this faction won last game
 * @param wasEliminated: Weather or not the faction was destroyed
 * last game
 * 
 * @description THis class holds all the basic data that ALL 
 * factions have. The 3 other types inherit this class in order
 * to keep the diffrent types organized. 
 */
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.PlayerName = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return PlayerName;
	}

	/**
	 * @param startingTerritory
	 *            the startingTerritory to set
	 */
	public void setStartingTerritory(String startingTerritory) {
		this.startingTerritory = startingTerritory;
	}

	/**
	 * @return the startingTerritory
	 */
	public String getStartingTerritory() {
		return startingTerritory;
	}

	/**
	 * @param isWinner
	 *            the isWinner to set
	 */
	public void setIsWinner(Boolean isWinner) {
		this.isWinner = isWinner;
	}

	/**
	 * @return the isWinner
	 */
	public Boolean getIsWinner() {
		return isWinner;
	}

	/**
	 * @param wasEliminated
	 *            the wasEliminated to set
	 */
	public void setWasEliminated(Boolean wasEliminated) {
		this.wasEliminated = wasEliminated;
	}

	/**
	 * @return the wasEliminated
	 */
	public Boolean getWasEliminated() {
		return wasEliminated;
	}

}
