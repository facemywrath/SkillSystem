package riseofempires.skillsystem.util;

import org.bukkit.ChatColor;

public class TextUtil {
	
	public static String createManaBar(double mana, double maxMana) {
        StringBuilder manaBar = new StringBuilder(ChatColor.RED + "[" + ChatColor.BLUE);
        int progress = (int) (mana / maxMana * 50.0D);
        for (int i = 0; i < progress; i++) {
            manaBar.append('|');
        }
        manaBar.append(ChatColor.DARK_RED);
        for (int i = 0; i < 50 - progress; i++) {
            manaBar.append('|');
        }
        manaBar.append(ChatColor.RED).append(']');
        return manaBar + " - " + ChatColor.BLUE + (int) (mana / maxMana * 100.0D) + "%";
    }

}
