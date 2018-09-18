package riseofempires.skillsystem.skills;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.abstraction.Events;
import riseofempires.skillsystem.skillhandling.abstraction.IToggleable;
import riseofempires.skillsystem.skillhandling.abstraction.IUnlockable;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class DrillHands extends AbstractSkill implements IUnlockable,IToggleable{

	HashMap<Player, Long> activated = new HashMap<>();

	public DrillHands(SkillSystem system) {
		super(system, "DrillHands", SkillType.ENCHANTMENT, SkillUsage.SELF, new ItemStack(Material.DIAMOND_PICKAXE), 5, new StatPackage(0f).setCooldown(-15000f).setDuration(100f).setManaCost(0).setDescription("Automatically smelt mined ores."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(180000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(2).setCooldown(150000).setDuration(6500).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(3).setCooldown(135000).setDuration(8000).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(4).setCooldown(120000).setDuration(9500).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(5).setCooldown(100000).setDuration(11000).setManaCost(0));
		Events.listen(system, BlockDamageEvent.class, event -> {
			Player player = event.getPlayer();
			if(!activated.containsKey(player)) return;
			event.setInstaBreak(true);
		});
	}

	@Override
	public boolean cast(LivingEntity caster, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		toggle(player, rarity, level);
		return true;
	}

	@Override
	public AbstractSkill getUnlockedBy() {
		return this.getMain().getSkillManager().getSkillByName("Mining");
	}

	@Override
	public int getUnlockedLevel() {
		return 45;
	}

	@Override
	public void toggle(Player player, SkillRarity rarity, int level) {
		if(isEnabled(player)) activated.remove(player);
		else 
		{
			Long time = (long) (this.getDuration(rarity)*1L + this.getScaling().getDuration()*1L*level);
			activated.put(player, System.currentTimeMillis() + time);
		}
	}

	@Override
	public boolean isEnabled(Player player) {
		return activated.containsKey(player);
	}

	@Override
	public Long getExpiration(Player player) {
		if(isEnabled(player)) return activated.get(player);
		return 0L;
	}

	@Override
	public void toggle(Player player, LivingEntity ent, SkillRarity rarity, int level) {
		// TODO Auto-generated method stub
		
	}
}
