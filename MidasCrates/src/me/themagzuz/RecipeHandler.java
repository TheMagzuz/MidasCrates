package me.themagzuz;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeHandler {
	private MidasCrates pl;
	public RecipeHandler(MidasCrates plugin){
		this.pl = plugin;
	}
	public void AddRecipe(ShapedRecipe rec){
		pl.getServer().addRecipe(rec);
	}
	
	// This should be used when adding new recipes
	public void SetupRecipes(){
		ShapedRecipe keyRec = new ShapedRecipe(Crate.keyItem);
		
		keyRec.shape("   ", " X ", "   ");
		keyRec.setIngredient('X', Material.TRIPWIRE_HOOK);
		AddRecipe(keyRec);
	}
}
