package riseofempires.skillsystem.main;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import riseofempires.core.main.Core;
import riseofempires.skillsystem.commandhandler.CommandManager;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skills.Fireball;
import riseofempires.skillsystem.skills.Fireblast;
import riseofempires.skillsystem.skills.Firewave;
import riseofempires.skillsystem.skills.Recovery;
import riseofempires.skillsystem.skills.Rejuvinate;

public class SkillSystem extends JavaPlugin {
	
	private SkillManager skillManager;
	private CommandManager commandManager;
	
	private Core core;
	
	public void onEnable()
	{
		commandManager = new CommandManager(this);
		skillManager = new SkillManager(this);
		registerSkills();
		if(Bukkit.getPluginManager().getPlugin("RoeCore") == null)
		{
			System.out.println("ERROR: CORE NOT FOUND, DISABLING SKILL SYSTEM!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		core = (Core) Bukkit.getPluginManager().getPlugin("RoeCore");
		core.loadSkillSystem(this);
	}
	
	public Core getCore(){return this.core;}
	
	public SkillManager getSkillManager() { return this.skillManager; }
	
	public static SkillSystem getMain() { return SkillSystem.getPlugin(SkillSystem.class); }
	
	public static void registerAbstractSkill(AbstractSkill skill)
	{
		SkillSystem.getMain().getSkillManager().registerAbstractSkill(skill);
	}

	public void registerSkills() {
		List<AbstractSkill> skills = Arrays.asList(new Fireball(this), new Firewave(this), new Fireblast(this), new Rejuvinate(this), new Recovery(this));
		skills.stream().forEach(skill -> skillManager.registerAbstractSkill(skill));
	}

}
