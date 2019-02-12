package utilities;

import java.util.Random;

public class Helper {
	
	public static int generateRandomNumbers(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
}
