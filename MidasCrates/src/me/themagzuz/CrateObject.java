package me.themagzuz;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class CrateObject {
	private String name;
	private ArrayList<Chance> winnings = new ArrayList<Chance>();
	private ArrayList<Integer> chances = new ArrayList<Integer>();
	private FileConfiguration config = MidasCrates.pl.getConfig();
	private MidasCrates pl = MidasCrates.pl;
	public CrateObject(String Name){
		name = Name;
		if (config.contains("Crates."+name)){
			pl.getLogger().severe("This crate is already set. It will not be overwritten");
			return;
		} else {
			config.set("Crates."+name+".Winnings", winnings);
		}

	}
	public String GetName(){
			return name;
	}
	public ArrayList<Chance> GetWinnings(){
		return winnings;
	}
	public void AddWinning(Chance add, String name){
		String path = ("Crates."+GetName()+"."+name);
		GetWinnings().add(add);
		if (config.isSet(path)){
			pl.getLogger().severe(String.format("%s is already set in the config", path));;
			return;
		}
		config.set("Crates."+GetName()+"."+name, add.GetChance());
		pl.saveConfig();
		pl.reloadConfig();
	}
}
