package riseofempires.skillsystem.skillhandling.managers;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ProjectileStorage {
	
	public Player getPlayer() {
		return player;
	}

	public Float getDamage() {
		return damage;
	}
	
	public PotionEffect getPotionEffect()
	{
		return effect;
	}

	private Player player;
	private Float damage;
	private PotionEffect effect;
	
	public ProjectileStorage(Player player, Float damage)
	{
		this.player = player;
		this.damage = damage;
	}
	
	public ProjectileStorage(Player player, Float damage, PotionEffect effect)
	{
		this.player = player;
		this.damage = damage;
		this.effect = effect;
	}
	
	

}
