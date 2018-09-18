package riseofempires.skillsystem.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import riseofempires.skillsystem.main.SkillSystem;
import riseofempires.skillsystem.skillhandling.abstraction.AbstractSkill;
import riseofempires.skillsystem.skillhandling.abstraction.IToggleable;
import riseofempires.skillsystem.skillhandling.storage.SkillRarity;
import riseofempires.skillsystem.skillhandling.storage.SkillType;
import riseofempires.skillsystem.skillhandling.storage.SkillUsage;
import riseofempires.skillsystem.skillhandling.storage.StatPackage;

public class WolfForm extends AbstractSkill implements IToggleable{
	
	List<Player> activated = new ArrayList<>();

	public WolfForm(SkillSystem system) {
		super(system, "WolfForm", SkillType.TRANSMUTATION, SkillUsage.SELF, new ItemStack(Material.APPLE), 5, new StatPackage(0f).setCooldown(0).setManaCost(0).setDescription("Transform into a wolf."));
		this.setStats(SkillRarity.TRASH, new StatPackage(1).setCooldown(30000).setManaCost(10));
		this.setStats(SkillRarity.COMMON, new StatPackage(2).setCooldown(20000).setManaCost(10));
		this.setStats(SkillRarity.UNCOMMON, new StatPackage(3).setCooldown(10000).setManaCost(6));
		this.setStats(SkillRarity.RARE, new StatPackage(4).setCooldown(5000).setManaCost(6));
		this.setStats(SkillRarity.LEGENDARY, new StatPackage(5).setCooldown(100).setManaCost(2));
	}
	
	@Override
	public boolean cast(LivingEntity caster, int level, SkillRarity rarity)
	{
		Player player = (Player) caster;
		toggle(player, rarity, level);
		return true;
	}

	@Override
	public void toggle(Player player, SkillRarity rarity, int level) {
		if(isEnabled(player)) {
			getMain().getCore().getUserManager().getUser(player).undisguise();
			activated.remove(player);
		}
		else {
			getMain().getCore().getUserManager().getUser(player).setMobDisguise(EntityType.WOLF);
			activated.add(player);
		}
	}

	@Override
	public boolean isEnabled(Player player) {
		return activated.contains(player);
	}

	@Override
	public Long getExpiration(Player player) {
		return Long.MAX_VALUE;
	}

	@Override
	public void toggle(Player player, LivingEntity ent, SkillRarity rarity, int level) {
		// TODO Auto-generated method stub
		
	}
}
