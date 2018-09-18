package riseofempires.skillsystem.skills;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
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
import riseofempires.skillsystem.util.Numbers;

public class Hellscape extends AbstractSkill{

	public Hellscape(SkillSystem main) {
		super(main, "Hellscape", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.FIREBALL), 20, new StatPackage(0f).setRange(0.2f).setCooldown(-65f).setDamage(0.65f).setManaCost(0.1f).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Shoot a fireball that corrupts the ground in the target area.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(8000).setRange(4f).setDamage(2).setManaCost(5));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(7000).setRange(5f).setDamage(2).setManaCost(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(6500).setRange(6).setDamage(1.5f).setManaCost(4));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(6000).setRange(7).setDamage(1.5f).setManaCost(3));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(5250).setRange(8).setDamage(1.25f).setManaCost(3));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(4500).setRange(9).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(3500).setRange(10).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(2500).setRange(11).setDamage(1).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		Snowball ball = player.launchProjectile(Snowball.class);
		ball.setVelocity(ball.getVelocity().multiply(1.1));
		ball.setFireTicks(10000000);
		float range = this.getRange(rarity) + this.getScaling().getRange()*level;
		sm.launchProjectile(ball, new ProjectileStorage(player, (float) (this.getDamage(rarity) + level*this.getScaling().getDamage()), event -> {
			for(float x = (float) (-0.3*range); x <= 0.3*range; x++)
			{
				for(float y = (float) (-0.3*range); y <= 0.3*range; y++)
				{
					for(float z = (float) (-0.3*range); z <= 0.3*range; z++)
					{
						Location loc = event.getEntity().getLocation().add(new Vector(x,y,z));
						if(loc.getBlock().getType() == Material.AIR) continue;
						if(loc.distance(event.getEntity().getLocation()) > range) continue;
						spread(event.getEntity().getLocation(), loc, event.getEntity().getLocation().subtract(loc).toVector().normalize(), range, 0);
					}
				}
			}

		}));
		return true;
	}

	public void spread(Location hitPoint, Location loc, Vector vec, float range, int i)
	{
		for(int j = 0; j < 2; j++)
		{
			float randx = Numbers.getRandom(-100, 100)/400.0f;
			float randy = Numbers.getRandom(-100, 100)/400.0f;
			float randz = Numbers.getRandom(-100, 100)/400.0f;
			Vector newVec = vec.multiply(new Vector(1+randx, 1+randy, 1+randz)).normalize().multiply(0.2);
			Location newLoc = loc.add(vec);
			if(newLoc.distance(hitPoint) < range && i < Math.pow(range, 2))
			{
				if(newLoc.getBlock().getType() != Material.AIR)
					newLoc.getBlock().setType((loc.getBlock().getType().toString().contains("WATER")?Material.LAVA:getRandomNetherMat()));
				getMain().getServer().getScheduler().runTaskLater(getMain(), () -> {spread(hitPoint, newLoc, newVec, range, i+1);spread(hitPoint, newLoc, vec, range, i+1);}, 4L);
			}
		}
	}

	public Material getRandomNetherMat()
	{
		int rand = Numbers.getRandom(0, 15);
		if(rand < 9)
			return Material.NETHERRACK;
		if(rand < 13)
			return Material.SOUL_SAND;
		if(rand < 14)
			return Material.LAVA;
		return Material.GLOWSTONE;

	}

}
