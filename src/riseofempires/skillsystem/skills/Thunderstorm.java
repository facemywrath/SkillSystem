package riseofempires.skillsystem.skills;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
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
import riseofempires.skillsystem.util.Numbers;

public class Thunderstorm extends AbstractSkill {

	public Thunderstorm(SkillSystem main) {
		super(main, "Thunderstorm", SkillType.TRANSMUTATION, SkillUsage.SKILLSHOT, new ItemStack(Material.SNOW_BALL), 20,
				new StatPackage(0f).setCooldown(-150f).setDamage(0.1f).setManaCost(0.1f).setDuration(100).setDescription(ChatColor.translateAlternateColorCodes('&', "&9Shoot a barrage of fireballs in the direction you're looking.")));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(26000).setDuration(3000).setDamage(2).setManaCost(5));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(24000).setDuration(3000).setDamage(2).setManaCost(4));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(22000).setDuration(3000).setDamage(1.5f).setManaCost(4));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(21000).setDuration(3000).setDamage(1.5f).setManaCost(3));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(20000).setDuration(3000).setDamage(1.25f).setManaCost(3));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(18000).setDuration(3000).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(17000).setDuration(3000).setDamage(1).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(16000).setDuration(3000).setDamage(1).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		Player player = (Player) shooter;
		SkillManager sm = getMain().getSkillManager();
		int radius = 4;
		Location loc = sm.getTargetBlock(player, 25f).getLocation();
		Location botLoc = loc.clone();
		if(loc.getY() >= 255) return false;
		for(int i = 0; i < 255 - loc.getY(); i ++) {
			if(loc.clone().add(new Vector(0, 1, 0)).getBlock().getType() == Material.AIR && i < 15) {
				loc = loc.add(new Vector(0, 1, 0));
			} else {
				break;
			}
		}
		Long time = System.currentTimeMillis();
		time += (long) (this.getScaling().getDuration()*level);
		time += (long) (this.getDuration(rarity));
		thunderstorm(player, botLoc, loc, level, rarity, time, radius);
		return true;
	}

	public void thunderstorm(Player player, Location botLoc, Location topLoc, int level, SkillRarity rarity, Long time, int radius) {

		if (System.currentTimeMillis() < time) {
			double randx = Numbers.getRandom(-10 * radius, 10 * radius) / 10.0;
			double randz = Numbers.getRandom(-10 * radius, 10 * radius) / 10.0;
			Location tempLoc = topLoc.clone().add(new Vector(randx,0,randz));
			tempLoc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, tempLoc, 3, 0.1, 0.1, 0.1, 0f);
			tempLoc.getWorld().spawnParticle(Particle.DRIP_WATER, tempLoc, 3, 0.1, 0.1, 0.1, 0f);
			if(System.currentTimeMillis()%100 > 80)
			{
				List<Entity> nearby = (List<Entity>) botLoc.getWorld().getNearbyEntities(botLoc, radius, 2, radius);
				if(nearby.size() == 0) return;
				int rand = Numbers.getRandom(0, nearby.size());
				int i = 0;
				for(Entity ent : botLoc.getWorld().getNearbyEntities(botLoc, radius, 2, radius))
				{
					if(rand != i)
					{
						i++;
						continue;
					}
					if(!(ent instanceof LivingEntity) || ent.equals(player)) continue;
					LivingEntity lent = (LivingEntity) ent;
					botLoc.getWorld().strikeLightningEffect(lent.getLocation());
					lent.damage(this.getDamage(rarity) + level*this.getScaling().getDamage());
					break;
				}
			}
			SkillManager sm = getMain().getSkillManager();
			getMain().getServer().getScheduler().runTaskLater(getMain(), () -> { thunderstorm(player, botLoc, topLoc, level, rarity, time, radius);
			}, 1L);
		}
	}
}