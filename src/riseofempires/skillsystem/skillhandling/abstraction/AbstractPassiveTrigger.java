package riseofempires.skillsystem.skillhandling.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public abstract class AbstractPassiveTrigger<T extends Event> extends AbstractSkill implements Consumer<T> {
	
	private List<UUID> enabled = new ArrayList<>();

	public AbstractPassiveTrigger(SkillSystem system, Class<T> classType, String name, SkillType type, SkillUsage usage, ItemStack indicator,
			int maxLevel, StatPackage scaling) {
		super(system, name, type, usage, indicator, maxLevel, scaling);
		Events.listen(system, classType, event -> this.accept((T) event));
	}
	
	public void accept(T event)
	{
		return;
	}
	
	public boolean attemptCast(Player player)
	{
		User user = this.getMain().getCore().getUserManager().getUser(player);	
		SkillRarity rarity = user.getSkillRarity(this);
		int level = user.getSkillLevel(this);
		if(!user.hasSkill(this))
		{
			return false;
		}
		if(this.isOnCooldown(player))
		{
			return false;
		}
		if(this.getManaCost(rarity) + level*this.getScaling().getMana() > user.getCurrentMana())
		{
			return false;
		}
		return true;
	}
	
	public void finishCast(Player player)
	{
		User user = this.getMain().getCore().getUserManager().getUser(player);	
		SkillRarity rarity = user.getSkillRarity(this);
		int level = user.getSkillLevel(this);
		this.updateCooldown(player, (long) (1L*this.getCooldown(rarity) + 1L*this.getScaling().getCooldown()*level));
		this.sendCastMessage(player, rarity, level);
	}
	
	public boolean isEnabled(Player player)
	{
		return(!enabled.isEmpty() && enabled.contains(player.getUniqueId()));
	}
	
	public void toggle(Player player)
	{
		UUID uuid = player.getUniqueId();
		if(enabled.isEmpty() || !enabled.contains(uuid)) enabled.add(uuid);
		else enabled.remove(uuid);
	}

}
