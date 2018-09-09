package riseofempires.skillsystem.skillhandling.storage;

public class StatPackage {

	private float maxLevel = -1;
	private float duration = -1;
	private double damage = -1;
	private float cooldown = 2000;
	private float mana = -1;
	private float healing = -1;
	private String description;

	public StatPackage(float maxLevel, float duration, double damage, float cooldown, float mana, String description)
	{
		this.maxLevel = maxLevel;
		this.duration = duration;
		this.cooldown = cooldown;
		this.mana = mana;
		this.damage = damage;
		this.description = description;
	}
	public StatPackage(float maxLevel, float duration, double damage, float cooldown, float mana)
	{
		this.maxLevel = maxLevel;
		this.duration = duration;
		this.cooldown = cooldown;
		this.damage = damage;
		this.mana = mana;
	}
	public StatPackage(float maxLevel, float cooldown, double damage, float mana, String description)
	{
		this.maxLevel = maxLevel;
		this.cooldown = cooldown;
		this.mana = mana;
		this.damage = damage;
		this.description = description;
	}
	public StatPackage(float maxLevel, float cooldown, double damage, float mana)
	{
		this.maxLevel = maxLevel;
		this.cooldown = cooldown;
		this.damage = damage;
		this.mana = mana;
	}

	public StatPackage(float maxLevel, float duration, float cooldown, float mana, String description)
	{
		this.maxLevel = maxLevel;
		this.duration = duration;
		this.cooldown = cooldown;
		this.mana = mana;
		this.description = description;
	}
	public StatPackage(float maxLevel, float duration, float cooldown, float mana)
	{
		this.maxLevel = maxLevel;
		this.duration = duration;
		this.cooldown = cooldown;
		this.mana = mana;
	}
	public StatPackage(float maxLevel, float cooldown)
	{
		this.maxLevel = maxLevel;
		this.cooldown = cooldown;
	}
	public StatPackage(float maxLevel, float cooldown, float mana, String description)
	{
		this.maxLevel = maxLevel;
		this.cooldown = cooldown;
		this.mana = mana;
		this.description = description;
	}
	public StatPackage(float maxLevel, float cooldown, float mana)
	{
		this.maxLevel = maxLevel;
		this.cooldown = cooldown;
		this.mana = mana;
	}

	public StatPackage(float maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	public StatPackage setMaxLevel(float i)
	{
		this.maxLevel = i;
		return this;
	}
	public StatPackage setManaCost(float i)
	{
		this.mana = i;
		return this;
	}
	public StatPackage setCooldown(float i)
	{
		this.cooldown = i;
		return this;
	}
	public StatPackage setHealing(float i)
	{
		this.healing = i;
		return this;
	}
	public StatPackage setDuration(float i)
	{
		this.duration = i;
		return this;
	}
	public StatPackage setDamage(double d)
	{
		this.damage = d;
		return this;
	}
	public StatPackage setDescription(String str)
	{
		this.description = str;
		return this;
	}
	public float getMana() {
		return mana;
	}
	public float getMaxLevel() {
		return maxLevel;
	}
	public float getDuration() {
		return duration;
	}
	public double getDamage() {
		return damage;
	}
	
	public float getHealing()
	{
		return this.healing;
	}
	public float getCooldown() {
		return cooldown;
	}
	public String getDescription() {
		return description;
	}
}
