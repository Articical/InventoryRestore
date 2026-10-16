package uk.rythefirst.invrestore.managers.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class InventoryTools {
	
	// Serialize full player inventory (main + armor + offhand)
    public static String serializePlayerInventory(PlayerInventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Main inventory (36 slots)
            dataOutput.writeInt(inventory.getSize());
            for (ItemStack item : inventory.getContents()) {
                dataOutput.writeObject(item);
            }

            // Armor (4 slots)
            for (ItemStack armorItem : inventory.getArmorContents()) {
                dataOutput.writeObject(armorItem);
            }

            // Offhand (1 slot)
            dataOutput.writeObject(inventory.getItemInOffHand());

            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Deserialize and apply to player
    public static void deserializePlayerInventory(Player player, String base64) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            PlayerInventory inventory = player.getInventory();

            // Load main inventory
            int size = dataInput.readInt();
            ItemStack[] contents = new ItemStack[size];
            for (int i = 0; i < size; i++) {
                contents[i] = (ItemStack) dataInput.readObject();
            }
            inventory.setContents(contents);

            // Load armor
            ItemStack[] armor = new ItemStack[4];
            for (int i = 0; i < 4; i++) {
                armor[i] = (ItemStack) dataInput.readObject();
            }
            inventory.setArmorContents(armor);

            // Load offhand
            inventory.setItemInOffHand((ItemStack) dataInput.readObject());

            dataInput.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	

}
