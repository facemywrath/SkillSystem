package riseofempires.skillsystem.skillhandling.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
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
