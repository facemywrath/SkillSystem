package riseofempires.skillsystem.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import riseofempires.core.main.Core;
import riseofempires.core.userhandler.User;
import riseofempires.core.userhandler.UserManager;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractPassiveTrigger;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Mining extends AbstractPassiveTrigger<BlockBreakEvent> {

	public Mining(SkillSystem system) {
		super(system, BlockBreakEvent.class, "Mining", SkillType.ENCHANTMENT, SkillUsage.TRIGGERED, new ItemStack(Material.ANVIL), 50, new StatPackage(0f).setCooldown(-1000f).setDuration(150f).setManaCost(0f).setDescription("Gain mining speed."));
		this.setStats(SkillRarity.TRASH, new StatPackage(8).setCooldown(180000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(12).setCooldown(170000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(17).setCooldown(160000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(22).setCooldown(150000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.EPIC, new StatPackage(28).setCooldown(125000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(35).setCooldown(110000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.MYTHIC, new StatPackage(42).setCooldown(100000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.ULTIMATE, new StatPackage(50).setCooldown(90000).setDuration(5000).setManaCost(0));
	}

	@Override
	public void accept(BlockBreakEvent event)
	{
		Player player = (Player) event.getPlayer();
		Core core = this.getMain().getCore();
		UserManager um = core.getUserManager();
		User user = um.getUser(player);
		if(um.getUser(player) == null) return;
		if(!this.attemptCast(player)) return;
		SkillRarity rarity = user.getSkillRarity(this);
		int level = user.getSkillLevel(this);
		int duration = (int) ((this.getDuration(rarity) + level*this.getScaling().getDuration())/50.0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duration, (int) (level/10.0)+1));
		finishCast(player);
	}
}
