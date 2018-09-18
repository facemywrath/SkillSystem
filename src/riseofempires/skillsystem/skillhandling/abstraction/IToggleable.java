package riseofempires.skillsystem.skillhandling.abstraction;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import riseofempires.skillsystem.skillhandling.storage.SkillRarity;

public interface IToggleable {

	public abstract void toggle(Player player, SkillRarity rarity, int level);
	public abstract void toggle(Player player, LivingEntity ent, SkillRarity rarity, int level);
	public boolean isEnabled(Player player);
	public Long getExpiration(Player player);

}
