package dev.itzpipeg.abynspass.commands;

import dev.itzpipeg.abynspass.Main;
import dev.itzpipeg.abynspass.menus.MisionListInv;
import dev.itzpipeg.abynspass.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PassCommand implements CommandExecutor {


    private Main plugin;

    public PassCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("abynspass.admin") || sender.hasPermission("*")){
            if(args.length > 0){

                String subCommand = args[0].toLowerCase();

                if(subCommand.equals("panel")) {

                    Player player = (Player) sender;

                    new MisionListInv(plugin).openInventory(player);

                }

                if(subCommand.equals("start")) {

                    try {
                        if(args.length >= 2){

                            CommandSender player = sender;

                            if(args[2].equals("all")){

                                if(!(plugin.activeChallenge.containsKey(Integer.parseInt(args[1])))){

                                    if(plugin.getConfig().getString("MISIONS." + args[1] + ".GROUP", null) != null){

                                        if(plugin.getConfig().getString("MISIONS." + args[1] + ".GROUP").equalsIgnoreCase("GENERAL")){
                                            List<String> playerList = new ArrayList<>();

                                            for(Player playerNicks : Bukkit.getOnlinePlayers()) {
                                                playerList.add(playerNicks.getName());
                                            }

                                            plugin.startNewChallenge(Integer.parseInt(args[1]), playerList);

                                            player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fEl evento con id &e" + args[1] + " &fha sido iniciado para todos los jugadores."));
                                        }else{

                                            player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fLa misión con id &E" + args[1] + " &fno se puede iniciar, porque es de tipo &ePLAYER&f."));
                                        }
                                    }
                                }else{
                                    player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fEse evento ya se encuentra activo!"));

                                }

                            }else{

                                if(plugin.getPlayerData().getString("PLAYERDATA." + args[2]) != null){

                                    if(!(plugin.activeChallenge.containsKey(Integer.parseInt(args[1])))){

                                        if(plugin.getConfig().getString("MISIONS." + args[1] + ".GROUP", null) != null) {

                                            if (plugin.getConfig().getString("MISIONS." + args[1] + ".GROUP").equalsIgnoreCase("PLAYER")) {


                                                List<String> playerList = new ArrayList<>();

                                                playerList.add(args[2]);

                                                plugin.startNewChallenge(Integer.parseInt(args[1]), playerList);

                                                player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fEl evento con id &e" + args[1] + " &fha sido iniciado para el jugador &e" + args[2]));

                                            }else{
                                                player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fLa misión con id &E" + args[1] + " &fno se puede iniciar, porque es de tipo &eGENERAL&f."));

                                            }
                                        }

                                    }else{

                                        player.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fEse evento ya se encuentra activo!"));

                                    }
                                }
                            }

                        }else{
                            sender.sendMessage(" /"+label+  " start <number> <player/all>");
                        }

                    }catch (NullPointerException e){

                        sender.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fLa misión con ese número no se encuentra disponible."));
                    }
                }else if(subCommand.equals("stop")) {

                    try {

                        if(args.length >= 2){

                            if(plugin.activeChallenge.containsKey(Integer.parseInt(args[1]))){

                                plugin.stopChallenge(Integer.parseInt(args[1]));

                                sender.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fDetenido el challenge con id &e" + args[1] + " &fcorrectamente."));

                            }else{
                                sender.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fEsa misión no se encuentra activa."));
                            }

                        }else{
                            sender.sendMessage(" /"+label+  " stop <number>");
                        }

                    }catch (NullPointerException e){

                        sender.sendMessage(CC.translate(" &e&lABYNSPASS &e↳ &fLa misión con ese número no se encuentra disponible."));
                    }


                }

            }else{

                sender.sendMessage(CC.translate("&r "));

                sender.sendMessage(CC.translate(" &e&lABYNSPASS &fv"+ plugin.getDescription().getVersion()));

                sender.sendMessage(CC.translate("&r "));

                sender.sendMessage(CC.translate("&r &e/pass panel &7- &fAbrir el panel de misiones."));

                sender.sendMessage(CC.translate("&r "));

                sender.sendMessage(CC.translate("&r &e/pass start <number> &7- &fIniciar una misión."));

                sender.sendMessage(CC.translate("&r &e/pass stop <number> &7- &fDenete una misión."));

                sender.sendMessage(CC.translate("&r "));

            }
        }


        return true;
    }
}
