package utilities;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Helper {
	private static Scanner sc = new Scanner(System.in);
	
	public static int generateRandomNumbers(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
	
	public static int commandInput() {
		int command = 0;
		try {
			command = sc.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid input!");
		}
		sc.nextLine();
		return command;
	}
}
