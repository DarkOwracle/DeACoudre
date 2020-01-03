package fr.naruse.deacoudre.v1_12.util;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NPCStatistics implements Listener {
    private DacPluginV1_12 pl;
    private Location location;
    private NPC npc;
    private NPC firstNpc;
    private NPC secondNpc;
    private NPC thirdNpc;
    private String firstPlayer;
    private String secondPlayer;
    private String thirdPlayer;
    public NPCStatistics(DacPluginV1_12 dacPlugin, Location loc) {
        this.pl = dacPlugin;
        this.location = loc;
        Bukkit.getScheduler().scheduleSyncDelayedTask(dacPlugin.getDacPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Iterator<NPC> it = CitizensAPI.getNPCRegistry().iterator(); it.hasNext(); ) {
                    NPC npc = it.next();
                    if(npc != npc && npc != firstNpc && npc != secondNpc && npc != thirdNpc){
                        npc.despawn();
                        System.out.println(npc);
                    }
                }
            }
        }, 20*5);
    }

    public void spawn(){
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§3NaruseII");
        npc.spawn(location);
        runnable();
        refreshRanking();
        if(pl.getConfig().getString("dac.firstNpc.x") != null){
            try{
                Location loc = new Location(Bukkit.getWorld(pl.getConfig().getString("dac.firstNpc.world")),
                        pl.getConfig().getDouble("dac.firstNpc.x"), pl.getConfig().getDouble("dac.firstNpc.y"),
                        pl.getConfig().getDouble("dac.firstNpc.z"), pl.getConfig().getInt("dac.firstNpc.yaw"),
                        pl.getConfig().getInt("dac.firstNpc.pitch"));
                firstNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§3NaruseII");
                firstNpc.spawn(loc);
            }catch (Exception e){
                System.out.println("[DeACoudre] FirstPlayerNPC operates on an untraceable world.");
            }
        }
        if(pl.getConfig().getString("dac.secondNpc.x") != null){
            try{
                Location loc = new Location(Bukkit.getWorld(pl.getConfig().getString("dac.secondNpc.world")),
                        pl.getConfig().getDouble("dac.secondNpc.x"), pl.getConfig().getDouble("dac.secondNpc.y"),
                        pl.getConfig().getDouble("dac.secondNpc.z"), pl.getConfig().getInt("dac.secondNpc.yaw"),
                        pl.getConfig().getInt("dac.secondNpc.pitch"));
                secondNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§3NaruseII");
                secondNpc.spawn(loc);
            }catch (Exception e){
                System.out.println("[DeACoudre] SecondPlayerNPC operates on an untraceable world.");
            }
        }
        if(pl.getConfig().getString("dac.thirdNpc.x") != null){
            try{
                Location loc = new Location(Bukkit.getWorld(pl.getConfig().getString("dac.thirdNpc.world")),
                        pl.getConfig().getDouble("dac.thirdNpc.x"), pl.getConfig().getDouble("dac.thirdNpc.y"),
                        pl.getConfig().getDouble("dac.thirdNpc.z"), pl.getConfig().getInt("dac.thirdNpc.yaw"),
                        pl.getConfig().getInt("dac.thirdNpc.pitch"));
                thirdNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§3NaruseII");
                thirdNpc.spawn(loc);
            }catch (Exception e){
                System.out.println("[DeACoudre] ThirdPlayerNPC operates on an untraceable world.");
            }
        }
    }

    public void refreshRanking() {
        HashMap<Long, List<String>> pAndP = new HashMap<>();
        List<Long> list = Lists.newArrayList();
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
            PlayerStatistics playerStatistics = new PlayerStatistics(pl, p.getName());
            long points = playerStatistics.getPerfects()+playerStatistics.getWins()+playerStatistics.getGames();
            points -= playerStatistics.getFails()+playerStatistics.getLoses();
            list.add(points);
            if(!pAndP.containsKey(points)){
                pAndP.put(points, Lists.newArrayList());
            }
            pAndP.get(points).add(p.getName());
        }
        Collections.sort(list);
        HashMap<Long, List<String>> placeAndPlayer = new HashMap<>();
        for(long i : list){
            placeAndPlayer.put(i, pAndP.get(i));
        }
        int count = 0;
        for(long i : placeAndPlayer.keySet()){
            count++;
            for(String s : placeAndPlayer.get(i)){
                if(count == 0){
                    firstPlayer = s;
                }else if(count == 1){
                    secondPlayer = s;
                }else if(count == 2){
                    thirdPlayer = s;
                }
            }
        }
        if(firstNpc != null){
            if(firstPlayer == null){
                firstNpc.setName("§3Naruse");
            }else{
                firstNpc.setName("§6"+firstPlayer);
            }
        }
        if(secondNpc != null){
            if(secondPlayer == null){
                secondNpc.setName("§eNaruse");
            }else{
                secondNpc.setName("§e"+secondPlayer);
            }
        }
        if(thirdNpc != null){
            if(thirdPlayer == null){
                thirdNpc.setName("§dNaruse");
            }else{
                thirdNpc.setName("§d"+thirdPlayer);
            }
        }
    }

    private int count = 0;
    private int rankingCount = 55;
    private Player target = null;
    private Player firstTarget;
    private Player secondTarget;
    private Player thirdTarget;
    private void runnable(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getDacPlugin(), new Runnable() {
            @Override
            public void run() {
                if(count >= 20){
                    rankingCount++;
                    if(rankingCount == 60){
                        rankingCount = 0;
                        refreshRanking();
                    }
                    if(location.distance(npc.getEntity().getLocation()) >= 1.5){
                        npc.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                    double distance = 30.0;
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(npc.getEntity().getLocation().distance(p.getLocation()) <= distance){
                            distance = npc.getEntity().getLocation().distance(p.getLocation());
                            target = p;
                        }
                        if(firstNpc != null) if(firstNpc.getEntity().getLocation().distance(p.getLocation()) <= distance){
                            distance = firstNpc.getEntity().getLocation().distance(p.getLocation());
                            firstTarget = p;
                        }
                        if(secondNpc != null) if(secondNpc.getEntity().getLocation().distance(p.getLocation()) <= distance){
                            distance = secondNpc.getEntity().getLocation().distance(p.getLocation());
                            secondTarget = p;
                        }
                        if(thirdNpc != null) if(thirdNpc.getEntity().getLocation().distance(p.getLocation()) <= distance){
                            distance = thirdNpc.getEntity().getLocation().distance(p.getLocation());
                            thirdTarget = p;
                        }
                    }
                    if(target != null){
                        if(npc.getName() != target.getName()){
                            npc.setName(target.getName());
                        }
                    }
                    count = 0;
                }
                if(target != null){
                    npc.faceLocation(target.getLocation());
                }
                if(firstTarget != null){
                    if(firstNpc != null){
                        firstNpc.faceLocation(firstTarget.getLocation());
                    }
                }
                if(secondTarget != null){
                    if(secondNpc != null){
                        secondNpc.faceLocation(secondTarget.getLocation());
                    }
                }
                if(thirdTarget != null){
                    if(thirdNpc != null){
                        thirdNpc.faceLocation(thirdTarget.getLocation());
                    }
                }
                count++;
                runnable();
            }
        }, 1);
    }

    public void onDisable(){
        if(npc.isSpawned()){
            npc.destroy();
        }
        if(firstNpc != null){
            firstNpc.destroy();
        }
        if(secondNpc != null){
            secondNpc.destroy();
        }
        if(thirdNpc != null){
            thirdNpc.destroy();
        }
        for (Iterator<NPC> it = CitizensAPI.getNPCRegistry().iterator(); it.hasNext(); ) {
            NPC npc = it.next();
            npc.despawn();
            System.out.println(npc);
        }
    }

    @EventHandler
    public void click(NPCRightClickEvent e){
        if(e.getNPC() == npc){
            if(target != null){
                PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(target);
                playerStatistics.openInventory(e.getClicker());
            }
        }
        if(firstNpc != null){
            if(e.getNPC() == firstNpc){
                if(e.getNPC().getName().contains("§3")){
                    return;
                }
                String name = e.getNPC().getName().replace("§6", "").replace("§e", "").replace("§d", "");
                PlayerStatistics playerStatistics = new PlayerStatistics(pl, name);
                playerStatistics.openInventory(e.getClicker());
            }
        }
        if(secondNpc != null){
            if(e.getNPC() == secondNpc){
                if(e.getNPC().getName().contains("§3")){
                    return;
                }
                String name = e.getNPC().getName().replace("§6", "").replace("§e", "").replace("§d", "");
                PlayerStatistics playerStatistics = new PlayerStatistics(pl, name);
                playerStatistics.openInventory(e.getClicker());
            }
        }
        if(thirdNpc != null){
            if(e.getNPC() == thirdNpc){
                if(e.getNPC().getName().contains("§3")){
                    return;
                }
                String name = e.getNPC().getName().replace("§6", "").replace("§e", "").replace("§d", "");
                PlayerStatistics playerStatistics = new PlayerStatistics(pl, name);
                playerStatistics.openInventory(e.getClicker());
            }
        }
    }
}