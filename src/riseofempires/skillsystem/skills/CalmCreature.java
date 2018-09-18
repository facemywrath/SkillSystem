package riseofempires.skillsystem.skills;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.abstraction.Events;
import riseofempires.skillsystem.skillhandling.abstraction.IToggleable;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class CalmCreature extends AbstractSkill implements IToggleable{

	HashMap<Player, Long> activated = new HashMap<>();
	HashMap<Player, Creature> mobs = new HashMap<>();

	public CalmCreature(SkillSystem system) {
		super(system, "CalmCreature", SkillType.NECROMANCY, SkillUsage.TARGET, new ItemStack(Material.MONSTER_EGG), 5, new StatPackage(0f).setCooldown(-15000f).setDuration(100f).setManaCost(0).setDescription("Calm the mind of a creature to get it to stop attacking."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(180000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(2).setCooldown(150000).setDuration(6500).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(3).setCooldown(135000).setDuration(8000).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(4).setCooldown(120000).setDuration(9500).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(5).setCooldown(76000).setDuration(11000).setManaCost(0));
		Events.listen(system, EntityTargetEvent.class, event -> {
			if(!(event.getTarget() instanceof Player)) return;
			Player target = (Player) event.getTarget();
			if(!activated.containsKey(target)) return;
			if(!mobs.containsValue(event.getEntity())) return;
			event.setCancelled(true);
		});
	}

	@Override
	public boolean cast(LivingEntity caster, LivingEntity target, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		if(!(target instanceof Creature)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Must be cast on a creature."));
			return false;
		}
		toggle(player, target, rarity, level);
		return true;
	}
	@Override
	public void toggle(Player player, LivingEntity target, SkillRarity rarity, int level) {
		if(isEnabled(player)) 
			{
			mobs.remove(player);
			activated.remove(player);
			}
		else 
		{
			((org.bukkit.entity.Creature)target).setTarget(null);
			Long time = (long) (this.getDuration(rarity)*1L + this.getScaling().getDuration()*1L*level);
			mobs.put(player, (Creature) target);
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
	public void toggle(Player player, SkillRarity rarity, int level) {
		// TODO Auto-generated method stub
		
	}
}
