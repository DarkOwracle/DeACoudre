package fr.naruse.deacoudre.manager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.v1_12.cmd.DacCommands;
import fr.naruse.deacoudre.v1_12.dac.Dac;
import fr.naruse.deacoudre.v1_12.dac.Dacs;
import fr.naruse.deacoudre.v1_12.event.Listeners;
import fr.naruse.deacoudre.v1_12.util.*;
import fr.naruse.deacoudre.common.configuration.Configurations;
import fr.naruse.deacoudre.v1_12.util.manager.Manager;
import fr.naruse.deacoudre.v1_12.util.support.OtherPluginSupport;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DacPluginV1_12 extends AbstractDacPlugin {
    public DacPlugin pl;
    public Dacs dacs;
    public Manager manager = null;
    public BlockChoice blockChoice;
    public NPCStatistics npcStatistics;
    public String language = "fr";
    public HashMap<Player, PlayerStatistics> statisticsOfPlayer = new HashMap<>();
    public WorldEditPlugin worldEditPlugin;
    public static DacPluginV1_12 INSTANCE;
    public Configurations configurations;
    public OtherPluginSupport otherPluginSupport;
    public Logs logs;
    //public CommonPlugin commonPlugin;
    public DacPluginV1_12(DacPlugin pl) {
        super(pl);
        this.pl = pl;
    }

    @Override
    public void onEnable() {
        this.INSTANCE = this;
        saveConfig();
        new HuntiesRunnable().runTaskTimer(pl, 20, 20);
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
                //holograms = new Holograms(INSTANCE);
                //commonPlugin = (CommonPlugin) Bukkit.getPluginManager().getPlugin("NaruseResourcesCommon");
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
        if(manager != null){
            //manager.onDisable();
        }
        if(npcStatistics != null){
            npcStatistics.onDisable();
        }
        //holograms.removeLeaderBoard();
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
        if(getConfig().getString("dac.manager.x") != null){
            try{
                Location loc = new Location(Bukkit.getWorld(getConfig().getString("dac.manager.world")),
                        getConfig().getDouble("dac.manager.x"), getConfig().getDouble("dac.manager.y"),
                        getConfig().getDouble("dac.manager.z"), getConfig().getInt("dac.manager.yaw"),
                        getConfig().getInt("dac.manager.pitch"));
                this.manager = new Manager(this, loc);
                Bukkit.getPluginManager().registerEvents(manager, this.getDacPlugin());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("[DeACoudre] Le Manager est mal configure.");
            }
        }else{
            System.out.println("[DeACoudre] Le Manager n'est pas configure.");
        }
    }

    class HuntiesRunnable extends BukkitRunnable{

        @Override
        public void run() {
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getWorld().getName().equalsIgnoreCase("Event")){
                    if(p.getInventory().contains(Material.CHEST)){
                        p.getInventory().clear();
                    }
                    if(p.isFlying() && p.getGameMode() != GameMode.CREATIVE){
                        p.setFlying(false);
                        p.setAllowFlight(false);
                    }
                }
                //if(commonPlugin != null){
                    //if(!commonPlugin.isInGame(p)){
                        p.setFoodLevel(20);
                        if(p.getGameMode() == GameMode.SURVIVAL){
                            p.setGameMode(GameMode.ADVENTURE);
                        }
                    //}
                //}
            }
        }
    }
}
