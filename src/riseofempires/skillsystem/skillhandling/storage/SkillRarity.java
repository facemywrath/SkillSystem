package riseofempires.skillsystem.skillhandling.storage;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;

public enum SkillRarity {
	
	TRASH(ChatColor.GRAY),COMMON(ChatColor.WHITE),UNCOMMON(ChatColor.GREEN),RARE(ChatColor.DARK_AQUA),EPIC(ChatColor.DARK_PURPLE),LEGENDARY(ChatColor.GOLD),MYTHIC(ChatColor.RED),ULTIMATE(ChatColor.DARK_RED);
	
	private ChatColor color;
	SkillRarity(ChatColor color)
	{
	this.color = color;	
	}
	
	public static SkillRarity getRarity(String string)
	{
		for(SkillRarity rarity : SkillRarity.values())
		{
			if(rarity.toString().equalsIgnoreCase(string))
				return rarity;
		}
		return null;
	}

	public String formatName() {
		return color + StringUtils.capitalize(this.name().toLowerCase());
	}

}
