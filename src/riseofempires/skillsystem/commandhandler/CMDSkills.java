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

public class CMDSkills implements CommandExecutor {
	
	private CommandManager commandManager;
	
	public CMDSkills(CommandManager mgr){
		this.commandManager = mgr;
		mgr.getMain().getCommand("skills").setExecutor(this);
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
		player.sendMessage("Your current skills: ");
		for(SkillStorage skill : skills)
		{
			player.sendMessage(skill.getRarity().toString() + " " + skill.getSkill().getName() + " Level " + skill.getLevel());
		}
		return true;
	}

}
