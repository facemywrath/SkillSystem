package riseofempires.skillsystem.main;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import riseofempires.core.main.Core;
import riseofempires.skillsystem.commandhandler.CommandManager;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skills.AutoSmelt;
import riseofempires.skillsystem.skills.BearForm;
import riseofempires.skillsystem.skills.Blizzard;
import riseofempires.skillsystem.skills.CalmCreature;
import riseofempires.skillsystem.skills.CatForm;
import riseofempires.skillsystem.skills.DrillHands;
import riseofempires.skillsystem.skills.Enderscape;
import riseofempires.skillsystem.skills.Fireball;
import riseofempires.skillsystem.skills.Fireblast;
import riseofempires.skillsystem.skills.Firewave;
import riseofempires.skillsystem.skills.Gatlin;
import riseofempires.skillsystem.skills.Grappler;
import riseofempires.skillsystem.skills.Hellscape;
import riseofempires.skillsystem.skills.Mining;
import riseofempires.skillsystem.skills.Monster;
import riseofempires.skillsystem.skills.Recovery;
import riseofempires.skillsystem.skills.Rejuvinate;
import riseofempires.skillsystem.skills.Roar;
import riseofempires.skillsystem.skills.StoneEater;
import riseofempires.skillsystem.skills.Swipe;
import riseofempires.skillsystem.skills.Thunderstorm;
import riseofempires.skillsystem.skills.VolatileReflex;
import riseofempires.skillsystem.skills.WolfForm;
import riseofempires.skillsystem.skills.Xray;

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
		List<AbstractSkill> skills = Arrays.asList(new Fireball(this), new Hellscape(this), new Gatlin(this), new Grappler(this), new CatForm(this), new WolfForm(this), new Swipe(this), new Roar(this), new BearForm(this), new CalmCreature(this), new Xray(this), new Monster(this), new AutoSmelt(this), new DrillHands(this), new Enderscape(this), new Mining(this), new StoneEater(this), new VolatileReflex(this), new Blizzard(this), new Thunderstorm(this), new Firewave(this), new Fireblast(this), new Rejuvinate(this), new Recovery(this));
		for(int i = 0; i < skills.size(); i++)
		{
			skills.get(i).setId(i);
		}
		skills.stream().forEach(skill -> skillManager.registerAbstractSkill(skill));
	}

}
