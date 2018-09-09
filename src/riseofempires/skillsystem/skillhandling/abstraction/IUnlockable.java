package riseofempires.skillsystem.skillhandling.abstraction;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;

public abstract class IUnlockable {

	private String unlockedBySkillName;
	private AbstractSkill unlockedBySkill;
	private int unlockedAtLevel;
	
	public AbstractSkill getUnlockedBy()
	{
		if(unlockedBySkill == null) unlockedBySkill = SkillManager.getAbstractSkillByName(unlockedBySkillName);
		if(unlockedBySkill == null) return null;
		return unlockedBySkill;
	}
	public int getUnlockedLevel()
	{
		return 100;
	}
	
	public void setUnlockedBySkill(String skillname)
	{
		unlockedBySkillName = skillname;
		unlockedBySkill = SkillSystem.getMain().getSkillManager().getSkillByName(skillname);
	}
	
	public void setUnlockedAtSkillLevel(int level)
	{
		unlockedAtLevel = level;
	}

}
