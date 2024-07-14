package dev.itzpipeg.abynspass.events;

import dev.itzpipeg.abynspass.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Join implements Listener {

    @EventHandler

    public void onJoin(PlayerJoinEvent event){

        Main.getPlugin(Main.class).createPlayerData(event.getPlayer().getName());

    }
}
