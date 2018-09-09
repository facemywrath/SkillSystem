package riseofempires.skillsystem.skillhandling.managers;

import org.bukkit.entity.Player;

public class ProjectileStorage {
	
	public Player getPlayer() {
		return player;
	}

	public Float getDamage() {
		return damage;
	}

	private Player player;
	private Float damage;
	
	public ProjectileStorage(Player player, Float damage)
	{
		this.player = player;
		this.damage = damage;
	}
	
	

}
