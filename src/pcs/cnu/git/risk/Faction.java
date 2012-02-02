/*
 * @Author Ian Paterson
 * Faction class
 * 
 */
package pcs.cnu.git.risk;
public class Faction {
	private String PlayerName;
	private String startingTerritory;
	private Boolean isWinner;
	private Boolean wasEliminated;
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
