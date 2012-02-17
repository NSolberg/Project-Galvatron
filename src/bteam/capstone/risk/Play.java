/** 
 * @author Nick Solberg
 */

package bteam.capstone.risk;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Play {
	private static boolean isAttacking;

	
	public static void firstTurnSetup(){
		/**
		 * @todo
		 */
	}
	/**
	 * @author Nick Solberg
	 * @param atkCountry represents the attackers country
	 * @param defCountry represents the defenders country
	 * 
	 * This method will resolve an attack phase between two players
	 * Given the attacking country and defending country the method
	 * will retrieve the number of troops each side has.
	 * The attacking player will be allowed to choose how many dice 
	 * will be rolled in their phase, given the attacker appropriate 
	 * has available resources. The attacker is also given the option
	 * to cancel the attack after any round. The loop will stop when the
	 * attacking player has 1 troop or the denfeder has 0
	 * 
	 * TODO Still need to implement the uses of missles and scars.
	 * also need to apply conquering a country and how it effects a player.
	 * Later modify this method to work with a gui and client.
	 */
	public static void attack(Country atkCountry, Country defCountry) {
		isAttacking = true;
		if (atkCountry.getCountryBorders().contains(defCountry.id()) == false) {
			System.out.println("This will be an invalid attack");
			isAttacking = false;
		}
		// else if (isAttacking == true) {
		while (isAttacking == true && defCountry.getTroopQuantity() > 0
				&& atkCountry.getTroopQuantity() > 1) {
			System.out.println("Attack continues, valid arguments");
			int switchVal = 0;
			if (atkCountry.getTroopQuantity() >= 4) {

				switchVal += 10;
			} else if (atkCountry.getTroopQuantity() == 3) {

				switchVal += 5;
			} else if (atkCountry.getTroopQuantity() == 2) {

				switchVal += 2;
			}
			if (atkCountry.getTroopQuantity() == 1) {
				System.out
						.println("Not enough troops to attack, must be greater than one");
				break;
			}

			if (defCountry.getTroopQuantity() >= 2) {
				switchVal += 2;
			} else if (defCountry.hasHQ() == true) {
				switchVal += 2;
			}

			System.out.println(switchVal);
			System.out.println("Current attackers troops: "
					+ atkCountry.getTroopQuantity());
			System.out.println("Current defenders troops: "
					+ defCountry.getTroopQuantity());
			
			
			Scanner defInScanner = new Scanner(System.in);
			defInScanner.reset();
			String temp;
			if (switchVal == 5) {
				System.out.println("Attacker would you like to roll one or two dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two")) {
					switchVal = 5;
					System.out.println("2v1");
				}
			}

			if (switchVal == 7) {
				System.out.println("Attacker would you like to roll one or two dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 4;
					System.out.println("1v2");
				} else if (temp.equals("two")) {
					switchVal = 7;
					System.out.println("2v2");
				}
			}

			if (switchVal == 10) {
				System.out.println("Attacker would you like to roll one, two, or three dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two")) {
					switchVal = 5;
					System.out.println("2v1");
				} else if (temp.equals("three")) {
					switchVal = 10;
					System.out.println("3v1");
				}
			}

			if (switchVal == 12) {
				System.out.println("Attacker would you like to roll one, two, or three dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 4;
					System.out.println("1v2");
				}
				else if (temp.equals("two")) {
					switchVal = 7;
					System.out.println("2v2");
				}
				else if (temp.equals("three")) {
					switchVal = 12;
					System.out.println("3v2");
				}
			}

		
			switch (switchVal) {
			case 12:
				System.out.println("3v2");
				int[] attack6 = { randomDice(), randomDice(), randomDice() };
				Arrays.sort(attack6);
				int[] defense6 = { randomDice(), randomDice() };
				Arrays.sort(defense6);
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 3; i++) {
					System.out.print(attack6[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(defense6[i] + " ");
				}
				System.out.println();

				if (attack6[2] > defense6[1]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack6[2] + " is greater than "
							+ defense6[1]);
				}
				if (attack6[2] <= defense6[1]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack6[2] + " is less than or equal "
							+ defense6[1]);
				}
				if (attack6[1] > defense6[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack6[1] + " is greater than "
							+ defense6[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack6[1] + " is less than or equal "
							+ defense6[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			case 10:
				System.out.println("3v1");
				int[] attack5 = { randomDice(), randomDice(), randomDice() };
				Arrays.sort(attack5);
				int[] defense5 = { randomDice() };
				Arrays.sort(defense5);
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 3; i++) {
					System.out.print(attack5[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				System.out.print(defense5[0] + " ");
				System.out.println();
				if (attack5[2] > defense5[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack5[2] + " is greater than "
							+ defense5[0]);
				}
				if (attack5[2] <= defense5[0]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack5[2] + " is less than or equal "
							+ defense5[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 7:
				System.out.println("2v2");
				int[] attack4 = { randomDice(), randomDice() };
				Arrays.sort(attack4);
				int[] defense4 = { randomDice(), randomDice() };
				Arrays.sort(defense4);
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(attack4[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(defense4[i] + " ");
				}
				System.out.println();

				if (attack4[1] > defense4[1]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack4[1] + " is greater than "
							+ defense4[1]);
				}
				if (attack4[1] <= defense4[1]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack4[1] + " is less than or equal "
							+ defense4[1]);
				}
				if (attack4[0] > defense4[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack4[0] + " is greater than "
							+ defense4[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack4[0] + " is less than or equal "
							+ defense4[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 5:
				System.out.println("2v1");
				int[] attack3 = { randomDice(), randomDice() };
				Arrays.sort(attack3);
				int[] defense3 = { randomDice() };
				Arrays.sort(defense3);
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(attack3[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");

				System.out.print(defense3[0] + " ");

				System.out.println();

				if (attack3[1] > defense3[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack3[1] + " is greater than "
							+ defense3[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack3[1] + " is less than or equal "
							+ defense3[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 2:
				// 1vs1
				System.out.println("1v1");
				int[] attack2 = { randomDice(), randomDice() };
				Arrays.sort(attack2);
				int[] defense2 = { randomDice() };
				Arrays.sort(defense2);
				System.out.println();
				System.out.print("attack die from lowest to highest ");

				System.out.print(attack2[0] + " ");
				System.out.println();
				System.out.print("defense die from lowest to highest ");

				System.out.print(defense2[0] + " ");

				System.out.println();

				if (attack2[0] > defense2[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack2[0] + " is greater than "
							+ defense2[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack2[0] + " is less than or equal "
							+ defense2[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			case 4:
				// 1vs2
				System.out.println("1v2");
				int[] attack1 = { randomDice()};
				Arrays.sort(attack1);
				int[] defense1 = { randomDice(), randomDice()};
				Arrays.sort(defense1);
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				System.out.print(attack1[0] + " ");
				System.out.println();
				System.out.print("defense die from lowest to highest ");

				for (int i = 0; i < 2; i++) {
					System.out.print(defense1[i] + " ");
				}
				System.out.println();

				if (attack1[0] > defense1[1]) {
					defCountry.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack1[0] + " is greater than "
							+ defense1[1]);
				} else {
					atkCountry.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack1[0] + " is less than or equal "
							+ defense1[1]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			}

			// ignore for now
			Scanner in = new Scanner(System.in);
			// isAttacking = false;
			if (atkCountry.getTroopQuantity() > 1
					&& defCountry.getTroopQuantity() > 0) {
				System.out
						.println("Attacker! Do you wish to continue? type 'yes' to Continue");
				String temp2 = in.next();
				// in.close();
				if (temp2.equals("yes")) {
					isAttacking = true;
				} else {
					isAttacking = false;
					if (atkCountry.getTroopQuantity() == 1) {
						System.out.println("Not enough troops");
					} else {
						System.out.println("Combat stopped");

					}
					break;
				}

				// }
				// System.out.println("Victory, " + defCountry.getCountryName()
				// +" is now yours");
			}

		}

	}

	public static int randomDice() {
		int dice;
		Random generator = new Random();
		dice = generator.nextInt(6) + 1;
		return dice;
	}
}
