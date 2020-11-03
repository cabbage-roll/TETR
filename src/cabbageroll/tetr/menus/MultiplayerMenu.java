package cabbageroll.tetr.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cabbageroll.tetr.Main;

public class MultiplayerMenu implements InventoryHolder{
    private Inventory inventory=null;
    public MultiplayerMenu(Player player){
        Main.lastui.put(player, "multiplayer");
        Inventory inventory=Bukkit.createInventory(this, 54, "Multiplayer");
        ItemStack border=new ItemStack(Material.THIN_GLASS);
        //fill the border with glass
        for(int i=0;i<9;i++){
            inventory.setItem(i, border);
        }
        for(int i=45;i<54;i++){
            inventory.setItem(i, border);
        }
        
        //clickable items
        ItemStack item;
        ItemMeta itemmeta;
        
        item=new ItemStack(Material.DIRT);
        itemmeta=item.getItemMeta();
        itemmeta.setDisplayName("CREATE ROOM");
        item.setItemMeta(itemmeta);
        inventory.setItem(9, item);
        
        item=new ItemStack(Material.DIRT);
        itemmeta=item.getItemMeta();
        itemmeta.setDisplayName("ROOM LISTING");
        item.setItemMeta(itemmeta);
        inventory.setItem(10, item);
        
        item=new ItemStack(Material.DIRT);
        itemmeta=item.getItemMeta();
        itemmeta.setDisplayName("BACK");
        item.setItemMeta(itemmeta);
        inventory.setItem(36, item);
        
        player.openInventory(inventory);
    }
    
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
