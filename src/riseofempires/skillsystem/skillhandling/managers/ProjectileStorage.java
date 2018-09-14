package riseofempires.skillsystem.skillhandling.managers;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
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
	
	public boolean hasHitEffect()
	{
		return onHit != null;
	}
	
	public void trigger(ProjectileHitEvent event)
	{
		onHit.accept(event);
	}

	private Consumer<ProjectileHitEvent> onHit;
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
	
	public ProjectileStorage(Player player, Float damage, Consumer<ProjectileHitEvent> onHit)
	{
		this.onHit = onHit;
		this.player = player;
		this.damage = damage;
	}
	
	

}
