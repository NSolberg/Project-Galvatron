package bteam.capstone.risk;

public class RiskCard {
	//0 wildcard 1 soldier 2 horse 3 cannon
	private int type;
	private Country depiction; 
	
	public RiskCard(int a, Country country){
		type = a;
		depiction = country;
	}
	
	public Country getCountry(){
		return depiction;
	}
	
	public int getType(){
		return type;
	}
}
