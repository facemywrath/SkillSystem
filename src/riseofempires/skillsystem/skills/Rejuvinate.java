package riseofempires.skillsystem.skills;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Rejuvinate extends AbstractSkill {

	
	public Rejuvinate(SkillSystem main) {
		super(main, "Rejuvinate", SkillType.ABJURATION, SkillUsage.TARGET, new ItemStack(Material.APPLE), 10, new StatPackage(0f).setHealing(1).setCooldown(-200f).setManaCost(0.25f).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Heal a player for X amount.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1, 15000).setHealing(2).setManaCost(10));
		this.setStats(SkillRarity.COMMON, new StatPackage(3, 13500).setHealing(2.5f).setManaCost(8));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5, 12000f).setHealing(3).setManaCost(7));
		this.setStats(SkillRarity.RARE, new StatPackage(7, 10000).setHealing(3).setManaCost(6));
		this.setStats(SkillRarity.EPIC, new StatPackage(10, 8000).setHealing(3).setManaCost(5));
	}
	
	@Override
	public boolean cast(LivingEntity shooter, Player target, int level, SkillRarity rarity)
	{
		if(!(shooter instanceof Player))
			return false;
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		if(this.isOnCooldown(player))
		{
			this.sendCooldownMessage(player);
			return false;
		}
		float healing = this.getScaling().getHealing() + this.getHealing(rarity);
		if(target.getMaxHealth() - target.getHealth() >= healing)
		{
			target.setHealth(target.getHealth() + healing);
		}
		this.updateCooldown(player, (long) (this.getCooldown(rarity) + this.getScaling().getCooldown() * level));
		return true;
	}

}
