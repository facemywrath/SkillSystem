package riseofempires.skillsystem.skillhandling.managers;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import net.md_5.bungee.api.ChatColor;
import riseofempires.core.main.Core;
import riseofempires.core.userhandler.SkillStorage;
import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;

public class SkillManager {

	private SkillSystem main;
	private ProjectileManager projectileManager;
	private HashMap<String, AbstractSkill> skills = new HashMap<>();

	public SkillManager(SkillSystem main) 
	{
		this.main = main; 
		this.projectileManager = new ProjectileManager(this);
	}

	public void registerAbstractSkill(AbstractSkill skill)
	{
		if(!skills.containsKey(skill.getName()))
		{
			skills.put(skill.getName(), skill);
			System.out.println("Skill " + skill.getName() + " registered successfully.");
		}
		else
			System.out.println("ERROR: Skill " + skill.getName() + " attempted registry twice.");
	}

	public void launchProjectile(Player player, Projectile proj, float damage)
	{
		this.projectileManager.launchProjectile(player, proj, damage);
	}

	public void attemptCast(Player player, SkillStorage ss)
	{
		AbstractSkill skill = ss.getSkill();
		int level = ss.getLevel();
		SkillRarity rarity = ss.getRarity();
		Core core = getMain().getCore();
		if(core.getUserManager().getUser(player) == null)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Your data didn't load correctly. Rejoin. Sorry!"));
			return;
		}
		User user = core.getUserManager().getUser(player);
		if(!user.hasSkill(skill))
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You dont have Skill " + skill.getName()));
			return;
		}
		if(skill.isOnCooldown(player))
		{
			skill.sendCooldownMessage(player);
			return;
		}
		if(skill.getManaCost(rarity) + level*skill.getScaling().getMana() > user.getCurrentMana())
		{
			skill.sendManaMessage(player, rarity, level);
			return;
		}
		if(skill.getUsage() == SkillUsage.SELF || skill.getUsage() == SkillUsage.SKILLSHOT)
		{
			if(skill.cast(player, level, rarity))
			{
				skill.updateCooldown(player, (long) (skill.getCooldown(rarity) + skill.getScaling().getCooldown() * level));
				skill.sendCastMessage(player, rarity, level);
			}
		}
	}

	public void aoeDamage(Player player, Location loc, float radius, float damage)
	{
		for(Entity ent : loc.getWorld().getNearbyEntities(loc, radius, radius, radius))
		{
			if(!(ent instanceof LivingEntity))continue;
			if(ent.equals(player)) continue;
			LivingEntity living = (LivingEntity) ent;
			living.damage(damage);
		}
	}


	public void aoeFireDamage(Player player, Location loc, float radius, float damage)
	{
		for(Entity ent : loc.getWorld().getNearbyEntities(loc, radius, radius, radius))
		{
			if(!(ent instanceof LivingEntity))continue;
			if(ent.equals(player)) continue;
			LivingEntity living = (LivingEntity) ent;
			ent.setFireTicks(500);
			living.damage(damage);
		}
	}

	public SkillSystem getMain()
	{
		return this.main;
	}

	public AbstractSkill getSkillByName(String str)
	{
		for(String skill : skills.keySet())
		{
			if(skill.equalsIgnoreCase(str)) return skills.get(skill);
		}
		return null;
	}

	public static AbstractSkill getAbstractSkillByName(String str)
	{
		AbstractSkill skill = SkillSystem.getMain().getSkillManager().getSkillByName(str);
		if(skill != null)
			return skill;
		return null;
	}


}
