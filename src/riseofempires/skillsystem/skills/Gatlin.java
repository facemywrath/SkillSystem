package riseofempires.skillsystem.skills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.ProjectileStorage;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Gatlin extends AbstractSkill{

	public Gatlin(SkillSystem main) {
		super(main, "Gatlin", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.FIREBALL), 20, new StatPackage(0f).setCooldown(-65f).setDamage(0.65f).setManaCost(0.1f).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Shoot an arrow. Hitting an enemy reduces the cooldown.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(15000).setDamage(2).setManaCost(5));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(14500).setDamage(2).setManaCost(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(13500).setDamage(1.5f).setManaCost(4));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(12000).setDamage(1.5f).setManaCost(3));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(10000).setDamage(1.25f).setManaCost(3));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(9000).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(8000).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(5000).setDamage(1).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setVelocity(arrow.getVelocity().multiply(1.1));
		sm.launchProjectile(arrow, new ProjectileStorage(player, (float) (this.getDamage(rarity) + level*this.getScaling().getDamage()), event -> {
			if(event.getHitEntity() != null)
				this.updateCooldown(player, 1000L - ((long) level * 40));
		}));
		return true;
	}

}
