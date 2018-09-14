package riseofempires.skillsystem.skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import riseofempires.core.main.Core;
import riseofempires.core.userhandler.User;
import riseofempires.core.userhandler.UserManager;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractPassiveTrigger;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;
import riseofempires.skillsystem.util.Numbers;

public class Enderscape extends AbstractPassiveTrigger<EntityDamageByEntityEvent> {

	public Enderscape(SkillSystem system) {
		super(system, EntityDamageByEntityEvent.class, "Enderscape", SkillType.ENCHANTMENT, SkillUsage.TRIGGERED, new ItemStack(Material.ANVIL), 20, new StatPackage(0f).setRange(0.3f).setCooldown(-100f).setManaCost(0f).setDescription("Teleport nearby when hit."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(26000).setRange(3).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(3).setCooldown(24000).setRange(3).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(5).setCooldown(22000).setRange(4).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(7).setCooldown(21000).setRange(4).setManaCost(0));
		this.setStats(SkillRarity.EPIC, new StatPackage(10).setCooldown(20000).setRange(5).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(13).setCooldown(18000).setRange(5).setManaCost(0));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(16).setCooldown(17000).setRange(6).setManaCost(0));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(20).setCooldown(16000).setRange(6).setManaCost(0));
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
		float range = this.getRange(rarity) + this.getScaling().getRange()*level;
		Location loc = null;
		for(int i = 0; i < 100; i++)
		{
			int randx = Numbers.getRandom((int)range*-1, (int) range);
			int randy = Numbers.getRandom(-2,2);
			int randz = Numbers.getRandom((int)range*-1, (int) range);
			Location tempLoc = player.getLocation().add(new Vector(randx, randy, randz));
			if(tempLoc.getBlock().getType() != Material.AIR) continue;
			if(tempLoc.clone().add(new Vector(0,-1,0)).getBlock().getType() == Material.AIR) continue;
			loc = tempLoc;
			break;
		}
		if(loc != null)
		{
			loc.setDirection(player.getLocation().subtract(loc).toVector().normalize());
			player.teleport(loc);
			loc.getWorld().playSound(loc, Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
			this.sendCastMessage(player, rarity, level);
			this.updateCooldown(player, (long) (this.getCooldown(rarity) + this.getScaling().getCooldown() * level));
		}
	}

}
