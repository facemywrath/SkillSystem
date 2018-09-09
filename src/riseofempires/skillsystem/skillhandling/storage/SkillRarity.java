package riseofempires.skillsystem.skillhandling.storage;

import org.apache.commons.lang3.StringUtils;

public enum SkillRarity {
	
	TRASH,COMMON,UNCOMMON,RARE,EPIC,LEGENDARY,MYTHIC,ULTIMATE;
	
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
		return StringUtils.capitalize(this.name().toLowerCase());
	}

}
