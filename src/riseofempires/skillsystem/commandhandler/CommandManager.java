package riseofempires.skillsystem.commandhandler;

import riseofempires.skillsystem.main.SkillSystem;

public class CommandManager {
	
	private SkillSystem main;
	
	private CMDBind commandBind;
	private CMDSkills commandSkills;
	private CMDCast commandCast;
	
	public CommandManager(SkillSystem main)
	{
		this.main = main;
		this.commandBind = new CMDBind(this);
		this.commandCast = new CMDCast(this);
		this.commandSkills = new CMDSkills(this);
	}
	
	public SkillSystem getMain(){return this.main;}

}
