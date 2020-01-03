package fr.naruse.deacoudre.manager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.naruse.deacoudre.common.configuration.Configurations;
import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.v1_13.cmd.DacCommands;
import fr.naruse.deacoudre.v1_13.dac.Dac;
import fr.naruse.deacoudre.v1_13.dac.Dacs;
import fr.naruse.deacoudre.v1_13.event.Listeners;
import fr.naruse.deacoudre.v1_13.util.*;
import fr.naruse.deacoudre.v1_13.util.leaderboard.Holograms;
import fr.naruse.deacoudre.v1_13.util.support.OtherPluginSupport;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DacPluginV1_13 extends AbstractDacPlugin {
    public DacPlugin pl;
    public Dacs dacs;
    public BlockChoice blockChoice;
    public NPCStatistics npcStatistics;
    public String language = "fr";
    public HashMap<Player, PlayerStatistics> statisticsOfPlayer = new HashMap<>();
    public WorldEditPlugin worldEditPlugin;
    public static DacPluginV1_13 INSTANCE;
    public Configurations configurations;
    public OtherPluginSupport otherPluginSupport;
    public Logs logs;
    public Holograms holograms;
    public DacPluginV1_13(DacPlugin pl) {
        super(pl);
        this.pl = pl;
    }

    @Override
    public void onEnable() {
        this.INSTANCE = this;
        saveConfig();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.getDacPlugin(), new Runnable() {
            @Override
            public void run() {
                logs = new Logs();
                Bukkit.getConsoleSender().sendMessage("§c§l[§3DAC§c§l] §c§l[§3Logs§c§l] §aEnabling DeACoudre...");
                configurations = new Configurations(INSTANCE.pl);
                worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
                language = getConfig().getString("language");
                dacs = new Dacs(INSTANCE);
                blockChoice = new BlockChoice();
                getCommand("dac").setExecutor(new DacCommands(INSTANCE));
                otherPluginSupport = new OtherPluginSupport();
                Bukkit.getPluginManager().registerEvents(new Listeners(INSTANCE), INSTANCE.getDacPlugin());
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    PlayerStatistics playerStatistics = new PlayerStatistics(INSTANCE, p.getName());
                    statisticsOfPlayer.put(p, playerStatistics);
                }
                init();
                logs.stop();
                holograms = new Holograms(INSTANCE);
                if((PlaceholderAPIPlugin) Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                    new DACPlaceHolder().register();
                }
            }
        });
    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            statisticsOfPlayer.get(p).saveStatistics();
        }
        for(Dac dac : dacs.getDacs()){
            dac.onDisable();
        }
        if(npcStatistics != null){
            npcStatistics.onDisable();
        }
        holograms.removeLeaderBoard();
    }

    @Override
    public void onLoad() { }

    public void reload(){
        for(Dac dac : dacs.getDacs()){
            dac.stop();
            dac.restart(true);
        }
        dacs = new Dacs(this);
    }


    private void init(){
        if(getConfig().getString("dac.npc.x") != null){
            if(Bukkit.getPluginManager().getPlugin("Citizens") != null){
                try{
                    Location loc = new Location(Bukkit.getWorld(getConfig().getString("dac.npc.world")),
                            getConfig().getDouble("dac.npc.x"), getConfig().getDouble("dac.npc.y"),
                            getConfig().getDouble("dac.npc.z"), getConfig().getInt("dac.npc.yaw"),
                            getConfig().getInt("dac.npc.pitch"));
                    double version = 0;
                    for(String args : Bukkit.getVersion().split(" ")){
                        if(args.contains("1.")){
                            version = Double.valueOf(args.replace(")", ""));
                            break;
                        }
                    }
                    if(version < 1.10 || version > 1.12){
                        System.out.println("[DeACoudre] NPCs work only on minecraft 1.10 to 1.12.");
                    }else{
                        this.npcStatistics = new NPCStatistics(this, loc);
                        Bukkit.getPluginManager().registerEvents(npcStatistics, this.getDacPlugin());
                        this.npcStatistics.spawn();
                    }
                }catch (Exception e){
                    System.out.println("[DeACoudre] PlayerNPC operates on an untraceable world.");
                }
            }else{
                System.out.println("[DeACoudre] PlayerNPC needs Citizens to work.");
            }
        }else{
            System.out.println("[DeACoudre] PlayerNPC wasn't configured.");
        }
    }
}
