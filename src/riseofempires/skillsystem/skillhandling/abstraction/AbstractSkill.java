package riseofempires.skillsystem.skillhandling.abstraction;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;
import riseofempires.skillsystem.util.Marker;

public abstract class AbstractSkill{
	
	private SkillSystem main;
	
	private HashMap<SkillRarity, StatPackage> stats = new HashMap<>();
	private StatPackage scaling;
	
	private String name, description; 
	private SkillType type;
	private SkillUsage usage;
	private ItemStack indicator;
	private int overallMaxLevel;
	
	private byte id;
	
	private HashMap<Player, Marker<Long>> cooldowns = new HashMap<>();

	public AbstractSkill(SkillSystem system, String name, SkillType type, SkillUsage usage, ItemStack indicator, int maxLevel, StatPackage scaling) {
	//	super(name, type, indicator, maxLevel);
		this.main = system;
		this.name = name;
		this.usage = usage;
		this.type = type;
		this.indicator = indicator;
		this.overallMaxLevel = maxLevel;
		this.scaling = scaling;
		stats.keySet().stream().forEach(sr -> this.stats.put(sr, stats.get(sr)));
	}
	
	public void setId(int id)
	{
		this.id = (byte) id;
	}
	
	public void updateCooldown(Player player, Long cooldown)
	{
		cooldowns.put(player, new Marker<Long>(cooldown));
	}
	
	public boolean isOnCooldown(Player player)
	{
		if(!cooldowns.containsKey(player)) return false;
		Marker<Long> cd = cooldowns.get(player);
		if(cd.getItem() <= cd.getMillisPassedSince()) return false;
		return true;
	}
	
	public float getRange(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getRange() > 1)
				return stats.get(rarity).getRange();
		return 1;
	}
	
	public void removeCooldown(Player player)
	{
		if(cooldownContains(player)) cooldowns.remove(player);
	}
	
	public boolean cooldownContains(Player player)
	{
		return cooldowns.containsKey(player);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public float getCooldownRemaining(Player player)
	{
		if(!cooldowns.containsKey(player)) return 0f;
		Marker<Long> cd = cooldowns.get(player);
		return (cd.getItem() - cd.getMillisPassedSince()) / 1000.0f;
	}
	
	public void sendCooldownMessage(Player player)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + this.getName() + " is on cooldown for " + getCooldownRemaining(player) + " seconds."));
	}
	
	public void sendManaMessage(Player player, SkillRarity rarity, int level)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNot enough mana to cast " + this.getName() + ". Mana Cost: " + this.getManaCost(rarity) + this.getScaling().getMana()*level));
	}
	
	public void sendCastMessage(Player player, SkillRarity rarity, int level)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9You cast " + rarity.formatName() + " " + this.getName() + " Level " + level));
	}
	
	public AbstractSkill setStats(SkillRarity rarity, StatPackage stats)
	{
		this.stats.put(rarity, stats);
		return this;
	}
	
	public SkillSystem getMain()
	{
		return this.main;
	}
	
	public StatPackage getScaling()
	{
		return this.scaling;
	}
	
	public String getDescription(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getDescription() != null)
				return stats.get(rarity).getDescription();
		return null;
	}
	
	public SkillUsage getUsage()
	{
		return this.usage;
	}
	
	public SkillType getType()
	{
		return this.type;
	}
	
	public float getManaCost(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getMana() > -1)
				return stats.get(rarity).getMana();
		return 5;
	}
	
	public float getHealing(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getHealing() > -1)
				return stats.get(rarity).getHealing();
		return 0;
	}
	
	public float getMaxLevel(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getMaxLevel() > -1)
				return stats.get(rarity).getMaxLevel();
		return 1;
	}
	
	public float getDuration(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getDuration() > -1)
				return stats.get(rarity).getDuration();
		return 0;
	}
	
	public float getCooldown(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getCooldown() > -1)
				return stats.get(rarity).getCooldown();
		return 2000;
	}
	
	public double getDamage(SkillRarity rarity)
	{
		if(stats.containsKey(rarity))
			if(stats.get(rarity).getDamage() > -1)
				return stats.get(rarity).getDamage();
		return 0;
	}

	public boolean cast(LivingEntity shooter, int level, SkillRarity rarity) {
		return false;
	}

	public boolean cast(LivingEntity shooter, LivingEntity target, int level, SkillRarity rarity) {
		return false;
	}

	public boolean cast(LivingEntity shooter, Player target, int level, SkillRarity rarity) {
		return false;
	}

}
