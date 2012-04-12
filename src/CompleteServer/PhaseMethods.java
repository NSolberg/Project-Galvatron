package CompleteServer;

import java.util.Random;



public class PhaseMethods {
	
	public static int[] attack(int atk, int def) {
		int[] out = { 0, 0 };
		Random ran = new Random();
		int[] aDice = new int[atk];
		for (int i = 0; i < atk; i++) {
			aDice[i] = ran.nextInt(6);
		}
		int[] dDice = new int[def];
		for (int i = 0; i < def; i++) {
			dDice[i] = ran.nextInt(6);
		}
		aDice = sort(aDice);
		dDice = sort(dDice);
		for (int i = 0; i < def; i++) {
			if (aDice[i] > dDice[i])
				out[1]++;
			else
				out[0]++;
		}
		return out;
	}

	public static void conquer(Map world, int c1, int c2, player p1, player p2) {
		p1.addCountry(c2);
		p2.removeCountrys(c2);
		Country one = world.getCountry(c1);
		Country two = world.getCountry(c2);
		int amt = one.getTroopQuantity() - 1;
		one.setTroopQuantity(1);
		two.setTroopQuantity(amt);
		two.setOwner(p1);
	}

	public static void dealDamage(int[] dmg, Country one, Country two) {
		one.setTroopQuantity(one.getTroopQuantity() - dmg[0]);
		two.setTroopQuantity(two.getTroopQuantity() - dmg[1]);
	}

	public static int[] sort(int[] in) {
		for (int i = 0; i < in.length; i++) {
			for (int j = i + 1; j < in.length; j++) {
				if (in[j] > in[i]) {
					int temp = in[j];
					in[j] = in[i];
					in[i] = temp;
				}
			}
		}
		return in;
	}
}
