package me.themagzuz;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {
	
	private MidasCrates pl;
	
	public BlockListener(MidasCrates plugin){
		pl = plugin;
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	public void InitializeCrateBlock(){
		return;
	}
	
	@EventHandler
	public void OnBlockPlace(BlockPlaceEvent e){
		Block b = e.getBlock();
		Player p = (Player) e.getPlayer();
		
		p.sendMessage(b.toString());
		
	}
	
	
	}



