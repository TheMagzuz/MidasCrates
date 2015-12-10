package me.themagzuz;

import org.bukkit.inventory.ShapedRecipe;

public class RecipeHandler {
	private MidasCrates pl;
	public RecipeHandler(MidasCrates plugin){
		this.pl = plugin;
	}
	public void AddRecipe(ShapedRecipe rec){
		pl.getServer().addRecipe(rec);
	}
}
