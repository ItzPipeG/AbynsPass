package dev.itzpipeg.abynspass;

import dev.itzpipeg.abynspass.commands.PassCommand;
import dev.itzpipeg.abynspass.events.Join;
import dev.itzpipeg.abynspass.events.MenuClickEvent;
import dev.itzpipeg.abynspass.events.MisionsEvent;
import dev.itzpipeg.abynspass.models.ChallengeModel;
import dev.itzpipeg.abynspass.placeholders.Placeholder;
import dev.itzpipeg.abynspass.utils.CC;
import dev.itzpipeg.abynspass.utils.MisionStatus;
import dev.itzpipeg.abynspass.utils.MisionType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {


    public String RootConfig;

    private FileConfiguration playerdata = null;
    private File playerdataFile = null;

    public Map<Integer, ChallengeModel> activeChallenge = new HashMap<>();

    @Override
    public void onEnable(){

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholder(this).register();
        }

        registerConfig();
        registerPlayerData();
        setupEvents();
        setupCommands();
    }

    private void setupCommands() {
        getCommand("pass").setExecutor(new PassCommand(this));
    }

    public void setupEvents(){
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new MisionsEvent(this), this);
        getServer().getPluginManager().registerEvents(new MenuClickEvent(), this);
    }

    public void registerConfig(){
        File config = new File(this.getDataFolder(),"config.yml");
        RootConfig = config.getPath();
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public FileConfiguration getPlayerData(){
        if(playerdata == null){
            reloadPlayerData();
        }
        return playerdata;
    }

    public void reloadPlayerData(){
        if(playerdata == null){
            playerdataFile = new File(getDataFolder() + File.separator + "playerdata", "playerdata.yml");
        }
        playerdata = YamlConfiguration.loadConfiguration(playerdataFile);
        Reader defConfigStream;
        try{
            defConfigStream = new InputStreamReader(this.getResource("playerdata.yml"),"UTF8");
            if(defConfigStream != null){
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                playerdata.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public void savePlayerData(){
        try{
            playerdata.save(playerdataFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerPlayerData(){
        playerdataFile = new File(this.getDataFolder(), "playerdata.yml");
        if(!playerdataFile.exists()){
            this.getPlayerData().options().copyDefaults(true);
            savePlayerData();
        }
    }

    public void createPlayerData(String playerName){

        if(getPlayerData().getString("PLAYERDATA." + playerName) == null){

            getPlayerData().createSection("PLAYERDATA." + playerName);

            savePlayerData();
        }
    }

    public void startNewChallenge(Integer id, List<String> challengeParticipants){

        if(getConfig().getString("MISIONS." + id) != null){

            MisionType challengeType = MisionType.valueOf(getConfig().getString("MISIONS." + id + ".TYPE"));

            String challengeValue = getConfig().getString("MISIONS." + id + ".VALUE");

            Integer challengeAmount = getConfig().getInt("MISIONS." + id + ".AMOUNT");

            ChallengeModel challengeModel = new ChallengeModel(challengeParticipants, new ArrayList<>(), challengeType, challengeValue, challengeAmount, System.currentTimeMillis(), MisionStatus.STARTED);

            List<String> cmdList = getConfig().getStringList("MISIONS." + id + ".STARTED.COMMANDS");

            for (String playersName : challengeParticipants) {
                Player player = Bukkit.getPlayer(playersName);

                if (player != null) {
                    if (getConfig().getString("MISIONS." + id + ".STARTED.MESSAGE", null) != null) {
                        player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, getConfig().getString("MISIONS." + id + ".STARTED.MESSAGE"))));
                    }

                    if (getConfig().getString("MISIONS." + id + ".STARTED.SOUND", null) != null) {
                        player.playSound(player.getLocation(), Sound.valueOf(getConfig().getString("MISIONS." + id + ".STARTED.SOUND")), 10, 1);
                    }

                    for (String cmd : cmdList) {
                        String parsedCmd = PlaceholderAPI.setPlaceholders(player, cmd.replaceAll("\\{player}", player.getName()));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsedCmd);
                    }
                }
            }

            activeChallenge.put(Integer.valueOf(id), challengeModel);
        }
    }

    public void stopChallenge(Integer id){

        if(activeChallenge.containsKey(id)){
            activeChallenge.get(id).setChallengeStatus(MisionStatus.NONE);

            for(Player players : Bukkit.getOnlinePlayers()){
                if(getConfig().getString("MISIONS."+id+".FINISHED.MESSAGE", null) != null){
                    players.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(players, getConfig().getString("MISIONS."+id+".FINISHED.MESSAGE"))));
                }

                if(getConfig().getString("MISIONS."+id+".FINISHED.SOUND", null) != null){
                    players.playSound(players.getLocation(), Sound.valueOf(getConfig().getString("MISIONS."+id+".FINISHED.SOUND")), 10, 1);
                }


                List<String> cmdList = new ArrayList<>();

                for(String cmd : getConfig().getStringList("MISIONS." + id + ".FINISHED.COMMANDS")){
                    cmdList.add(PlaceholderAPI.setPlaceholders(players, cmd.replaceAll("\\{players}", players.getName())));
                }

                for(String cmd : cmdList){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                }
            }

            activeChallenge.remove(id);
        }
    }
}