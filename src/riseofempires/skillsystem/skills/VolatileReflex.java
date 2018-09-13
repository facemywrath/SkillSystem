package riseofempires.skillsystem.skills;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import riseofempires.core.main.Core;
import riseofempires.core.userhandler.User;
import riseofempires.core.userhandler.UserManager;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractPassiveTrigger;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class VolatileReflex extends AbstractPassiveTrigger<EntityDamageByEntityEvent> {

	public VolatileReflex(SkillSystem system) {
		super(system, EntityDamageByEntityEvent.class, "VolatileReflex", SkillType.ENCHANTMENT, SkillUsage.TRIGGERED, new ItemStack(Material.ANVIL), 20, new StatPackage(0f).setDamage(2f).setCooldown(-100f).setManaCost(0f).setDescription("Push someone away when they hit you."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(26000).setDamage(2).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(24000).setDamage(2).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(22000).setDamage(1.5f).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(21000).setDamage(1.5f).setManaCost(0));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(20000).setDamage(1.25f).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(18000).setDamage(1).setManaCost(0));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(17000).setDamage(1).setManaCost(0));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(16000).setDamage(1).setManaCost(0));
	}
	
	@Override
	public void accept(EntityDamageByEntityEvent event)
	{
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if(!(event.getDamager() instanceof LivingEntity)) return;
		Core core = this.getMain().getCore();
		UserManager um = core.getUserManager();
		if(um.getUser(player) == null) return;
		User user = um.getUser(player);
		if(!user.hasSkill(this)) return;
		if(this.isOnCooldown(player)) return;
		LivingEntity target = (LivingEntity) event.getDamager();
		SkillRarity rarity = user.getSkillRarity(this);
		int level = user.getSkillLevel(this);
		target.setVelocity(target.getLocation().subtract(player.getLocation()).toVector().normalize().multiply(1+0.05*level));
		this.sendCastMessage(player, rarity, level);
		this.updateCooldown(player, (long) (this.getCooldown(rarity) + this.getScaling().getCooldown() * level));
	}

}
