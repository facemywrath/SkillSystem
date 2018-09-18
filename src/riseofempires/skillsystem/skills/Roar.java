package riseofempires.skillsystem.skills;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Roar extends AbstractSkill {

	public Roar(SkillSystem system) {
		super(system, "Roar", SkillType.EVOCATION, SkillUsage.SKILLSHOT, new ItemStack(Material.APPLE), 5, new StatPackage(0f).setDuration(4).setRange(0.25f).setCooldown(-500).setManaCost(0).setDescription("Roar and frighten nearby mobs, weakening them. Requires bear form active."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setDuration(60).setCooldown(30000).setRange(3).setManaCost(10));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setDuration(65).setCooldown(24000).setRange(3).setManaCost(8));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setDuration(70).setCooldown(19000).setRange(3).setManaCost(6));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setDuration(75).setCooldown(16000).setRange(3).setManaCost(4));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(8).setDuration(80).setCooldown(14000).setRange(3).setManaCost(3));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(9).setDuration(90).setCooldown(12000).setRange(3).setManaCost(2));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(10).setDuration(100).setCooldown(10000).setRange(3).setManaCost(1));
	}

	@Override
	public boolean cast(LivingEntity caster, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		User user = getMain().getCore().getUserManager().getUser(player);
		if(!user.isDisguised() || user.getDisguise() != EntityType.POLAR_BEAR)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Must be in bear form to cast &6Roar"));
			return false;
		}
		float range = this.getRange(rarity) + this.getScaling().getRange()*level;
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1f, 1);
		for(Entity ent : player.getNearbyEntities(range, range, range))
		{
			if(!(ent instanceof LivingEntity))continue;
			Location loc = ent.getLocation();
			ent.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, ent.getLocation().add(new Vector(0,0.5,0)), 10, 0.4, 0.4, 0.4, 0.1);
			float duration = this.getDuration(rarity) + this.getScaling().getDuration()*level;
			int amplifier = (int) (level/2.5-1);
			PotionEffect effect = new PotionEffect(PotionEffectType.WEAKNESS, (int) duration, amplifier);
			((LivingEntity)ent).addPotionEffect(effect);
		}
		return true;
	}
}
