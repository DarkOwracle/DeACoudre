package fr.naruse.deacoudre.v1_12.util;

import fr.naruse.deacoudre.main.DacPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class DACPlaceHolder extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "dac";
    }

    @Override
    public String getAuthor() {
        return "naruse";
    }

    @Override
    public String getVersion() {
        return DacPlugin.INSTANCE.getDescription().getVersion();
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return this.onRequest(p, params);
    }

    @Override
    public String onRequest(OfflinePlayer p, String identifier) {
        //RANK
        if(identifier.equals("first")){
            List<OfflinePlayer> players = DACHelper.getPlayerRank(1);
            if(players == null){
                return super.onRequest(p, identifier);
            }
            String name = ",";
            for(OfflinePlayer player : players){
                name += ", "+player.getName();
            }
            return name.replace(",, ", "");
        }
        if(identifier.equals("second")){
            List<OfflinePlayer> players = DACHelper.getPlayerRank(2);
            if(players == null){
                return super.onRequest(p, identifier);
            }
            String name = ",";
            for(OfflinePlayer player : players){
                name += ", "+player.getName();
            }
            return name.replace(",, ", "");
        }
        if(identifier.equals("third")){
            List<OfflinePlayer> players = DACHelper.getPlayerRank(3);
            if(players == null){
                return super.onRequest(p, identifier);
            }
            String name = ",";
            for(OfflinePlayer player : players){
                name += ", "+player.getName();
            }
            return name.replace(",, ", "");
        }
        if(identifier.equals("fourth")){
            List<OfflinePlayer> players = DACHelper.getPlayerRank(4);
            if(players == null){
                return super.onRequest(p, identifier);
            }
            String name = ",";
            for(OfflinePlayer player : players){
                name += ", "+player.getName();
            }
            return name.replace(",, ", "");
        }
        if(identifier.equals("fifth")){
            List<OfflinePlayer> players = DACHelper.getPlayerRank(5);
            if(players == null){
                return super.onRequest(p, identifier);
            }
            String name = ",";
            for(OfflinePlayer player : players){
                name += ", "+player.getName();
            }
            return name.replace(",, ", "");
        }
        //WINS
        if(identifier.equals("wins")){
            PlayerStatistics statistics = DACHelper.getPlayerStatistics(p);
            return statistics.getWins()+"";
        }
        //LOSES
        if(identifier.equals("loses")){
            PlayerStatistics statistics = DACHelper.getPlayerStatistics(p);
            return statistics.getLoses()+"";
        }
        return null;
    }
}
