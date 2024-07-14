package dev.itzpipeg.abynspass.menus;

import dev.itzpipeg.abynspass.Main;
import dev.itzpipeg.abynspass.utils.Bar;
import dev.itzpipeg.abynspass.utils.CC;
import dev.itzpipeg.abynspass.utils.MisionStatus;
import dev.itzpipeg.abynspass.utils.MisionType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MisionListInv {

    private final Main plugin;

    private final Inventory inventory;
    public MisionListInv(Main plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, 36, CC.translate("&8Menú de Misiones"));
        initializeItems();
    }


    public void initializeItems(){

        for (String misionsKey : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)) {
            
            ItemStack misionItem = new ItemStack(Material.AIR);

            String misionType = plugin.getConfig().getString("MISIONS." + misionsKey + ".TYPE");

            if(MisionType.valueOf(misionType) == MisionType.MOB_KILL ){

                misionItem.setType(Material.REDSTONE);
            }else if(MisionType.valueOf(misionType) == MisionType.BOSS_KILL ){

                misionItem.setType(Material.REDSTONE_BLOCK);
            }else if(MisionType.valueOf(misionType) == MisionType.BLOCK_PLACE ){

                misionItem.setType(Material.GRASS_BLOCK);

            }else if(MisionType.valueOf(misionType) == MisionType.BLOCK_BREAK ){

                misionItem.setType(Material.DIAMOND_PICKAXE);

            }else if(MisionType.valueOf(misionType) == MisionType.REGION_JOIN ){

                misionItem.setType(Material.GREEN_DYE);
            }else if(MisionType.valueOf(misionType) == MisionType.REGION_LEFT ){

                misionItem.setType(Material.RED_DYE);
            }else if(MisionType.valueOf(misionType) == MisionType.ITEM_CRAFT ){

                misionItem.setType(Material.CRAFTING_TABLE);
            }else if(MisionType.valueOf(misionType) == MisionType.ITEMS_CRAFT ){

                misionItem.setType(Material.CRAFTING_TABLE);
            }

            ItemMeta meta = misionItem.getItemMeta();
            
            meta.setDisplayName(CC.translate("&e" + misionsKey));

            List<String> lore = new ArrayList<>();

            lore.add(CC.translate("&r"));

            lore.add(CC.translate(" &fTipo: &e" + misionType));

            lore.add(CC.translate(" &fValue: &e" + plugin.getConfig().getString("MISIONS." + misionsKey + ".VALUE")));

            if(MisionType.valueOf(misionType) != MisionType.REGION_JOIN && MisionType.valueOf(misionType) != MisionType.REGION_LEFT){

                lore.add(CC.translate(" &fCantidad: &e" + plugin.getConfig().getString("MISIONS." + misionsKey + ".AMOUNT")));
            }

            lore.add(CC.translate("&r"));

            if(plugin.activeChallenge.get(Integer.parseInt(misionsKey)) != null){
                lore.add(CC.translate(" &fEstado: &2" + plugin.activeChallenge.get(Integer.parseInt(misionsKey)).getChallengeStatus()));

                lore.add(CC.translate(" &fIniciado hace: &e" + Bar.formatTimeWithoutPoints((int) ((System.currentTimeMillis() - plugin.activeChallenge.get(Integer.parseInt(misionsKey)).getChallengeStartDate()) / 1000))));
            }else{

                lore.add(CC.translate(" &fEstado: &c" + MisionStatus.NONE));

            }

            lore.add(CC.translate("&r"));

            lore.add(CC.translate(" &eClick para modificar."));

            lore.add(CC.translate("&r"));

            meta.setLore(lore);

            misionItem.setItemMeta(meta);

            misionItem.setAmount(Integer.parseInt(misionsKey));

            inventory.addItem(misionItem);
        }
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public Inventory getInventory(){
        return inventory;
    }
}
