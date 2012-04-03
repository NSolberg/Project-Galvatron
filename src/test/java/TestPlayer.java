

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import bteam.capstone.risk.player;

public class TestPlayer {
	String data1 = "PlayerA 1 2 12 5";
	String data2 = "PlayerB 2 2 1 2";
	String data3 = "PlayerC 0 2 2 1";
	String data4 = "PlayerD 1 2 7 3";
	String data5 = "PlayerE 0 3 5 4";

	player playerA = new player(data1);
	player playerB = new player(data2);
	player playerC = new player(data3);
	player playerD = new player(data4);
	player playerE = new player(data5);

	@Test
	public void testPlayer() {
		assertEquals(playerA.toString(), data1);
		assertEquals(playerB.toString(), data2);
		assertEquals(playerC.toString(), data3);
		assertEquals(playerD.toString(), data4);
		assertEquals(playerE.toString(), data5);
	}

	@Test
	public void testPlayerCountrys() {
		ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(0, 1, 2,
				3));
		playerA.addCountry(0);
		playerA.addCountry(1);
		playerA.addCountry(2);
		playerA.addCountry(3);

		assertEquals(true, playerA.getCountrys().containsAll(temp));
	}
	
	
	@Test
	public void testGetCountryAt() {
		playerB.addCountry(0);
		playerB.removeCountrys(0);
		assertEquals(true,playerB.getCountrys().isEmpty());
	}
	
	@Test
	public void testRemoveAllCountrys(){
		playerC.addCountry(0);
		playerC.addCountry(1);
		playerC.addCountry(2);
		playerC.addCountry(3);
		playerC.removeAllCountrys();
		assertEquals(true,playerB.getCountrys().isEmpty());
	}

}
