package me.Logaaan.mini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					ItemStack helm = p.getInventory().getHelmet();
					ItemStack chest = p.getInventory().getChestplate();
					ItemStack legs = p.getInventory().getLeggings();
					ItemStack boots = p.getInventory().getBoots();
					ItemStack tool = p.getItemInHand();
					double toAddHP = 0;
					if (tool != null) {
						if (tool.hasItemMeta() && tool.getItemMeta().hasLore()) {
							List<String> lore = tool.getItemMeta().getLore();
							for (String l : lore) {
								if (l.contains("Zivoty: ")) {
									String[] firstpass = l.split(" ");
									toAddHP += Integer.parseInt(ChatColor.stripColor(firstpass[1]));
								}
							}
						}
					}
					//HELMA
					if (helm != null) {
						if (helm.hasItemMeta() && helm.getItemMeta().hasLore()) {
							List<String> lore = helm.getItemMeta().getLore();
							for (String l : lore) {
								if (l.contains("Zivoty: ")) {
									String[] firstpass = l.split(" ");
									toAddHP += Integer.parseInt(ChatColor.stripColor(firstpass[1]));
								}
							}
						}
					}
					//CHESTPLATE
					if (chest != null) {
						if (chest.hasItemMeta() && chest.getItemMeta().hasLore()) {
							List<String> lore = chest.getItemMeta().getLore();
							for (String l : lore) {
								if (l.contains("Zivoty: ")) {
									String[] firstpass = l.split(" ");
									toAddHP += Integer.parseInt(ChatColor.stripColor(firstpass[1]));
								}
							}
						}
					}
					//LEGGINGS
					if (legs != null) {
						if (legs.hasItemMeta() && legs.getItemMeta().hasLore()) {
							List<String> lore = legs.getItemMeta().getLore();
							for (String l : lore) {
								if (l.contains("Zivoty: ")) {
									String[] firstpass = l.split(" ");
									toAddHP += Integer.parseInt(ChatColor.stripColor(firstpass[1]));
								}
							}
						}
					}
					//BOTY
					if (boots != null) {
						if (boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
							List<String> lore = boots.getItemMeta().getLore();
							for (String l : lore) {
								if (l.contains("Zivoty: ")) {
									String[] firstpass = l.split(" ");
									toAddHP += Integer.parseInt(ChatColor.stripColor(firstpass[1]));
								}
							}
						}
					}
					p.setMaxHealth(20+toAddHP);
				}
				
			}
			
		}, 1L, 10L);
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
					double toAddDam = 0;
					double critC = 0;
					double critH = 0;
					boolean dobreak = false;
					for (String l : lore) { //je to tak lepší, protože mám rovnou string a nemusím ho získávat
						if (l.contains("Zivotnost: ")) {
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
						if (l.contains("Poskozeni: ") && !l.contains("Critical")) {
							index_zivotnost = index;
							l = ChatColor.stripColor(l);
							l = l.replace("Poskozeni: ", "");
							toAddDam = Integer.parseInt(l);
						}
						if (l.contains("Critical Sance: ")) {
							index_zivotnost = index;
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Sance: ", "");
							l = l.replace("%", "");
							critC = Integer.parseInt(l);
						}
						if (l.contains("Critical Poskozeni: ")) {
							index_zivotnost = index;
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Poskozeni: ", "");
							l = l.replace("%", "");
							critH = Integer.parseInt(l);
						}


						index++;
					}
					e.setDamage(e.getDamage() + toAddDam);
					if (new Random().nextInt(100) < critC) {
						e.setDamage(e.getDamage() + (e.getDamage() / 100 * critH));
						e.getDamager().sendMessage("Critical Hit!");
					}
				}
			}
		}
	}


}
