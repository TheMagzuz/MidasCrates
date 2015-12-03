package me.themagzuz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
	public PlayerListener(MidasCrates plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	void onPlayerDeath(PlayerDeathEvent e){
		if (MidasCrates.midas == true){
			e.setDeathMessage(e.getEntity().getName() + " tried to cheat King Midas");
			MidasCrates.midas = false;
		}
	}
	@EventHandler
	public void OnInvClick(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		if (inventory.getName().equalsIgnoreCase(Crate.crateInterface.getName())){
			if (clicked.equals(Crate.chestItem)){
				Crate.OpenCrate(player);
			}
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerClickSign(PlayerInteractEvent e){
					Player player = (Player) e.getPlayer();
					
					if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
						Block b = e.getClickedBlock();
						//player.sendMessage(e.getAction().toString());
						if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
							Sign sign = (Sign) b.getState();
							String[] lines = sign.getLines();
							//player.sendMessage(lines.toString());
							if(lines[0].equalsIgnoreCase("[midas]")){
								MidasCrates.midas(player);
								
							}
						}
					}
					
	            }
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e){
		Player player = (Player) e.getPlayer();
		player.sendMessage(ChatColor.GREEN + "Skriv " + ChatColor.BLUE + "/peng " + ChatColor.GREEN  +"for at få hjælp");
	}
	@EventHandler
	public void OnEntityDeath(EntityDeathEvent e){
		Player player = (Player) e.getEntity().getKiller();
		
		if (player == null) return;
		
		if (e.getEntity() instanceof Monster){
			double rand = Math.random();
		
			if (rand >= 0.95){
				e.getDrops().add(Crate.keyItem);
				player.sendMessage(ChatColor.AQUA + "You got a key!");
			}
			
		}
		
	}
	
}
	

