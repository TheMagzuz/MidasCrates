package me.themagzuz;




import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import jdk.nashorn.internal.runtime.regexp.joni.Config;

@SuppressWarnings("unused")
public class MidasCrates extends JavaPlugin{
	
	public static boolean midas = false;
	
	public static MidasCrates pl;
	
	public static int CrateCount = 0;
	
	public static ChanceComparator compa = new ChanceComparator();
	
	public void LoadConfig(){
		saveConfig();
	}
	
	public static void midas(Player player){
		
			
			
			
			
			int i = player.getItemInHand().getAmount();
			
			if (i == 0){
				player.sendMessage(ChatColor.RED + "You have no items in your hand");
			} else if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), i*2)){
					
					if (player.getItemInHand().getType().toString() == "DIAMOND"){
						player.sendMessage(ChatColor.RED + "You can't turn diamonds into diamonds!");
					} else{
				
			player.setItemInHand(new ItemStack(Material.DIAMOND, i));
			player.sendMessage(ChatColor.AQUA + "Turned the items in your hand to diamonds!");
			player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, i*2));
					}
			} else if (player.getItemInHand().getType().toString() == "DIAMOND"){
				player.sendMessage(ChatColor.RED + "You can't turn diamonds into diamonds!");
			}else {
				midas = true;
				player.setHealth(0.0D);
				player.sendMessage(ChatColor.RED + "Don't try to cheat Midas!");
				// Bukkit.broadcastMessage(player.getName() + " tried to cheat King Midas " + midas);
			}
	}
	
	private void setCrates(){
		if(!pl.getConfig().contains("Crates")){
			pl.getConfig().set("Crates", Chance.GetChances());
		} else if (Chance.GetChances().size() > 0){

			for(int i = 0; i < Chance.GetChances().size(); i++){
				if(pl.getConfig().contains("Crates."+i)) continue;
				else{
					pl.getConfig().set("Crates."+i+".Output", Chance.GetChanceIndex(i).GetOut());
					pl.getConfig().set("Crates."+i+".Chance", Chance.GetChanceIndex(i).GetChance());
				}	
				
			}
			
		} else return;
	}
	
	public static void SetCrates(){
		pl.setCrates();
	}
	public static void SetCrates(Player player, MidasCrates plugin){
		pl.setCrates(player, plugin);
	}
	private void setCrates(Player player, MidasCrates plugin){
		player.sendMessage(this.getDataFolder().toString());
		
			
			for(int i = 0; i < Chance.GetChances().size(); i++){
				
				if(plugin.getConfig().contains("Crates."+i)) continue;
				else{
					player.sendMessage(String.valueOf(i));
					plugin.getConfig().set("Crates."+String.valueOf(i)+".Output", Chance.GetChanceIndex(i).GetOut());
					plugin.getConfig().set("Crates."+String.valueOf(i)+".Chance", Chance.GetChanceIndex(i).GetChance());
					plugin.saveConfig();
					plugin.reloadConfig();
				}
			}
		
	}
	@Override
	public void onEnable(){
		getLogger().info("Initializing Plugin Object");
		pl = this;
		getLogger().info("Initializing Block Listener");
		new BlockListener(this);
		getLogger().info("Initializing Player Listener");
		new PlayerListener(this);
		getLogger().info("Initializing Plugin Manager");
		PluginManager pm = getServer().getPluginManager();
		getLogger().info("Initializing data for the key item");
		Crate.InitializeKeyItem();
		getLogger().info("Initializing Crate Item");
		Crate.InitializeCrateItem();
		getLogger().info("Initializing Crate Interface");
		Crate.InitializeCrateInterface();
		getLogger().info("Loading Config");
		LoadConfig();
		Crate.SetupChance();
		
	}
	
	@Override
	public void onDisable(){
		/*File config = new File(this.getDataFolder() + "/config.yml");
		File crateBlocks = new File(this.getDataFolder() + "/crates.yml");
		if (!config.exists()){
			pl.saveDefaultConfig();
		}
		if (!crateBlocks.exists()){
			
		}
		if(!pl.getConfig().contains("Main.CrateCount")){
			pl.getConfig().set("Main.CrateCount", CrateCount);
		}*/
		
		SetCrates();
		
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player player = (Player) sender;
		
		
		if (cmd.getName().equalsIgnoreCase("itemname") && sender instanceof Player){
			player.sendMessage("The name of the item in your hand is "+ player.getItemInHand().getType().toString());
		}
		if (cmd.getName().equalsIgnoreCase("midas") && sender instanceof Player){
			midas(player);
		

		}
		
		if (cmd.getName().equalsIgnoreCase("crate") && sender instanceof Player){
			Crate.OpenCrate(player);
		}
		
		if (cmd.getName().equalsIgnoreCase("setcrate") && sender instanceof Player && args[0] != null){
			Set<Material> set = null;
			Block lookingAt = player.getTargetBlock(set, 10);
			
			int chestX;
			int chestY;
			int chestZ;
			
			String chestCoords;
			
			java.util.List<MetadataValue> meta = lookingAt.getMetadata("");
			
			if (lookingAt.getType() == Material.CHEST){
				chestX = lookingAt.getX();
				chestY = lookingAt.getY();
				chestZ = lookingAt.getZ();
				
				chestCoords = (chestX + "," + chestY + "," + chestZ);
				
				getConfig().set("Crates." + args[0] + "position", chestCoords);
				
				
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("key") && sender instanceof Player){
			
			if (args.length == 0){
					player.getInventory().addItem(Crate.keyItem);
				} else if (args.length == 1){
				Player toGive = Bukkit.getPlayerExact(args[0]);
				if (toGive == null){
					player.sendMessage(ChatColor.RED + "Player \'" + args[0] + "\' not found");
				} else{
					toGive.getInventory().addItem(Crate.keyItem);
				}
				}else{
				if (args.length == 2){
					Player toGive = Bukkit.getPlayer(args[0]);
					if (toGive == null){
						player.sendMessage(ChatColor.RED + ("Player \'" + args[0] + "\' not found"));
					}
					try{
						Integer.parseInt(args[1]);
					} catch (NumberFormatException n){
						player.sendMessage(ChatColor.RED + ("\'" + args[1] + "\' is not a valid number"));
						return false;
					}
					int giveCount = Integer.parseInt(args[1]);
					for (int i = 0; i < giveCount; i++){
						toGive.getInventory().addItem(Crate.keyItem);
					}
					player.sendMessage(ChatColor.GREEN + ("Gave " + args[0] + " " + args[1] + " key(s)"));
					
				}
				
				}
			} 
			
			
			


	

		///////////////////*MAIN COMMAND*////////////////////
		if (cmd.getName().equalsIgnoreCase("midascrates")){
			if (args[0].equalsIgnoreCase("reload")){
				reloadConfig();
			} else if (args[0].equalsIgnoreCase("setcrates")){
				SetCrates(player, pl);
			} else if (args[0].equalsIgnoreCase("sort")){
				int chance = 0;
				Collections.sort(Chance.GetChances(), new ChanceComparator());
				for (int i = 0; i < Chance.GetChances().size(); i++){
					player.sendMessage(String.format("I: %s, Chance: %s", i, Chance.GetChances().get(i).GetChance()));
					chance += Chance.GetChances().get(i).GetChance();
				}
			} else if (args[0].equalsIgnoreCase("crateobj")){
				CrateObject crate = new CrateObject("test");
				crate.AddWinning(new ItemStack(Material.DIAMOND), "Diamond");
			}
			else {															
				player.sendMessage(ChatColor.RED + "Invalid SubCommand!");
			}
		/////////////////*END MAIN COMMAND*//////////////////
		}else if (cmd.getName().equalsIgnoreCase("InitKeyItem")){
			Crate.InitializeKeyItem();
		} else if (cmd.getName().equalsIgnoreCase("crateinterface")){
			Crate.OpenCrateInterface(player);
		} else if (cmd.getName().equalsIgnoreCase("addcrate")){
			if (args.length == 0){
				Crate.AddItemToInterface();
				player.sendMessage(ChatColor.GREEN + "Added a crate to the interface!");
			} else if (args.length == 1){
				boolean failed = false;
				boolean addedHand = false;
				ItemStack item = new ItemStack(Material.AIR, 1, (byte) 0);
				try{
				item = new ItemStack(Material.getMaterial(args[0].toUpperCase()), 1);
				} catch(Exception e){
					e.printStackTrace();
					getLogger().info(e.getClass().getName());
					if(args[0].equalsIgnoreCase("hand")){
						if (!(player.getItemInHand().getType().equals(Material.AIR))){
							Crate.AddItemToInterface(player.getItemInHand());
							addedHand = true;
							player.sendMessage(ChatColor.GREEN + "Added item " + player.getItemInHand().getType() + " to the crate interface");
						} else{
							player.sendMessage(ChatColor.RED + "Cannot add nothing to the crate interface");
						}
					}
					failed = true;
				}
				if (item.getType().equals(Material.AIR)){
					player.sendMessage(ChatColor.RED + "Cannot add air to the crate interface");
				} else if (!failed){
					player.sendMessage(ChatColor.GREEN + "Added item " + item.getType() + " to the crate interface");
				Crate.AddItemToInterface(item);
				} else if(!addedHand){
					player.sendMessage(ChatColor.RED + "Something went wrong!");
				}
				failed = false;
				addedHand = false;
		}	
		

		} else if (cmd.getName().equalsIgnoreCase("getchances")){
			if(Chance.OneExists()){
			for (int i = 0; i < Chance.ChanceCount(); i++){
					Chance chance = Chance.GetChanceIndex(i);
					if (chance != null){
						player.sendMessage(chance.toString());
					}
			}
			} else{
				player.sendMessage(ChatColor.RED + "There are no chances yet!");
			}
		} else if (cmd.getName().equalsIgnoreCase("addchance")){
			if (args.length == 3){
				boolean arg1IsInt;
				boolean arg3IsInt;
				
				try{
					Integer.parseInt(args[0]);
					arg1IsInt = true;
				} catch(Exception e){
					player.sendMessage(ChatColor.RED + args[0].toString() + " is not a valid number");
					arg1IsInt = false;
					return false;
				}
				try{
					Integer.parseInt(args[2]);
					arg3IsInt = true;
				} catch (Exception e){
					player.sendMessage(ChatColor.RED + args[2].toString() + " is not a valid number");
					arg3IsInt = false;
					return false;
				}
				if (arg1IsInt && arg3IsInt){
					Chance chance = new Chance(Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]), true);
					player.sendMessage(ChatColor.GREEN + "Created chance: " + chance.toString());
				}
			} else{
				player.sendMessage(ChatColor.RED + "Wrong syntax!");
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("peng")){
			Player peng = Bukkit.getPlayer("pengusej123");
			if (peng != null){
				peng.playSound(peng.getLocation(), Sound.NOTE_PIANO, 100.0f, 1);
				player.sendMessage(ChatColor.GREEN + "Pinged pengusej123!");
			} else {
				player.sendMessage(ChatColor.RED + "Pengusej123 is not online right now");
			}
		}	
		return true;
	
	
	}
}
	


