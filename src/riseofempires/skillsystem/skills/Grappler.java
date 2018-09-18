package riseofempires.skillsystem.skills;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.ProjectileStorage;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Grappler extends AbstractSkill{

	public Grappler(SkillSystem main) {
		super(main, "Grappler", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.LEASH), 20, new StatPackage(0f).setCooldown(-65f).setDamage(0.25f).setManaCost(0.1f).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Launch an arrow that pulls you to it on hit.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(8000).setDamage(4).setManaCost(5));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(7000).setDamage(5).setManaCost(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(6500).setDamage(6).setManaCost(4));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(6000).setDamage(7).setManaCost(3));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(5250).setDamage(8).setManaCost(3));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(4500).setDamage(9).setManaCost(2));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(3500).setDamage(10).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(2500).setDamage(10).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		Arrow arrow = player.launchProjectile(Arrow.class);
		sm.launchProjectile(arrow, new ProjectileStorage(player, (float) (this.getDamage(rarity) + level*this.getScaling().getDamage()), event ->
		{
			Player p = (Player) arrow.getShooter();
			Location loc = p.getLocation().clone();
			p.setVelocity(new Vector(0,0.6,0));
			getMain().getServer().getScheduler().runTaskLater(getMain(), () -> {
			p.setVelocity(arrow.getLocation().subtract(loc).toVector().normalize().multiply(loc.distance(arrow.getLocation())*0.2));
			}, 5L);
		}));
		return true;
	}

}
