package riseofempires.skillsystem.skills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractPassive;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Serenity extends AbstractPassive {

	public Serenity(SkillSystem system) {
		super(system, "Serenity", SkillType.ABJURATION, SkillUsage.PASSIVE, new ItemStack(Material.POTION), 15, new StatPackage(0f).setCooldown(-1100f).setHealing(1).setManaCost(0).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Heal yourself every X seconds you are not in combat.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(45000).setManaCost(0).setHealing(3));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(42500).setManaCost(0).setHealing(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(40000).setManaCost(0).setHealing(5));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(36000).setManaCost(0).setHealing(6));
		this.setStats(SkillRarity.EPIC, new StatPackage(9).setCooldown(31000).setManaCost(0).setHealing(7));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(12).setCooldown(26000).setManaCost(0).setHealing(8));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(15).setCooldown(22000).setManaCost(0).setHealing(9));
	}
	
	@Override
	public boolean autoCast(Player player, SkillRarity rarity, int level)
	{
		SkillManager sm = getMain().getSkillManager();
		if(this.isOnCooldown(player))
		{
			this.sendCooldownMessage(player);
			return false;
		}
		float healing = this.getScaling().getHealing()*level + this.getHealing(rarity);
		if(player.getHealth() + healing > player.getMaxHealth())
			player.setHealth(player.getMaxHealth());
		else
			player.setHealth(player.getHealth() + healing);
		return true;
	}
	
}
