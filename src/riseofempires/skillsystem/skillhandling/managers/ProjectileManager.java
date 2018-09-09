package riseofempires.skillsystem.skillhandling.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileManager implements Listener {

	private SkillManager skillManager;

	private HashMap<Projectile, ProjectileStorage> skillShotProjectiles = new HashMap<>();

	public ProjectileManager(SkillManager sm)
	{
		this.skillManager = sm;
		sm.getMain().getServer().getPluginManager().registerEvents(this, sm.getMain());
	}

	public void launchProjectile(Player player, Projectile proj, float damage)
	{
		skillShotProjectiles.put(proj, new ProjectileStorage(player, damage));
	}

	@EventHandler
	public void entityDamageByProjectile(EntityDamageByEntityEvent event)
	{
		if(event.getCause() == DamageCause.PROJECTILE && event.getDamager() instanceof Projectile && skillShotProjectiles.containsKey(event.getDamager())) 
		{
			if(event.getEntity().equals(skillShotProjectiles.get(event.getDamager()).getPlayer()))
			{
				event.setCancelled(true);
				return;
			}
			float damage = skillShotProjectiles.get(event.getDamager()).getDamage();
			event.setDamage(damage);
			skillShotProjectiles.remove(event.getDamager());
		}
	}
}

