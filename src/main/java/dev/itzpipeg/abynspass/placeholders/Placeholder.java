package dev.itzpipeg.abynspass.placeholders;

import dev.itzpipeg.abynspass.Main;
import dev.itzpipeg.abynspass.utils.Bar;
import dev.itzpipeg.abynspass.utils.CC;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Placeholder extends PlaceholderExpansion {


    private Main plugin;
    public Placeholder(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "abynspass";
    }

    @Override
    public String getAuthor() {
        return "itzpipe";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params){

        if (params.startsWith("mission_actual")) {

            if (!plugin.activeChallenge.isEmpty()) {
                Integer minKey = null;
                for (Integer key : plugin.activeChallenge.keySet()) {
                    if (minKey == null || key < minKey) {
                        minKey = key;
                    }
                }

                if (minKey != null) {
                    String mission = plugin.getConfig().getString("MISIONS." + minKey + ".DISPLAY_NAME");

                    return CC.translate(mission);
                }
            }else{

                return "No active";
            }
        }

        if (params.startsWith("mission_completed")) {

            String completedMissionsPath = "PLAYERDATA." + player.getName() + ".COMPLETED_MISIONS";

            if (plugin.getPlayerData().contains("PLAYERDATA." + player.getName())) {

                ConfigurationSection completedMissionsSection = plugin.getPlayerData().getConfigurationSection(completedMissionsPath);
                if (completedMissionsSection != null) {
                    int completedMissionsCount = completedMissionsSection.getKeys(false).size();
                    return String.valueOf(completedMissionsCount);
                } else {

                    return "0";
                }
            } else {
                return "0";
            }
        }

        if (params.startsWith("mission_bar")) {

            if (!plugin.activeChallenge.isEmpty()) {
                Integer minKey = null;
                for (Integer key : plugin.activeChallenge.keySet()) {
                    if (minKey == null || key < minKey) {
                        minKey = key;
                    }
                }

                if (minKey != null) {

                    Double current = plugin.getPlayerData().getDouble("PLAYERDATA." + player.getName() + ".MISIONS." + minKey + ".CURRENT");

                    Double max = plugin.getPlayerData().getDouble("PLAYERDATA." + player.getName() + ".MISIONS." + minKey + ".MAX");

                    return Bar.bar(current, max);

                }
            }else{
                return "No active";
            }
        }

        return null;
    }
}
