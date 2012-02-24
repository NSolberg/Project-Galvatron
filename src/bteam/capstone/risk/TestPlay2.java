package bteam.capstone.risk;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPlay2 {

	/*
	 * Test that a player with 6 minor cities acquires the redstar bonus.
	 * Only one mission card available, the check for superior case will be active.
	 */
	@Test
	public void testSuperior() {
		Play play = new Play();
		play.missionAvail.add(0);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 7; i++){
		play.world2.getCountry(i).setCityType(1);
		aPlayer.addCountry(i);
		}
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
	}
	
	/*
	 * Tests that a player who owns 4 minor cities, a major city, and the capitol
	 * receives the appropriate redstar.
	 * Only one mission card available, the check for superior case will be active.
	 */
	@Test
	public void testSuperior2() {
		Play play = new Play();
		play.missionAvail.add(0);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 5; i++){
		play.world2.getCountry(i).setCityType(1);
		aPlayer.addCountry(i);
		}
		play.world2.getCountry(7).setCityType(2);
		play.world2.getCountry(8).setCityType(3);
		aPlayer.addCountry(7);
		aPlayer.addCountry(8);
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
	}

	/*
	 * Tests that a player who owns 4 minor cities, a major city, and the capitol
	 * receives the appropriate redstar.
	 * Only one mission card available, the check for superior case will be active.
	 * Checks again after the player obtained 2 more cities.
	 */
	@Test
	public void testSuperior3() {
		Play play = new Play();
		play.missionAvail.add(0);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 5; i++){
		play.world2.getCountry(i).setCityType(1);
		aPlayer.addCountry(i);
		}
		play.drawMissionCard();
		System.out.println("current player does not meet the requirements.");
		//Checks that a player did not receive a redstar
		assertEquals(1 , aPlayer.getRedstar());
		aPlayer.addCountry(5);
		aPlayer.addCountry(6);
		play.world2.getCountry(5).setCityType(2);
		play.world2.getCountry(6).setCityType(3);
		play.drawMissionCard();
		
		//Checks that a player did receive a restar
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
	}
	
	@Test
	public void testReignOfTerror() {
		Play play = new Play();
		play.missionAvail.add(1);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 9; i++){
		aPlayer.addConquered(i);
		}
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.getConquered().clear();
	}	
	
	@Test
	public void testUrbanAssault() {
		Play play = new Play();
		play.missionAvail.add(2);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 4; i++){
		aPlayer.addConquered(i);
		aPlayer.addCountry(i);
		play.world2.getCountry(i).setCityType(1);
		}
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
		aPlayer.getConquered().clear();
	}	
	
	@Test
	public void testUnexpectedAttack() {
		Play play = new Play();
		play.missionAvail.add(4);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 9; i++){
		aPlayer.addConquered(i);
		aPlayer.addCountry(i);
		}
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
		aPlayer.getConquered().clear();
	}	
	
	@Test
	public void testImperialMight() {
		Play play = new Play();
		play.missionAvail.add(5);
		play.playerStack.add(aPlayer);
		aPlayer.setRedstar(1);
		for(int i = 0; i < 9; i++){
		aPlayer.addConquered(i);
		aPlayer.addCountry(i);
		}
		play.drawMissionCard();
		
		assertEquals(2 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
		aPlayer.getConquered().clear();
	}	
	
	@Test
	public void testDrawingMultipleMissionCardsDoesntChangeActiveOne() {
		Play play = new Play();
		play.missionAvail.add(0);
		play.missionAvail.add(1);
		play.missionAvail.add(2);
		play.missionAvail.add(3);
		play.missionAvail.add(4);
		play.missionAvail.add(5);
		
		play.drawMissionCard();
		play.drawMissionCard();
		play.drawMissionCard();
		play.drawMissionCard();
		play.drawMissionCard();
		play.drawMissionCard();
		
		
		assertEquals(0 , aPlayer.getRedstar());
		aPlayer.removeAllCountrys();
		aPlayer.getConquered().clear();
	}	
			player aPlayer = new player("AttackerBob  0 0 0 0");
			player bPlayer = new player("DefenderSue 0 0 0 0");
}
