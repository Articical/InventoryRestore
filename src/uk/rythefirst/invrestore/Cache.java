package uk.rythefirst.invrestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Cache {
	
	private HashMap<UUID, ArrayList<Inventory>> list;
	
	public Cache() {
		list = new HashMap<UUID, ArrayList<Inventory>>();
	}
	
	public ArrayList<Inventory> getInvs(Player p){
		return list.get(p.getUniqueId());
	}

}
