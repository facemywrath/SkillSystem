package riseofempires.skillsystem.skillhandling.abstraction;

import org.bukkit.entity.Player;

public interface IToggleable {
	
	public void toggle(Player player);
	public boolean isEnabled(Player player);
	public Long getExpiration(Player player);

}
