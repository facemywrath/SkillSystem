package riseofempires.skillsystem.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Swipe extends AbstractSkill {

	public Swipe(SkillSystem system) {
		super(system, "Swipe", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.APPLE), 5, new StatPackage(0f).setDamage(0.5).setRange(0.25f).setCooldown(-500).setManaCost(0).setDescription("Swipe with bear claws. Requires bear form active."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setDamage(5).setCooldown(30000).setRange(3).setManaCost(10));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setDamage(5).setCooldown(24000).setRange(3).setManaCost(8));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setDamage(5.5).setCooldown(19000).setRange(3).setManaCost(6));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setDamage(6).setCooldown(16000).setRange(3).setManaCost(4));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(8).setDamage(7).setCooldown(14000).setRange(3).setManaCost(3));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(9).setDamage(8).setCooldown(12000).setRange(3).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(10).setDamage(8).setCooldown(10000).setRange(3).setManaCost(1));
	}
	
	@Override
	public boolean cast(LivingEntity caster, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		User user = getMain().getCore().getUserManager().getUser(player);
		if(!user.isDisguised() || user.getDisguise() != EntityType.POLAR_BEAR)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Must be in bear form to cast &6Swipe"));
			return false;
		}
		float range = this.getRange(rarity) + this.getScaling().getRange()*level;
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1f, 1);
		for(Entity ent : player.getNearbyEntities(range, range, range))
		{
			if(!(ent instanceof LivingEntity))continue;
			Location loc = ent.getLocation();
			if(player.getLocation().subtract(loc).toVector().normalize().dot(player.getLocation().getDirection()) < -0.3)
			{
				ent.getWorld().spawnParticle(Particle.CLOUD, ent.getLocation().add(new Vector(0,0.5,0)), 10, 0.4, 0.4, 0.4, 0.1);
				float damage = (float) (this.getDamage(rarity) + this.getScaling().getDamage()*level);
				((LivingEntity)ent).damage(damage);
			}
		}
		return true;
	}
}
