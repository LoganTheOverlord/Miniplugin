package me.Logaaan.mini;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			ItemStack sw = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta mt = sw.getItemMeta();
			mt.setDisplayName(ChatColor.RED+"Custom Sword");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY+"Zivotnost: 100/100");
			lore.add(ChatColor.GRAY+"Poskozeni: "+new Random().nextInt(7));
			lore.add(ChatColor.GRAY+"Critical Sance: "+new Random().nextInt(80)+"%");
			lore.add(ChatColor.GRAY+"Critical Poskozeni: "+new Random().nextInt(120)+"%");
			mt.setLore(lore);
			sw.setItemMeta(mt);
			p.getInventory().addItem(sw);
			ItemStack zus = new ItemStack(Material.PAPER);
			ItemMeta mt2 = zus.getItemMeta();
			mt2.setDisplayName(ChatColor.GREEN+"Listek na Vylepseni");
			zus.setItemMeta(mt2);
			p.getInventory().addItem(zus);
		}
		
		
		return true;
		
	}
}
