package fr.naruse.deacoudre.v1_12.dac;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.common.DacPlayer;
import fr.naruse.deacoudre.v1_12.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class Dacs {
    private List<Dac> dacs = Lists.newArrayList();
    private HashMap<Player, Dac> dacOfPlayer = new HashMap<>();
    private HashMap<Player, DacPlayer> dacPlayerOfPlayer = new HashMap<>();
    private DacPluginV1_12 pl;
    private List<Sign> signsTaken = Lists.newArrayList();
    public Dacs(DacPluginV1_12 pl) {
        this.pl = pl;
        int count = 0;
        for(int i = 0; i != 999; i++){
            if(pl.getConfig().getString("dac."+i+".name") != null){
                String name = pl.getConfig().getString("dac."+i+".name");
                if(pl.getConfig().getString("dac."+i+".diving.x") != null){
                    if(pl.getConfig().getString("dac."+i+".pool.x") != null){
                        if(pl.getConfig().getString("dac."+i+".diving.x") != null){
                            if(pl.getConfig().getString("dac."+i+".end.x") != null){
                                if(pl.getConfig().getString("dac."+i+".lobby.x") != null){
                                    if(pl.getConfig().getString("dac."+i+".min") != null){
                                        if(pl.getConfig().getString("dac."+i+".max") != null){
                                            try{
                                                Location diving = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".diving.world")),
                                                        pl.getConfig().getDouble("dac."+i+".diving.x"), pl.getConfig().getDouble("dac."+i+".diving.y"),
                                                        pl.getConfig().getDouble("dac."+i+".diving.z"), pl.getConfig().getInt("dac."+i+".diving.yaw"),
                                                        pl.getConfig().getInt("dac."+i+".diving.pitch"));
                                                Location pool = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".pool.world")),
                                                        pl.getConfig().getDouble("dac."+i+".pool.x"), pl.getConfig().getDouble("dac."+i+".pool.y"),
                                                        pl.getConfig().getDouble("dac."+i+".pool.z"), pl.getConfig().getInt("dac."+i+".pool.yaw"),
                                                        pl.getConfig().getInt("dac."+i+".pool.pitch"));
                                                Location end = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".end.world")),
                                                        pl.getConfig().getDouble("dac."+i+".end.x"), pl.getConfig().getDouble("dac."+i+".end.y"),
                                                        pl.getConfig().getDouble("dac."+i+".end.z"), pl.getConfig().getInt("dac."+i+".end.yaw"),
                                                        pl.getConfig().getInt("dac."+i+".end.pitch"));
                                                Location lobby = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".lobby.world")),
                                                        pl.getConfig().getDouble("dac."+i+".lobby.x"), pl.getConfig().getDouble("dac."+i+".lobby.y"),
                                                        pl.getConfig().getDouble("dac."+i+".lobby.z"), pl.getConfig().getInt("dac."+i+".lobby.yaw"),
                                                        pl.getConfig().getInt("dac."+i+".lobby.pitch"));
                                                Location a = null, b = null;
                                                if(pl.getConfig().getString("dac."+i+".region.a.x") != null && pl.getConfig().getString("dac."+i+".region.b.x") != null){
                                                    a = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".region.a.world")),
                                                            pl.getConfig().getDouble("dac."+i+".region.a.x"), pl.getConfig().getDouble("dac."+i+".region.a.y"),
                                                            pl.getConfig().getDouble("dac."+i+".region.a.z"));
                                                    b = new Location(Bukkit.getWorld(pl.getConfig().getString("dac."+i+".region.b.world")),
                                                            pl.getConfig().getDouble("dac."+i+".region.b.x"), pl.getConfig().getDouble("dac."+i+".region.b.y"),
                                                            pl.getConfig().getDouble("dac."+i+".region.b.z"));
                                                }
                                                int max = pl.getConfig().getInt("dac."+i+".max");
                                                int min = pl.getConfig().getInt("dac."+i+".min");
                                                boolean isOpen = pl.getConfig().getBoolean("dac."+i+".isOpen");
                                                Dac dac = new Dac(pl, name, diving, pool, end, lobby, min, max, isOpen).buildRegion(a, b);
                                                Bukkit.getPluginManager().registerEvents(dac, pl.getDacPlugin());
                                                dacs.add(dac);
                                                count++;
                                            }catch (Exception e){
                                                Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_WORLD.getMessage().replace("**", name));
                                            }
                                        }else{
                                            Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_LIMIT.getMessage().replace("**", name));
                                        }
                                    }else{
                                        Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_LIMIT.getMessage().replace("**", name));
                                    }
                                }else{
                                    Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_NEED.getMessage().replace("**", name));
                                }
                            }else{
                                Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_LOBBY.getMessage().replace("**", name));
                            }
                        }else{
                            Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_END.getMessage().replace("**", name));
                        }
                    }else{
                        Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_POOL.getMessage().replace("**", name));
                    }
                }else{
                    Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §c"+Message.WITHOUT_DIVING.getMessage().replace("**", name));
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §a"+count+" arenas found.");
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getDacPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Dac dac : dacs){
                    for(World world : Bukkit.getWorlds()){
                        dac.registerNewSigns(world);
                    }
                }
            }
        }, 20);
    }

    public void addPlayer(Player p, Dac dac){
        if(!dac.isOpen()){
            p.sendMessage("§c"+Message.ARENA_CLOSED.getMessage());
            return;
        }
        if(!dacOfPlayer.containsKey(p)){
            //if(pl.commonPlugin != null){
                //if(pl.commonPlugin.isInGame(p)){
                   // p.sendMessage("§c"+Message.YOU_HAVE_A_GAME.getMessage());
                    //return;
                //}
            //}
            DacPlayer dacPlayer = new DacPlayer(p);
            dacPlayer.registerInventory();
            dacPlayer.registerGameMode();
            dacPlayerOfPlayer.put(p, dacPlayer);
            if(dac.addPlayer(p)){
                dacOfPlayer.put(p, dac);
            }
        }else{
            p.sendMessage("§c"+Message.YOU_HAVE_A_GAME.getMessage());
        }
    }

    public void removePlayer(Player p){
        if(pl.statisticsOfPlayer.containsKey(p)){
            pl.statisticsOfPlayer.get(p).saveStatistics();
        }
        if(dacOfPlayer.containsKey(p)){
            dacOfPlayer.get(p).removePlayer(p);
            dacOfPlayer.remove(p);
            if(dacPlayerOfPlayer.containsKey(p)){
                DacPlayer dacPlayer = dacPlayerOfPlayer.get(p);
                dacPlayer.setPlayerInventory();
                dacPlayer.setPlayerGameMode();
            }
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public List<Dac> getDacs() {
        return dacs;
    }

    public List<Sign> getSignsTaken() {
        return signsTaken;
    }

    public HashMap<Player, Dac> getDacOfPlayer() {
        return dacOfPlayer;
    }
}
