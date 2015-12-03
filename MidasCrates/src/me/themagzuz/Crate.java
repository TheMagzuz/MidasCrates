package me.themagzuz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

public class Crate {
	
	public static ItemStack keyItem = new ItemStack(Material.TRIPWIRE_HOOK, 1, (byte) 0);
	
	public static List<ItemStack> items = new ArrayList<ItemStack>();
	
	public static List<MetadataValue> chestMeta = new ArrayList<MetadataValue>();
	
	public static Inventory crateInterface = Bukkit.createInventory(null, 54, "Crates");
	
	public static ItemStack chestItem = new ItemStack(Material.CHEST, 1, (byte) 0);
	
	public static List<ItemStack> crates = new ArrayList<ItemStack>();
	
	public static Map<Integer, Integer> chanceMapper = new HashMap<Integer,Integer>();
	
	public static void InitializeKeyItem(){
		ItemMeta meta = keyItem.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		lore.add((ChatColor.RESET) + (ChatColor.DARK_PURPLE + "Used to open crates"));
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.AQUA + "Crate Key");
		
		keyItem.setItemMeta(meta);
		
		items.add(keyItem);
	}
	
	public static void InitializeCrateItem(){
		ItemMeta meta = chestItem.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		lore.add((ChatColor.RESET) + (ChatColor.DARK_PURPLE + "Open a crate"));
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.GOLD + "Crate");
		
		chestItem.setItemMeta(meta);
		
		crates.add(chestItem);
	}
	
	
	public static void SaveChestData(){
		
	}
	
	public static void AddItemToInterface(){
		crates.add(chestItem);
		InitializeCrateInterface();
	}
	
	public static void AddItemToInterface(ItemStack item){
		if(!(item.getType().equals(Material.AIR))){
		crates.add(item);
		InitializeCrateInterface();
		} 
			
		
	}
	
	public static void InitializeCrateInterface(){
		crateInterface.clear();
		for (int i = 0; i < crates.size(); i++){
			crateInterface.setItem(i, crates.get(i));
		}
		
		
	}
	
	
	public static void OpenCrateInterface(Player player){
		player.openInventory(crateInterface);
	}
	public static void SetupChance(){
		
		
		for (int i = 0; i < 100; i++){
			
				
			
		}
		
		for(int chance : chanceMapper.keySet()){
			MidasCrates.pl.getLogger().info(chance + ":" + chanceMapper.keySet());
		}
	}
	
	public static void CalculateChance(int chance){
		int count = 0;
		for (int i = 0; i < chanceMapper.size(); i++){
			count += chanceMapper.get(i);
		}
		if (count != 100){
			MidasCrates.pl.getLogger().log(Level.SEVERE, "The chances do not add up to 100");
			
		} else {
			
		}
		
			
		
	}
	
	
	public static void OpenCrate(Player player){
		double item;

		
		
		if (player.getInventory().containsAtLeast(keyItem, 1)){
		
			if(player.getInventory().firstEmpty() != -1){
		player.sendMessage(ChatColor.AQUA + "Opening a crate!");
		item =  Math.random()*100;
		Chance.Sort();
		for (int i = 0; i < Chance.GetChances().size(); i++){
			
		}
		
		player.getInventory().removeItem(keyItem);
			} else{
				player.sendMessage(ChatColor.RED + "You don't have enough space in your inventory");
			}
	} else{
		player.sendMessage(ChatColor.RED + "You don't have a key!");
	}
	}

}
