/**
 * @Author Ian Paterson
 * Faction class
 * 
 */
package bteam.capstone.risk;
public class Faction {
	protected String PlayerName;
	protected String startingTerritory;
	protected Boolean isWinner;
	protected Boolean wasEliminated;
	/**
	 * @param name the name to set
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
	 * @param startingTerritory the startingTerritory to set
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
	 * @param isWinner the isWinner to set
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
	 * @param wasEliminated the wasEliminated to set
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
