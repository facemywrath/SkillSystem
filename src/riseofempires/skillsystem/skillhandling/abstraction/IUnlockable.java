package riseofempires.skillsystem.skillhandling.abstraction;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;

public interface IUnlockable {
	
	public AbstractSkill getUnlockedBy();
	public int getUnlockedLevel();
}
