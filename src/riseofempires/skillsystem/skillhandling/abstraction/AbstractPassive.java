package riseofempires.skillsystem.skillhandling.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class AbstractPassive extends AbstractSkill implements Listener {
	
	private List<UUID> enabled = new ArrayList<>();

	public AbstractPassive(SkillSystem system, String name, SkillType type, SkillUsage usage, ItemStack indicator,
			int maxLevel, StatPackage scaling) {
		super(system, name, type, usage, indicator, maxLevel, scaling);
		getMain().getServer().getPluginManager().registerEvents(this, getMain());
	}
	
	public boolean autoCast(Player player, SkillRarity rarity, int level)
	{
		return false;
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
