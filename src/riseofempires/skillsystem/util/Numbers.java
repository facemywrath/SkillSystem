package riseofempires.skillsystem.util;

import java.util.Random;

public class Numbers {
	
	public static int getRandom(int lower, int higher)
	{
		Random random = new Random();
		return random.nextInt(higher-lower)+lower;
	}

}
