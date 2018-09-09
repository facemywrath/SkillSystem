package riseofempires.skillsystem.skills;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Firewave extends AbstractSkill{

	public Firewave(SkillSystem main) {
		super(main, "Firewave", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.FIREBALL), 20, new StatPackage(0f).setCooldown(-65f).setDamage(0.2f).setManaCost(0.1f).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Shoot a barrage of fireballs in the direction you're looking.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(8000).setDamage(2).setManaCost(5));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(7000).setDamage(2).setManaCost(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(6500).setDamage(1.5f).setManaCost(4));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(6000).setDamage(1.5f).setManaCost(3));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(5250).setDamage(1.25f).setManaCost(3));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(4500).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(3500).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(2500).setDamage(1).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		int radius = (int) (level/2.5+4);
		wave(player, level, rarity, 1, radius);
		return true;
	}

	public void wave(Player player, int level, SkillRarity rarity, int i, int radius)
	{
		for(int x = -i; x < i; x++)
		{
			for(int y = (int) (-1*(i/3.0)); y < i/3.0; y++)
			{
				for(int z = -i; z < i; z++)
				{
					Location loc = player.getLocation().add(new Vector(x,y,z));
					if(loc.distance(player.getLocation()) >= i-0.8 && loc.distance(player.getLocation()) <= i+0.8)
					{
						if(player.getLocation().subtract(loc).toVector().normalize().dot(player.getLocation().getDirection()) < -(1-(0.03*Math.pow(i, 1.4))))
						{
							loc.getWorld().spawnParticle(Particle.FLAME, loc, 7, 0.1, 0.1, 0.1, 0.01);
							getMain().getSkillManager().aoeFireDamage(player, loc, 0.5f, (float) (this.getDamage(rarity) + level*this.getScaling().getDamage()));
						}
					}
				}
			}
		}
		if(i < radius)
		{
			getMain().getServer().getScheduler().runTaskLater(getMain(), () -> {
				wave(player, level, rarity, i+1, radius);
			},1L);
		}
	}

}
