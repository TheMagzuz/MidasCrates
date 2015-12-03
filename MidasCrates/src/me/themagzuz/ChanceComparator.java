package me.themagzuz;

import java.util.Comparator;

class ChanceComparator implements Comparator<Chance>{

	@Override
	public int compare(Chance o1, Chance o2) {
		return o1.GetChance().compareTo(o2.GetChance());
	}
	
	
}