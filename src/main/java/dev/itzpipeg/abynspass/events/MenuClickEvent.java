package dev.itzpipeg.abynspass.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuClickEvent implements Listener {

    @EventHandler

    public void onPlayerClick(InventoryClickEvent event){

        if(event.getView().getTitle().contains("Menú de Misiones")){

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                event.setCancelled(true);
            }
        }
    }
}
