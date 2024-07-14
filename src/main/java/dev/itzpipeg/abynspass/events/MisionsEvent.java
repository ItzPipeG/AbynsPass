package dev.itzpipeg.abynspass.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.itzpipeg.abynspass.Main;
import dev.itzpipeg.abynspass.utils.Bar;
import dev.itzpipeg.abynspass.utils.CC;
import dev.itzpipeg.abynspass.utils.MisionStatus;
import dev.itzpipeg.abynspass.utils.MisionType;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MisionsEvent implements Listener {


    private Main plugin;


    public MisionsEvent(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        Player player = event.getPlayer();

        if(!(event.isCancelled())){
            for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

                int misions = Integer.parseInt(misionsList);

                if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.BLOCK_PLACE){
                    if(plugin.activeChallenge.containsKey(misions)){
                        if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                            if(event.getBlock().getType().equals(Material.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".VALUE")))){

                                String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                }

                                if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                    int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                    int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                    if(totalAmount >= maxAmount){

                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                        plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                        plugin.savePlayerData();

                                        plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                        plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                        if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE", null) != null){
                                            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                        }

                                        if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.SOUND", null) != null){
                                            player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.SOUND")), 10, 1);
                                        }

                                        List<String> cmdList = new ArrayList<>();

                                        for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                            cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                        }

                                        for(String cmd : cmdList){
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                        }


                                    }else{
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                        plugin.savePlayerData();

                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Player player = event.getPlayer();

        if(!(event.isCancelled())){
            for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

                int misions = Integer.parseInt(misionsList);

                if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.BLOCK_BREAK){
                    if(plugin.activeChallenge.containsKey(misions)){
                        if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                            if(event.getBlock().getType().equals(Material.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".VALUE")))){

                                String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                }

                                if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                    int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                    int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                    if(totalAmount >= maxAmount){

                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                        plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                        plugin.savePlayerData();

                                        plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                        plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                        if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                        }

                                        List<String> cmdList = new ArrayList<>();

                                        for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                            cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                        }

                                        for(String cmd : cmdList){
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                        }


                                    }else{
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                        plugin.savePlayerData();

                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event){

        if (event.getEntity().getKiller() == null){
            return;
        }

        Player player = event.getEntity().getKiller();

        for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

            int misions = Integer.parseInt(misionsList);

            if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.MOB_KILL){
                if(plugin.activeChallenge.containsKey(misions)){
                    if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                        if(event.getEntity().getType().toString().equalsIgnoreCase(plugin.getConfig().getString("MISIONS." + misions + ".VALUE"))){

                            String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                            String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                            if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                plugin.savePlayerData();

                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                            }

                            if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                if(totalAmount >= maxAmount){

                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                    plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                    plugin.savePlayerData();

                                    plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                    plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                    if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                        player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                    }

                                    List<String> cmdList = new ArrayList<>();

                                    for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                        cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                    }

                                    for(String cmd : cmdList){
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                    }


                                }else{
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                }
                            }
                        }
                    }
                }
            }else if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.BOSS_KILL){
                if(plugin.activeChallenge.containsKey(misions)){
                    if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                        if(event.getEntity().getCustomName() != null) {
                            if(event.getEntity().getCustomName().equalsIgnoreCase(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".VALUE")))){

                                String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                }

                                if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                    int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                    int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                    if(totalAmount >= maxAmount){

                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                        plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                        plugin.savePlayerData();

                                        plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                        plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                        if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                        }

                                        List<String> cmdList = new ArrayList<>();

                                        for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                            cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                        }

                                        for(String cmd : cmdList){
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                        }


                                    }else{
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                        plugin.savePlayerData();

                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event){

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

                int misions = Integer.parseInt(misionsList);

                if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.ITEM_CRAFT){
                    if(plugin.activeChallenge.containsKey(misions)){
                        if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                            if(event.getRecipe().getResult().getType().toString().equalsIgnoreCase(plugin.getConfig().getString("MISIONS." + misions + ".VALUE"))){

                                String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                    plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                }

                                if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                    int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                    int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                    if(totalAmount >= maxAmount){

                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                        plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                        plugin.savePlayerData();

                                        plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                        plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                        if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                        }

                                        List<String> cmdList = new ArrayList<>();

                                        for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                            cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                        }

                                        for(String cmd : cmdList){
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                        }


                                    }else{
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                        plugin.savePlayerData();

                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerCraftTwo(CraftItemEvent event) {

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            for (String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)) {

                int misions = Integer.parseInt(misionsList);

                if (MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.ITEMS_CRAFT) {
                    if (plugin.activeChallenge.containsKey(misions)) {
                        if (plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED) {

                            String[] requiredItems = plugin.getConfig().getString("MISIONS." + misions + ".VALUE").split(";");
                            String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS." + misions + ".CURRENT";

                            if (!plugin.getPlayerData().contains(playerDataMisionString)) {
                                plugin.getPlayerData().createSection(playerDataMisionString);
                            }

                            String craftedItem = event.getRecipe().getResult().getType().toString().toUpperCase();
                            boolean itemCrafted = Arrays.asList(requiredItems).contains(craftedItem);

                            if (itemCrafted) {
                                if (!plugin.getPlayerData().getConfigurationSection(playerDataMisionString).contains(craftedItem)) {
                                    plugin.getPlayerData().set(playerDataMisionString + "." + craftedItem, true);
                                    plugin.savePlayerData();

                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(getCompletedItemsCount(player, misions), requiredItems.length))));

                                    if (getCompletedItemsCount(player, misions) == requiredItems.length) {
                                        completeMission(player, misions);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private int getCompletedItemsCount(Player player, int misions) {
        String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS." + misions + ".CURRENT";
        return plugin.getPlayerData().getConfigurationSection(playerDataMisionString).getKeys(false).size();
    }

    private void completeMission(Player player, int misions) {
        String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS." + misions;

        plugin.getPlayerData().set(playerDataCompletedMisionString + ".DATE", System.currentTimeMillis());
        plugin.savePlayerData();

        plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());
        plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

        if (plugin.getConfig().getString("MISIONS." + misions + ".COMPLETED.MESSAGE") != null) {
            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS." + misions + ".COMPLETED.MESSAGE"))));
        }

        List<String> cmdList = new ArrayList<>();
        for (String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")) {
            cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
        }

        for (String cmd : cmdList) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
    }


    @EventHandler
    public void onPlayerJoinRegion(PlayerMoveEvent event){

        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if(player.getPlayer().isDead()){
            return;
        }

        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

            int misions = Integer.parseInt(misionsList);

            if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.REGION_JOIN){
                if(plugin.activeChallenge.containsKey(misions)){
                    if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(to.getWorld()));

                        for (ProtectedRegion region : regionManager.getRegions().values()) {

                            if(region.getId().equalsIgnoreCase(plugin.getConfig().getString("MISIONS." + misions + ".VALUE"))){

                                if (region.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()) || region.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
                                    String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                    String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                    if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                        plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                        plugin.savePlayerData();

                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                    }

                                    if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                        int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                        int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                        if(totalAmount >= maxAmount){

                                            plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                            plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                            plugin.savePlayerData();

                                            plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                            plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                            if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                                player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                            }

                                            List<String> cmdList = new ArrayList<>();

                                            for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                                cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                            }

                                            for(String cmd : cmdList){
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                            }


                                        }else{
                                            plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                            plugin.savePlayerData();

                                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerLeftRegion(PlayerMoveEvent event){

        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if(player.getPlayer().isDead()){
            return;
        }

        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        for(String misionsList : plugin.getConfig().getConfigurationSection("MISIONS").getKeys(false)){

            int misions = Integer.parseInt(misionsList);

            if(MisionType.valueOf(plugin.getConfig().getString("MISIONS." + misions + ".TYPE")) == MisionType.REGION_LEFT){
                if(plugin.activeChallenge.containsKey(misions)){
                    if(plugin.activeChallenge.get(misions).getChallengeStatus() == MisionStatus.STARTED){

                        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(to.getWorld()));

                        for (ProtectedRegion region : regionManager.getRegions().values()) {

                            if(region.getId().equalsIgnoreCase(plugin.getConfig().getString("MISIONS." + misions + ".VALUE"))){

                                if (region.contains(from.getBlockX(), from.getBlockY(), from.getBlockZ())) {
                                    if(!(region.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()))){

                                        String playerDataMisionString = "PLAYERDATA." + player.getName() + ".MISIONS.";

                                        String playerDataCompletedMisionString = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS.";

                                        if(!(plugin.getPlayerData().contains(playerDataMisionString + misions))){
                                            plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", 0);
                                            plugin.getPlayerData().set(playerDataMisionString + misions + ".MAX", plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT"));

                                            plugin.savePlayerData();

                                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                        }

                                        if(!(plugin.getPlayerData().contains(playerDataCompletedMisionString + misions))){

                                            int maxAmount = plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT");

                                            int totalAmount = plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT") + 1;

                                            if(totalAmount >= maxAmount){

                                                plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", maxAmount);

                                                plugin.getPlayerData().set(playerDataCompletedMisionString + misions + ".DATE", System.currentTimeMillis());

                                                plugin.savePlayerData();

                                                plugin.activeChallenge.get(misions).addChallengePlayerCompleted(player.getName());

                                                plugin.activeChallenge.get(misions).removeChallengePlayerParticipants(player.getName());

                                                if(plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE") != null){
                                                    player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("MISIONS."+misions+".COMPLETED.MESSAGE"))));
                                                }

                                                List<String> cmdList = new ArrayList<>();

                                                for(String cmd : plugin.getConfig().getStringList("MISIONS." + misions + ".COMPLETED.COMMANDS")){
                                                    cmdList.add(PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName())));
                                                }

                                                for(String cmd : cmdList){
                                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                                }


                                            }else{
                                                plugin.getPlayerData().set(playerDataMisionString + misions + ".CURRENT", totalAmount);

                                                plugin.savePlayerData();

                                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate(plugin.getConfig().getString("MISIONS." + misions + ".DISPLAY_NAME") + " " + Bar.bar(plugin.getPlayerData().getInt(playerDataMisionString + misions + ".CURRENT"), plugin.getConfig().getInt("MISIONS." + misions + ".AMOUNT")))));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
