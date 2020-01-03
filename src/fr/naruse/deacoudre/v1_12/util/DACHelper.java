package fr.naruse.deacoudre.v1_12.util;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DACHelper {
    private static void addPlayerPoints(OfflinePlayer p, long points){
        if(playerPoints.containsKey(p)){
            playerPoints.remove(p);
        }
        playerPoints.put(p, points);
    }

    private static void addPlayers(){
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
            if(getPlayerStatistics(p).getWins() != 0){
                addPlayerPoints(p, getPlayerStatistics(p).getWins());
            }
        }
    }

    private static HashMap<OfflinePlayer, PlayerStatistics> spleefPlayerHashMap = new HashMap<>();
    public static PlayerStatistics getPlayerStatistics(OfflinePlayer p) {
        if(spleefPlayerHashMap.containsKey(p)){
            spleefPlayerHashMap.get(p).refreshStatisticFromConfig();
            return spleefPlayerHashMap.get(p);
        }
        PlayerStatistics statistics = new PlayerStatistics(DacPluginV1_12.INSTANCE, p.getName());
        spleefPlayerHashMap.put(p, statistics);
        return statistics;
    }

    private static List<Long> intList = Lists.newArrayList();
    private static List<String> nameUsed = Lists.newArrayList();
    private static HashMap<OfflinePlayer, Long> playerPoints = new HashMap<>();
    public static List<OfflinePlayer> getPlayerRank(int place){
        addPlayers();
        HashMap<Long, List<OfflinePlayer>> pAndP = new HashMap<>();
        for(OfflinePlayer p : playerPoints.keySet()){
            if(!nameUsed.contains(p.getName())){
                long lives = playerPoints.get(p);
                intList.add(lives);
                if(!pAndP.containsKey(lives)){
                    pAndP.put(lives, Lists.newArrayList());
                }
                pAndP.get(lives).add(p);
                nameUsed.add(p.getName());
            }
        }
        Collections.sort(intList);
        nameUsed.clear();
        HashMap<Long, List<OfflinePlayer>> placeAndPlayer = new HashMap<>();
        for(long i : intList){
            placeAndPlayer.put(i, pAndP.get(i));
        }
        int count = 0;
        for(long i : placeAndPlayer.keySet()){
            if((count+1) == place){
                if(placeAndPlayer.containsKey(i)){
                    return placeAndPlayer.get(i);
                }
                break;
            }
            count++;
        }
        return null;
    }
}
