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
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;

public class CMDCast implements CommandExecutor {
	
	private CommandManager commandManager;
	
	public CMDCast(CommandManager mgr){
		this.commandManager = mgr;
		mgr.getMain().getCommand("cast").setExecutor(this);
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
		if(user.getSkillList().isEmpty())
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Sorry, you have no skills yet."));
			return true;
		}
		List<SkillStorage> skills = user.getSkillList();
		if(args.length == 0)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You must specify a skill to cast"));
			return true;
		}
		if(SkillManager.getAbstractSkillByName(args[0]) == null)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4That skill doesn't exist"));
			return true;
		}
		AbstractSkill skill = SkillManager.getAbstractSkillByName(args[0]);
		if(args.length == 1)
		{
			commandManager.getMain().getSkillManager().attemptCast(player, new SkillStorage(skill.getName(), user.getSkillRarity(skill), user.getSkillLevel(skill)));
		}
		return true;
	}

}
