package me.Logaaan.mini;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener, CommandExecutor{
	
	List<ActiveMob> mobs = new ArrayList<ActiveMob>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("givemec").setExecutor(this);
	}
	
	public void onDisable() {
		
	}
	
	@EventHandler
	public void shiftRight(PlayerInteractEvent e) {
		if (e.getPlayer().isSneaking()) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasLore()) {
					if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED+"Custom Sword")) {
						MobManager mm = MythicMobs.inst().getMobManager();
						ActiveMob mob = mm.spawnMob("testmob", e.getPlayer().getLocation());
						mobs.add(mob);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onmDeath(EntityDeathEvent e) {
		if (mobs.contains(MythicMobs.inst().getMobManager().getActiveMob(e.getEntity().getUniqueId()).get())) {
			e.getEntity().getKiller().sendMessage("GG");
		}
	}
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			ItemStack zbran = ((Player) e.getDamager()).getInventory().getItemInMainHand();
			if (zbran.hasItemMeta() && zbran.getItemMeta().hasLore()) {
				List<String> lore = zbran.getItemMeta().getLore();
				int index = 0;
				int r_l = 0;
				int rem = 99999; //max dur.
				int w = 0; //zbyva
				boolean dobreak = false;
				for (String l : lore) {
					if (l.contains("Zivotnost")) {
						r_l = index;
						l = ChatColor.stripColor(l);
						String[] well = l.split(" ");
						String[] ziv = well[1].split("/");
						w = Integer.parseInt(ziv[0]);
						rem = Integer.parseInt(ziv[1]);
						w -= 1;
						if (w == 0) {
							dobreak = true;
						}
					}
					index++;
				}
				if (w > 0) {
					lore.set(r_l, ChatColor.GRAY+"Zivotnost: "+w+"/"+rem);
					ItemMeta m = zbran.getItemMeta();
					m.setLore(lore);
					zbran.setItemMeta(m);
					((Player) e.getDamager()).getItemInHand().setItemMeta(m);
				}
				if (dobreak == true) {
					((Player) e.getDamager()).getInventory().remove(((Player) e.getDamager()).getItemInHand());
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			ItemStack sw = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta mt = sw.getItemMeta();
			mt.setDisplayName(ChatColor.RED+"Custom Sword");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY+"Zivotnost: 10/10");
			mt.setLore(lore);
			sw.setItemMeta(mt);
			p.getInventory().addItem(sw);
		}
		
		
		return true;
		
	}
}
