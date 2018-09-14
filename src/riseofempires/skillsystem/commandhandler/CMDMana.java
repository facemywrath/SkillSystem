package riseofempires.skillsystem.commandhandler;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import riseofempires.core.main.Core;
import riseofempires.core.userhandler.SkillStorage;
import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.util.TextUtil;

public class CMDMana implements CommandExecutor {
	
	private CommandManager commandManager;
	
	public CMDMana(CommandManager mgr){
		this.commandManager = mgr;
		mgr.getMain().getCommand("mana").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Command can only be done by players, sorry bud.");
			return true;
		}
		Player player = (Player) sender;
		Core core = commandManager.getMain().getCore();
		if(core.getUserManager().getUser(player) == null)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Your data didn't load correctly. Rejoin. Sorry!"));
			return true;
		}
		User user = core.getUserManager().getUser(player);
		player.sendMessage(TextUtil.createManaBar(user.getCurrentMana(), user.getMaxMana()));
		return true;
	}

}
