package me.Logaaan.mini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	Map<Chunk,ActiveMob> mobs = new HashMap<Chunk,ActiveMob>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("givemec").setExecutor(new Commands());
	}
	
	public void onDisable() {
		
	}
	
	@EventHandler
	public void shiftRight(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking()) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore()) {
					if (p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED+"Custom Sword")) {
						MobManager mm = MythicMobs.inst().getMobManager();
						ActiveMob mob = mm.spawnMob("testmob", e.getPlayer().getLocation());
						mobs.put(e.getPlayer().getLocation().getChunk(), mob);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e) {
		if (mobs.containsKey(e.getChunk())) {
			for (Chunk c : mobs.keySet()) {
				ActiveMob m = mobs.get(c);
				m.setDespawned();
				mobs.remove(c);
			}
		}
	}
	
	@EventHandler
	public void onmDeath(MythicMobDeathEvent e) {
		if (mobs.containsValue(e.getMob())) {
			e.getKiller().sendMessage("GG");
			mobs.values().remove(e.getMob());
		}
	}
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (p.getItemInHand() != null)  {
				ItemStack zbran = p.getInventory().getItemInMainHand();
				if (zbran.hasItemMeta() && zbran.getItemMeta().hasLore()) {
					List<String> lore = zbran.getItemMeta().getLore();
					int index = 0;
					int index_zivotnost = 0;
					int max_dur = 99999; //max dur.
					int remaining = 0; //zbyva
					boolean dobreak = false;
					for (String l : lore) { //je to tak lepší, protože mám rovnou string a nemusím ho získávat
						if (l.contains("Zivotnost")) {
							index_zivotnost = index;
							l = ChatColor.stripColor(l);
							String[] well = l.split(" ");
							String[] ziv = well[1].split("/");
							remaining = Integer.parseInt(ziv[0]);
							max_dur = Integer.parseInt(ziv[1]);
							remaining -= 1;
							if (remaining == 0) {
								p.getInventory().remove(p.getItemInHand());
							} else  {
								ItemMeta m = zbran.getItemMeta(); 
								lore.set(index_zivotnost, ChatColor.GRAY+"Zivotnost: "+remaining+"/"+max_dur);
								m.setLore(lore); //protože nechceme, aby se lore breaknul (tøeba staty) -> replacneme urèitou øádku.
								p.getItemInHand().setItemMeta(m);
							}
						}
						index++;
					}
				}
			}
		}
	}


}
