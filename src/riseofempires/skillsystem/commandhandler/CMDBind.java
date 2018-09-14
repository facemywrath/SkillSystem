package riseofempires.skillsystem.commandhandler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import riseofempires.core.main.Core;
import riseofempires.core.userhandler.SkillStorage;
import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.managers.SkillManager;

public class CMDBind implements CommandExecutor {

	private CommandManager commandManager;

	public CMDBind(CommandManager mgr){
		this.commandManager = mgr;
		mgr.getMain().getCommand("bind").setExecutor(this);
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
			ItemStack item = player.getItemInHand();
			if(user.isItemBinded(item))
			{
				SkillStorage ss = user.getBindInformation(item);
				user.unbindItem(item);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + ss.getRarity().formatName() + " " + ss.getSkill().getName() + " Level " + ss.getLevel() + " unbound from " + (item.getItemMeta().hasDisplayName()?item.getItemMeta().getDisplayName():item.getType().name().toLowerCase())));
			}
			return true;
		}
		if(SkillManager.getAbstractSkillByName(args[0]) == null)
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4That skill doesn't exist"));
			return true;
		}
		AbstractSkill skill = SkillManager.getAbstractSkillByName(args[0]);
		if(!user.hasSkill(skill))
		{
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You don't have that skill."));
			return true;
		}
		if(args.length == 1)
		{
			user.bindSkill(player.getItemInHand(), skill, user.getSkillRarity(skill), user.getSkillLevel(skill));
		}
		else
		{
			if(StringUtils.isNumeric(args[1]))
			{
				int level = Integer.parseInt(args[1]);
				if(user.hasSkill(skill, level))
				{
					user.bindSkill(player.getItemInHand(), skill, user.getSkillRarity(skill), level);
				}
			}
		}
		return true;
	}

}
