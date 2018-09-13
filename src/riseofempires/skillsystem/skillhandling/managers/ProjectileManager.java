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

	public void launchProjectile(Projectile proj, ProjectileStorage storage)
	{
		skillShotProjectiles.put(proj, storage);
	}

	@EventHandler
	public void projectileHit(ProjectileHitEvent event)
	{
		if(!skillShotProjectiles.containsKey(event.getEntity())) return;
		if(event.getHitEntity() == null) return;
		if(!(event.getHitEntity() instanceof LivingEntity)) return;
		LivingEntity ent = (LivingEntity) event.getHitEntity();
		ent.setNoDamageTicks(1);
		ent.setMaximumNoDamageTicks(1);
	}

	@EventHandler
	public void entityDamageByProjectile(EntityDamageByEntityEvent event)
	{
		if(!(event.getEntity() instanceof LivingEntity)) return;
		if(event.getCause() == DamageCause.PROJECTILE && event.getDamager() instanceof Projectile) 
		{
			if(skillShotProjectiles.containsKey(event.getDamager()))
			{
				ProjectileStorage ps = skillShotProjectiles.get(event.getDamager());
				LivingEntity ent = (LivingEntity) event.getEntity();
				ent.setNoDamageTicks(1);
				ent.setMaximumNoDamageTicks(1);
				if(event.getEntity().equals(ps.getPlayer()))
				{
					event.setCancelled(true);
					return;
				}
				float damage = ps.getDamage();
				event.setDamage(damage);
				if(ps.getPotionEffect() != null)
				{
					ent.addPotionEffect(ps.getPotionEffect());
				}
				skillShotProjectiles.remove(event.getDamager());
			}
			else
			{
				event.setCancelled(true);
			}
		}
	}
}

