package riseofempires.skillsystem.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import riseofempires.core.userhandler.User;
import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.abstraction.IToggleable;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class Xray extends AbstractSkill implements IToggleable{

	HashMap<Player, Long> activated = new HashMap<>();
	HashMap<Player, List<Block>> listOfBlocks = new HashMap<>();

	public Xray(SkillSystem system) {
		super(system, "Xray", SkillType.DIVINATION, SkillUsage.SELF, new ItemStack(Material.MONSTER_EGG), 5, new StatPackage(0f).setCooldown(-15000f).setDuration(100f).setManaCost(0).setDescription("View through whatever is in front of you."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(180000).setDuration(5000).setManaCost(0));
		this.setStats(SkillRarity.COMMON, new StatPackage(2).setCooldown(150000).setDuration(6500).setManaCost(0));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(3).setCooldown(135000).setDuration(8000).setManaCost(0));
		this.setStats(SkillRarity.RARE, new StatPackage(4).setCooldown(120000).setDuration(9500).setManaCost(0));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(5).setCooldown(100000).setDuration(11000).setManaCost(0));
	}

	@Override
	public boolean cast(LivingEntity caster, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		this.getMain().getServer().getScheduler().runTaskLater(this.getMain(), () -> toggle(player), 2L);
		return true;
	}
	@Override
	public void toggle(Player player) {
		if(isEnabled(player)) 
		{
			activated.remove(player);
			listOfBlocks.get(player).stream().forEach(block -> block.getState().update());
			listOfBlocks.remove(player);
		}
		else 
		{
			User user = this.getMain().getCore().getUserManager().getUser(player);	
			SkillRarity rarity = user.getSkillRarity(this);
			int level = user.getSkillLevel(this);
			Long time = (long) (this.getDuration(rarity)*1L + this.getScaling().getDuration()*1L*level);
			int radius = level+5;
			List<Block> blocks = new ArrayList<>();
			for(int x = -1*radius; x < radius; x++)
			{
				for(int y = -1*radius; y < radius; y++)
				{
					for(int z = -1*radius; z < radius; z++)
					{
						Location loc = player.getEyeLocation().add(new Vector(x,y,z));
						float dot = (float) player.getEyeLocation().subtract(loc).toVector().normalize().dot(player.getEyeLocation().getDirection());
						if(dot < -0.8 && loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.GLASS && loc.getBlock().getType().isSolid())
						{
							blocks.add(loc.getBlock());
							player.sendBlockChange(loc, 95, (byte) 15);
						}
					}
				}
			}
			listOfBlocks.put(player, blocks);
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
}
