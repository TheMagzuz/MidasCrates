package me.themagzuz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Chance {
	@Deprecated
	private int loc;
	
	private String out;
	private int chance;
	
	private static List<Chance> chances = new ArrayList<Chance>();
	
	private FileConfiguration config = MidasCrates.pl.getConfig();
	
	@Deprecated
	public Chance(int Location, String output, int Chance, boolean override){
		for(int i = 0; i < chances.size(); i++){
			if (chances.get(i).GetLoc() == Location){
				if (!override){
				MidasCrates.pl.getLogger().log(Level.SEVERE, "Attempted to add a chance to a occupied location. Enable override to put it, disregarding existing chances", new ChanceExistsException("Chance already exists"));
				return;
				} else{
					chances.remove(i);
				}
			}	
		} 
		loc = Location;
		out = output;
		chance = Chance;
		chances.add(this);
		for (int i = 0; i < GetChances().size(); i++){
			MidasCrates.pl.getConfig().set("Crates."+i+".Chance", this.chance);
			MidasCrates.pl.getConfig().set("Crates."+i+".Output", this.out);
			MidasCrates.pl.saveConfig();
			MidasCrates.pl.reloadConfig();
		}
	}
	
	public Chance(int chance, ItemStack prize, String name, CrateObject crate){
		if (config.isSet("Crates."+crate.GetName()+"."+name)){
			MidasCrates.pl.getLogger().severe(String.format("The chance %s already exists! It will not be created", name));
			return;
		} else if (config.contains("Crates."+crate.GetName())){
			MidasCrates.pl.getLogger().severe(String.format("The crate %s does not exist [Thrown by chance %s]", crate.GetName(), name));
		} else {
			crate.AddWinning(this, name);
		}
	}
	
	
	public static Chance GetChanceIndex(int get){
		
		
		for (int i = 0; i < chances.size(); i++){
			if(!(chances.get(i) == null)){
			if (chances.get(i).GetLoc() == get){
				return chances.get(i);
			}
			} else continue;
		}
		
		return null;
	}
	@Deprecated
	public Integer GetLoc(){
		return loc;
	}
	
	public String GetOut(){
		return out;
	}
	
	public Integer GetChance(){
		return chance;
	}
	@Deprecated
	public void SetLoc(int set) throws ChanceExistsException{
		for (int i = 0; i < chances.size(); i++){
			if (chances.get(i).GetLoc() == set){
				throw new ChanceExistsException("Could not set the location to " + set + " as there already exists a chance there");
			}
		}
		loc = set;
	}
	
	public static boolean OneExists(){
		return chances.size() != 0;
	}
	
	public static int ChanceCount(){
		return chances.size();
	}
	
	public String toString(){
		return (loc + ":" + out + ":" + chance);
	}
	
	public static List<Chance> GetChances(){
		return chances;
	}
	
	public static void Sort(){
		Collections.sort(GetChances(), new ChanceComparator());
	}
	
}

