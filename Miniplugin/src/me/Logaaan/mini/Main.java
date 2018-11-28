package me.Logaaan.mini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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
	public void clickInv(InventoryClickEvent e) {
		if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
			if (e.getCursor() != null && e.getCursor().hasItemMeta() && e.getCursor().getItemMeta().hasDisplayName() && e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Listek na Vylepseni")) {
				if (e.getCursor().getAmount() == 1) {
					List<String> lore = e.getCurrentItem().getItemMeta().getLore();
					int index = 0;
					boolean applied = false;
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					if (!name.contains("[+25]")) {
						for (String s : lore) {
							if (s.contains("Poskozeni: ") && !s.contains("Critical")) {
								s = ChatColor.stripColor(s);
								s = s.replace("Poskozeni: ", "");
								int pos = Integer.parseInt(s);
								pos += new Random().nextInt(3) - Math.floor(new Random().nextDouble());
								lore.set(index, ChatColor.GRAY+"Poskozeni: "+pos);
								applied = true;
								ItemMeta m = e.getCurrentItem().getItemMeta();
								m.setLore(lore);
								e.getCurrentItem().setItemMeta(m);
							}
							if (s.contains("Critical Poskozeni: ")) {
								s = ChatColor.stripColor(s);
								s = s.replace("Critical Poskozeni: ", "");
								s = s.replace("%", "");
								int pos = Integer.parseInt(s);
								pos += new Random().nextInt(2) - Math.floor(new Random().nextDouble());
								lore.set(index, ChatColor.GRAY+"Critical Poskozeni: "+pos+"%");
								applied = true;
								ItemMeta m = e.getCurrentItem().getItemMeta();
								m.setLore(lore);
								e.getCurrentItem().setItemMeta(m);
							}
							if (s.contains("Critical Sance: ")) {
								s = ChatColor.stripColor(s);
								s = s.replace("Critical Sance: ", "");
								s = s.replace("%", "");
								int pos = Integer.parseInt(s);
								pos += new Random().nextInt(2) - Math.floor(new Random().nextDouble());
								lore.set(index, ChatColor.GRAY+"Critical Sance: "+pos+"%");
								applied = true;
								ItemMeta m = e.getCurrentItem().getItemMeta();
								m.setLore(lore);
								e.getCurrentItem().setItemMeta(m);
							}
							index ++;
						}
						if (applied == true) {
							boolean hasLevel = false;
							int level = 1;
							for (int x = 1; x < 26; x++) {
								if (name.contains("[+"+x+"]")) {
									hasLevel = true;
									level = x;
								}
							}
							if (hasLevel) {
								name = name.replace("[+"+level+"]", "[+"+(level + 1)+"]");
							} else {
								name = name + " " + ChatColor.YELLOW+"[+1]";
							}
							ItemMeta m = e.getCurrentItem().getItemMeta();
							m.setDisplayName(name);
							e.getCurrentItem().setItemMeta(m);
								
							e.getWhoClicked().sendMessage("Zuslechteni bylo uspesne!"); e.getCursor().setType(Material.AIR); e.setCancelled(true);
						}
					} else {
						e.getWhoClicked().sendMessage("Maximalni stupen vylepseni dosazen!");
					}
				} else {
					e.getWhoClicked().sendMessage("Zuslechtovat lze po jednom!");
				}
			}
		}
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
			List<ItemStack> drops = e.getDrops();
			for (ItemStack i : drops) {
				if (i.hasItemMeta() && i.getItemMeta().hasLore()) {
					List<String> lore = i.getItemMeta().getLore();
					int toAddDam = 0;
					int critC = 0;
					int critH = 0;
					int zapaleni = 0;
					int slow = 0;
					for (String l : lore) {
						if (l.contains("Poskozeni: ") && !l.contains("Critical")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Poskozeni: ", "");
							toAddDam = Integer.parseInt(l);
						}
						if (l.contains("Critical Sance: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Sance: ", "");
							l = l.replace("%", "");
							critC = Integer.parseInt(l);
						}
						if (l.contains("Critical Poskozeni: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Poskozeni: ", "");
							l = l.replace("%", "");
							critH = Integer.parseInt(l);
						}
						if (l.contains("Zpomaleni: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Zpomaleni: ", "");
							l = l.replace("%", "");
							slow = Integer.parseInt(l);
						}
						if (l.contains("Zapaleni: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Zapaleni: ", "");
							l = l.replace("%", "");
							zapaleni = Integer.parseInt(l);
						}
					}
					String rarity = "Common";
					for (int x = 0; x < 5; x++) {
						if (new Random().nextInt(100) < 10) {
							rarity = "Artisan";
						}
						if (new Random().nextInt(100) < 15) {
							rarity = "Ancient";
						}
						if (new Random().nextInt(100) < 25) {
							rarity = "Epic";
						}
						if (new Random().nextInt(100) < 30) {
							rarity = "Strong";
						}
						if (new Random().nextInt(100) < 40) {
							rarity = "Majestic";
						}
						if (new Random().nextInt(100) < 50) {
							rarity = "Uncommon";
						}
						if (new Random().nextInt(100) < 75) {
							rarity = "Common";
						}
					}
					if (rarity.equals("Artisan")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(50) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(50) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(50) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(50) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(50) + 10));
						}
						lore.clear();
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Artisan");
					}
					
					if (rarity.equals("Ancient")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(40) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(40) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(40) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(40) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(40) + 10));
						}
						lore.clear();
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Ancient");
					}
					if (rarity.equals("Epic")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(20) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(20) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(20) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / (100 * new Random().nextInt(20) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(20) + 10));
						}
						lore.clear();						
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						if (new Random().nextInt(100) < 70) {
							lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						}
						lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Epic");
					}
					if (rarity.equals("Strong")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(15) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(15) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(15) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(15) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(15) + 10));
						}
						lore.clear();
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						if (new Random().nextInt(100) < 40) {
							lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						}
						if (new Random().nextInt(100) < 30) {
							lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						}
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Strong");
					}
					if (rarity.equals("Majestic")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(10) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(10) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(10) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(10) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(10) + 10));
						}
						lore.clear();
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						if (new Random().nextInt(100) < 30) {
							lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						}
						if (new Random().nextInt(100) < 10) {
							lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						}
						if (new Random().nextInt(100) < 10) {
							lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						}
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Majestic");
					}
					
					if (rarity.equals("Uncommon")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(8) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(8) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(8) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(8) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(8) + 10));
						}
						lore.clear();	
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						if (new Random().nextInt(100) < 28) {
							lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						}
						if (new Random().nextInt(100) < 25) {
							lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						}
						if (new Random().nextInt(100) < 5) {
							lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						}
						if (new Random().nextInt(100) < 5) {
							lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						}
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Uncommon");
					}
					if (rarity.equals("Common")) {
						if (toAddDam > 0) {
							toAddDam += (toAddDam / 100 * (new Random().nextInt(5) + 10));
						}
						if (critH > 0) {
							critH += (critH / 100 * (new Random().nextInt(5) + 10));
						}
						if (critC > 0) {
							critC += (critC / 100 * (new Random().nextInt(5) + 10));
						}
						if (zapaleni > 0) {
							zapaleni += (zapaleni / 100 * (new Random().nextInt(5) + 10));
						}
						if (slow > 0) {
							slow += (slow / 100 * (new Random().nextInt(5) + 10));
						}
						lore.clear();
						lore.add(ChatColor.RED+"Poskozeni: "+ChatColor.WHITE+""+toAddDam);
						if (new Random().nextInt(100) < 12) {
							lore.add(ChatColor.RED+"Critical Sance: "+ChatColor.WHITE+""+critC+"%");
						}
						if (new Random().nextInt(100) < 5) {
							lore.add(ChatColor.RED+"Critical Poskozeni: "+ChatColor.WHITE+""+critH+"%");
						}
						if (new Random().nextInt(100) < 1) {
							lore.add(ChatColor.RED+"Zapaleni: "+ChatColor.WHITE+""+zapaleni+"%");
						}
						if (new Random().nextInt(100) < 1) {
							lore.add(ChatColor.RED+"Zpomaleni: "+ChatColor.WHITE+""+slow+"%");
						}
						lore.add(ChatColor.RED+"Rarita: "+ChatColor.WHITE+"Common");
					}
					e.getDrops().remove(i);
					ItemMeta m = i.getItemMeta();
					m.setLore(lore);
					i.setItemMeta(m);
					e.getDrops().add(i);
				}
			}
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
							l = ChatColor.stripColor(l);
							l = l.replace("Poskozeni: ", "");
							toAddDam = Integer.parseInt(l);
						}
						if (l.contains("Critical Sance: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Sance: ", "");
							l = l.replace("%", "");
							critC = Integer.parseInt(l);
						}
						if (l.contains("Critical Poskozeni: ")) {
							l = ChatColor.stripColor(l);
							l = l.replace("Critical Poskozeni: ", "");
							l = l.replace("%", "");
							critH = 20 + Integer.parseInt(l);
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
