package fr.naruse.deacoudre.v1_13.cmd;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import fr.naruse.deacoudre.manager.DacPluginV1_13;
import fr.naruse.deacoudre.v1_13.dac.Dac;
import fr.naruse.deacoudre.v1_13.util.Message;
import fr.naruse.deacoudre.v1_13.util.PlayerStatistics;
import fr.naruse.deacoudre.v1_13.util.leaderboard.Holograms;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class DacCommands implements CommandExecutor, TabExecutor {
    private DacPluginV1_13 pl;
    public DacCommands(DacPluginV1_13 dacPlugin) {
        this.pl = dacPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                sendMessage(sender, "§3Hey! §6/§cdac stats <[Player]>");
                sendMessage(sender, "§3Hey! §6/§cdac join <Dac Name>");
            }
            if(args.length != 0) if(args[0].equalsIgnoreCase("statistics") || args[0].equalsIgnoreCase("stats")){
                if(args.length < 1){
                    return sendMessage(sender, "§3Hey! §6/§cdac statistics <[Player]>");
                }
                OfflinePlayer target = p;
                if(args.length > 1){
                    target = Bukkit.getPlayer(args[1]);
                    if(target == null){
                        target = Bukkit.getOfflinePlayer(args[1]);
                        if(target == null){
                            return sendMessage(sender, "§c"+ Message.PLAYER_NOT_FOUND.getMessage());
                        }
                    }
                }
                PlayerStatistics playerStatistics = null;
                if(p.isOnline()){
                    if(pl.statisticsOfPlayer.containsKey(target.getPlayer())){
                        playerStatistics = pl.statisticsOfPlayer.get(target.getPlayer());
                    }else{
                        playerStatistics = new PlayerStatistics(pl, target.getName());
                    }
                }
                if(playerStatistics == null){
                    return sendMessage(sender, "§c"+ Message.STATISTICS_NOT_FOUND.getMessage());
                }
                playerStatistics.openInventory(p);
                return true;
            }else if(args.length != 0) if(args[0].equalsIgnoreCase("join")){
                if(args.length < 2){
                    return sendMessage(sender, "§3Hey! §6/§cdac join <Dac Name>");
                }
                for(Dac dac : pl.dacs.getDacs()){
                    if(dac.getName().equalsIgnoreCase(args[1])){
                        pl.dacs.addPlayer(p, dac);
                        return true;
                    }
                }
                return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
            }
            if(args.length == 0){
                if(hasPermission(p, "dac.help")){
                    return sendMessage(sender, "§3Hey! §6/§cdac help");
                }else{
                    return false;
                }
            }
            if(args[0].equalsIgnoreCase("help")){
                if(!hasPermission(p, "dac.help")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                int page = 1;
                if(args.length > 1){
                    try{
                        page = Integer.valueOf(args[1]);
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                    }
                }
                if(!help(sender, page)){
                    return sendMessage(sender, "§c"+ Message.PAGE_NOT_FOUND.getMessage());
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("npc")){
                if(!hasPermission(p, "dac.npc")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                if(args[1].equalsIgnoreCase("stats")){
                    pl.getConfig().set("dac.npc.x", p.getLocation().getX());
                    pl.getConfig().set("dac.npc.y", p.getLocation().getY());
                    pl.getConfig().set("dac.npc.z", p.getLocation().getZ());
                    pl.getConfig().set("dac.npc.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac.npc.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac.npc.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("First")){
                    pl.getConfig().set("dac.firstNpc.x", p.getLocation().getX());
                    pl.getConfig().set("dac.firstNpc.y", p.getLocation().getY());
                    pl.getConfig().set("dac.firstNpc.z", p.getLocation().getZ());
                    pl.getConfig().set("dac.firstNpc.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac.firstNpc.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac.firstNpc.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("second")){
                    pl.getConfig().set("dac.secondNpc.x", p.getLocation().getX());
                    pl.getConfig().set("dac.secondNpc.y", p.getLocation().getY());
                    pl.getConfig().set("dac.secondNpc.z", p.getLocation().getZ());
                    pl.getConfig().set("dac.secondNpc.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac.secondNpc.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac.secondNpc.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("third")){
                    pl.getConfig().set("dac.thirdNpc.x", p.getLocation().getX());
                    pl.getConfig().set("dac.thirdNpc.y", p.getLocation().getY());
                    pl.getConfig().set("dac.thirdNpc.z", p.getLocation().getZ());
                    pl.getConfig().set("dac.thirdNpc.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac.thirdNpc.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac.thirdNpc.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
                }else{
                    return help(sender, 1);
                }
            }
            if(args[0].equalsIgnoreCase("manager")){
                if(!hasPermission(p, "dac.manager")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                pl.getConfig().set("dac.manager.x", p.getLocation().getX());
                pl.getConfig().set("dac.manager.y", p.getLocation().getY());
                pl.getConfig().set("dac.manager.z", p.getLocation().getZ());
                pl.getConfig().set("dac.manager.yaw", p.getLocation().getYaw());
                pl.getConfig().set("dac.manager.pitch", p.getLocation().getPitch());
                pl.getConfig().set("dac.manager.world", p.getLocation().getWorld().getName());
                pl.saveConfig();
                return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
            }
            if(args[0].equalsIgnoreCase("open")){
                if(!hasPermission(p, "dac.open")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(args[1].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                            place = i;
                            break;
                        }
                    }
                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                for(Dac dac : pl.dacs.getDacs()){
                    if(dac.getName().equalsIgnoreCase(args[1])){
                        if(!dac.isOpen()){
                            dac.open();
                            pl.getConfig().set("dac."+place+".isOpen", true);
                            pl.saveConfig();
                        }else{
                            return sendMessage(sender, "§c"+ Message.GAME_ALREADY_OPEN.getMessage());
                        }
                        break;
                    }
                }
                return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.ARENA_OPENED.getMessage());
            }
            if(args[0].equalsIgnoreCase("close")){
                if(!hasPermission(p, "dac.close")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(args[1].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                            place = i;
                            break;
                        }
                    }
                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                for(Dac dac : pl.dacs.getDacs()){
                    if(dac.getName().equalsIgnoreCase(args[1])){
                        if(dac.isOpen()){
                            dac.close();
                            pl.getConfig().set("dac."+place+".isOpen", false);
                            pl.saveConfig();
                        }else{
                            return sendMessage(sender, "§c"+ Message.GAME_AREADY_CLOSED.getMessage());
                        }
                        break;
                    }
                }
                return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.ARENA_CLOSED.getMessage());
            }
            if(args[0].equalsIgnoreCase("create")){
                if(!hasPermission(p, "dac.create")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") == null){
                        place = i;
                    }else if(args[1].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                        return sendMessage(sender, "§c"+ Message.NAME_ALREADY_USED.getMessage());
                    }

                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.TOO_MUCH_ARENAS.getMessage());
                }
                pl.getConfig().set("dac."+place+".name", args[1]);
                pl.getConfig().set("dac."+place+".isOpen", true);
                pl.saveConfig();
                return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.ARENA_CREATED.getMessage());
            }
            if(args[0].equalsIgnoreCase("delete")){
                if(!hasPermission(p, "dac.delete")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(args[1].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                            place = i;
                            break;
                        }
                    }
                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                pl.getConfig().set("dac."+place, null);
                pl.saveConfig();
                return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.ARENA_DELETED.getMessage());
            }
            if(args[0].equalsIgnoreCase("set")){
                if(!hasPermission(p, "dac.set")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 3){
                   return help(sender, 1);
                }
                if(args[1].equalsIgnoreCase("holograms")){
                    if(args.length < 3){
                        return help(sender, 3);
                    }
                    if(args[2].equalsIgnoreCase("location")){
                        pl.getConfig().set("holograms.location.x", p.getLocation().getX());
                        pl.getConfig().set("holograms.location.y", p.getLocation().getY());
                        pl.getConfig().set("holograms.location.z", p.getLocation().getZ());
                        pl.getConfig().set("holograms.location.world", p.getLocation().getWorld().getName());
                        pl.saveConfig();
                        pl.holograms.removeLeaderBoard();
                        pl.holograms = new Holograms(pl);
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.LOCATION_SAVED.getMessage());
                    }
                    if(args[2].equalsIgnoreCase("enable")){
                        pl.getConfig().set("holograms.enable", !pl.getConfig().getBoolean("holograms.enable"));
                        pl.saveConfig();
                        pl.holograms.removeLeaderBoard();
                        pl.holograms = new Holograms(pl);
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.SETTING_SAVED.getMessage());
                    }
                }
                if(args[1].equalsIgnoreCase("rewards")){
                    if(args.length < 4){
                        return help(sender, 2);
                    }
                    double d;
                    try{
                        d = Double.valueOf(args[3]);
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                    }
                    if(args[2].equalsIgnoreCase("win")){
                        pl.getConfig().set("rewards.win", d);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.NUMBER_SAVED.getMessage()+" §7(Rewards.win: "+d+")");
                    }
                    if(args[2].equalsIgnoreCase("lose")){
                        pl.getConfig().set("rewards.lose", d);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.NUMBER_SAVED.getMessage()+" §7(Rewards.lose: "+d+")");
                    }
                    return false;
                }
                if(args[1].equalsIgnoreCase("lives")){
                    if(args.length < 4){
                        return help(sender, 3);
                    }
                    if(args[2].equalsIgnoreCase("min")){
                        int lives;
                        try{
                            lives = Integer.valueOf(args[3]);
                            if(lives < 1){
                                return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage()+" (Number >= 1)");
                            }
                        }catch (Exception e){
                            return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                        }
                        pl.getConfig().set("lives.min", lives);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.NUMBER_SAVED.getMessage());
                    }else if(args[2].equalsIgnoreCase("max")){
                        int lives;
                        try{
                            lives = Integer.valueOf(args[3]);
                            if(lives < 1){
                               lives = -1;
                            }
                        }catch (Exception e){
                            return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                        }
                        pl.getConfig().set("lives.max", lives);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.NUMBER_SAVED.getMessage());
                    }else{
                        return help(sender, 3);
                    }
                }
                if(args[1].equalsIgnoreCase("broadcast")) {
                    if (args.length < 3) {
                        return help(sender, 3);
                    }
                    if(args[2].equalsIgnoreCase("comeJoin")){
                        if(args.length > 3){
                            int i;
                            try{
                                i = Integer.valueOf(args[3]);
                            }catch (Exception e){
                                return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                            }
                            pl.getConfig().set("broadcast.comeJoin.number", i);
                            pl.saveConfig();
                            return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.SETTING_SAVED.getMessage()+" §7(comeJoin.number: "+i+")");
                        }
                        pl.getConfig().set("broadcast.comeJoin.enable", !pl.getConfig().getBoolean("broadcast.comeJoin.enable"));
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.SETTING_SAVED.getMessage()+" §7(comeJoin.enable: "+pl.getConfig().getBoolean("broadcast.comeJoin.enable")+")");
                    }
                    return false;
                }
                if(args[1].equalsIgnoreCase("time")){
                    if(args.length < 4){
                        return sendMessage(sender, "§3Hey! §6/§cdac set time <Wait, Jump> <Number>");
                    }
                    int time;
                    try{
                        time = Integer.valueOf(args[3]);
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                    }
                    if(args[2].equalsIgnoreCase("wait")){
                        pl.getConfig().set("times.wait", time);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.TIME_SAVED.getMessage());
                    }else if(args[2].equalsIgnoreCase("jump")){
                        pl.getConfig().set("times.jump", time);
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.TIME_SAVED.getMessage());
                    }
                }else if(args[1].equalsIgnoreCase("firework")){
                    if(args.length < 4){
                        sendMessage(sender, "§3Hey! §6/§cdac set firework enable <True, False>");
                        return sendMessage(sender, "§3Hey! §6/§cdac set firework count <New Count>");
                    }
                    if(args[2].equalsIgnoreCase("enable")){
                        try{
                            boolean bool = Boolean.valueOf(args[3]);
                            pl.getConfig().set("firework.isEnable", bool);
                            pl.saveConfig();
                            return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DONE.getMessage());
                        }catch (Exception e){
                            return help(sender, 2);
                        }
                    }else if(args[2].equalsIgnoreCase("count")){
                        try{
                            int count = Integer.valueOf(args[3]);
                            pl.getConfig().set("firework.count", count);
                            pl.saveConfig();
                            return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DONE.getMessage());
                        }catch (Exception e){
                            return help(sender, 2);
                        }
                    }
                    return help(sender, 2);
                }else if(args[1].equalsIgnoreCase("Language")){
                    if(args[2].equalsIgnoreCase("french")){
                        pl.getConfig().set("language", "fr");
                    }else if(args[2].equalsIgnoreCase("english")){
                        pl.getConfig().set("language", "en");
                    }else{
                        pl.getConfig().set("language", "custom");
                    }
                    pl.saveConfig();
                    pl.configurations.getMessages().clearConfiguration();
                    pl.configurations.getMessages().generateConfig(false);
                    return sendMessage(sender, Message.DAC.getMessage()+"§a Language changed.");
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(args[2].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                            place = i;
                            break;
                        }
                    }
                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                if(args[1].equalsIgnoreCase("region")){
                    if(pl.worldEditPlugin == null){
                        return sendMessage(sender, "§c"+ Message.NEEDS_WE.getMessage());
                    }
                    if(Bukkit.getVersion().contains("1.13")){
                        return sendMessage(sender, "§c"+ Message.WE_DOESNT_WORK_IN_1_13.getMessage());
                    }
                    Selection selection = pl.worldEditPlugin.getSelection(p);
                    if(selection == null) {
                        return sendMessage(sender, "§cNo selection found.");
                    }
                    Vector min = selection.getNativeMinimumPoint();
                    Vector max = selection.getNativeMaximumPoint();
                    Block block = selection.getWorld().getBlockAt(min.getBlockX(), min.getBlockY(), min.getBlockZ());
                    pl.getConfig().set("dac."+place+".region.a.x", block.getX());
                    pl.getConfig().set("dac."+place+".region.a.y", block.getY());
                    pl.getConfig().set("dac."+place+".region.a.z", block.getZ());
                    pl.getConfig().set("dac."+place+".region.a.world", block.getWorld().getName());
                    block = selection.getWorld().getBlockAt(max.getBlockX(), max.getBlockY(), max.getBlockZ());
                    pl.getConfig().set("dac."+place+".region.b.x", block.getX());
                    pl.getConfig().set("dac."+place+".region.b.y", block.getY());
                    pl.getConfig().set("dac."+place+".region.b.z", block.getZ());
                    pl.getConfig().set("dac."+place+".region.b.world", block.getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.REGION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("regionWithPos")){
                    Block block = p.getLocation().getBlock();
                    if(args.length <= 3){
                        return help(sender, 1);
                    }
                    if(args[3].equalsIgnoreCase("pos1")){
                        pl.getConfig().set("dac."+place+".region.a.x", block.getX());
                        pl.getConfig().set("dac."+place+".region.a.y", block.getY());
                        pl.getConfig().set("dac."+place+".region.a.z", block.getZ());
                        pl.getConfig().set("dac."+place+".region.a.world", block.getWorld().getName());
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.REGION_SAVED.getMessage()+"§7 (Pos1)");
                    }else{
                        pl.getConfig().set("dac."+place+".region.b.x", block.getX());
                        pl.getConfig().set("dac."+place+".region.b.y", block.getY());
                        pl.getConfig().set("dac."+place+".region.b.z", block.getZ());
                        pl.getConfig().set("dac."+place+".region.b.world", block.getWorld().getName());
                        pl.saveConfig();
                        return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.REGION_SAVED.getMessage()+"§7 (Pos2)");
                    }
                }else if(args[1].equalsIgnoreCase("diving")){
                    pl.getConfig().set("dac."+place+".diving.x", p.getLocation().getX());
                    pl.getConfig().set("dac."+place+".diving.y", p.getLocation().getY());
                    pl.getConfig().set("dac."+place+".diving.z", p.getLocation().getZ());
                    pl.getConfig().set("dac."+place+".diving.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac."+place+".diving.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac."+place+".diving.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("pool")){
                    pl.getConfig().set("dac."+place+".pool.x", p.getLocation().getX());
                    pl.getConfig().set("dac."+place+".pool.y", p.getLocation().getY());
                    pl.getConfig().set("dac."+place+".pool.z", p.getLocation().getZ());
                    pl.getConfig().set("dac."+place+".pool.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac."+place+".pool.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac."+place+".pool.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("end")){
                    pl.getConfig().set("dac."+place+".end.x", p.getLocation().getX());
                    pl.getConfig().set("dac."+place+".end.y", p.getLocation().getY());
                    pl.getConfig().set("dac."+place+".end.z", p.getLocation().getZ());
                    pl.getConfig().set("dac."+place+".end.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac."+place+".end.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac."+place+".end.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("lobby")){
                    pl.getConfig().set("dac."+place+".lobby.x", p.getLocation().getX());
                    pl.getConfig().set("dac."+place+".lobby.y", p.getLocation().getY());
                    pl.getConfig().set("dac."+place+".lobby.z", p.getLocation().getZ());
                    pl.getConfig().set("dac."+place+".lobby.yaw", p.getLocation().getYaw());
                    pl.getConfig().set("dac."+place+".lobby.pitch", p.getLocation().getPitch());
                    pl.getConfig().set("dac."+place+".lobby.world", p.getLocation().getWorld().getName());
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.LOCATION_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("min")){
                    if(args.length < 4){
                        return sendMessage(sender, "§3Hey! §6/§cdac set <Min, Max> <Dac name> <Number>");
                    }
                    int min;
                    try{
                        min = Integer.valueOf(args[3]);
                        if(min <= 0){
                            return sendMessage(sender, "§c"+ Message.GREATED_THAN_0.getMessage());
                        }
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                    }
                    pl.getConfig().set("dac."+place+".min", min);
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.NUMBER_SAVED.getMessage());
                }else if(args[1].equalsIgnoreCase("max")){
                    if(args.length < 4){
                        return sendMessage(sender, "§3Hey! §6/§cdac set <Min, Max> <Dac name> <Number>");
                    }
                    int max;
                    try{
                        max = Integer.valueOf(args[3]);
                        if(max <= 0){
                            return sendMessage(sender, "§c"+ Message.GREATED_THAN_0.getMessage());
                        }
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+ Message.NEED_A_NUMBER.getMessage());
                    }
                    pl.getConfig().set("dac."+place+".max", max);
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+"§a "+ Message.NUMBER_SAVED.getMessage());
                }
            }
            if(args[0].equalsIgnoreCase("remove")){
                if(!hasPermission(p, "dac.remove")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 3){
                    return sendMessage(sender, "§3Hey! §6/§cdac remove Region <Dac Name>");
                }
                int place = 1000;
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(args[2].equalsIgnoreCase(pl.getConfig().getString("dac."+i+".name"))){
                            place = i;
                            break;
                        }
                    }
                }
                if(place == 1000){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                if(args[1].equalsIgnoreCase("region")){
                    pl.getConfig().set("dac."+place+".region", null);
                    pl.saveConfig();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.REGION_REMOVED.getMessage());
                }
            }
            if(args[0].equalsIgnoreCase("list")){
                if(!hasPermission(p, "dac.list")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                String activeDac = ",,", breakdownDac = ",,";
                List<String> list = Lists.newArrayList();
                for(Dac dac : pl.dacs.getDacs()){
                    activeDac += ", "+dac.getName();
                    list.add(dac.getName());
                }
                activeDac = activeDac.replace(",,, ", "");
                for(int i = 0; i != 999; i++){
                    if(pl.getConfig().getString("dac."+i+".name") != null){
                        if(!list.contains(pl.getConfig().getString("dac."+i+".name"))){
                            breakdownDac += ", "+pl.getConfig().getString("dac."+i+".name");
                        }
                    }
                }
                breakdownDac = breakdownDac.replace(",,, ", "");
                if(breakdownDac.contains(",,")){
                    breakdownDac = "";
                }
                if(activeDac.contains(",,")){
                    activeDac = "";
                }
                sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DAC_IN_OPERATION.getMessage()+" §2"+activeDac);
                return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DAC_IN_FAILURE.getMessage()+" §c"+breakdownDac);
            }
            if(args[0].equalsIgnoreCase("force")){
                if(!hasPermission(p, "dac.force")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args.length < 3){
                    return sendMessage(sender, "§3Hey! §6/§cdac force <Start, Stop> <Dac name>");
                }
                Dac dac = null;
                for(Dac d : pl.dacs.getDacs()){
                    if(d.getName().equalsIgnoreCase(args[2])){
                        dac = d;
                        break;
                    }
                }
                if(dac == null){
                    return sendMessage(sender, "§c"+ Message.ARENA_NOT_FOUND.getMessage());
                }
                if(args[1].equalsIgnoreCase("start")){
                    if(dac.game.WAIT){
                        if(dac.getPlayerInGame().size() == 0){
                            return sendMessage(sender, "§c"+ Message.THERE_ARE_NO_PLAYERS.getMessage());
                        }
                        dac.start();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.ARENA_STARTED.getMessage());
                    }else{
                        return sendMessage(sender, "§c"+ Message.ARENA_ALREADY_STARTED.getMessage());
                    }
                }else if(args[1].equalsIgnoreCase("stop")){
                    dac.stop();
                    return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.ARENA_STOPPED.getMessage());
                }
            }
            if(args[0].equalsIgnoreCase("reload")){
                if(!hasPermission(p, "dac.reload")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                sendMessage(sender, Message.DAC.getMessage()+" §cReloading...");
                pl.reload();
                return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DONE.getMessage());
            }
            if(args[0].equalsIgnoreCase("clear")){
                if(!hasPermission(p, "dac.clear")){
                    return sendMessage(sender, "§c"+ Message.YOU_DONT_HAVE_PERMISSION.getMessage());
                }
                if(args[1].equalsIgnoreCase("config")){
                    if(args[2].equalsIgnoreCase("manager")){
                        sendMessage(sender, Message.DAC.getMessage()+" §cReloading configuration...");
                        pl.configurations.getManager().clearConfiguration();
                        return sendMessage(sender, Message.DAC.getMessage()+" §a"+ Message.DONE.getMessage());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> list = Lists.newArrayList();
        if(args.length == 2){
            for(Dac dac : pl.dacs.getDacs()){
                if(dac.getName().contains(args[1])){
                    list.add(dac.getName());
                }
            }
        }
        if(args.length == 3){
            for(Dac dac : pl.dacs.getDacs()){
                if(dac.getName().contains(args[2])){
                    list.add(dac.getName());
                }
            }
        }
        return list;
    }

    private boolean sendMessage(CommandSender sender, String msg){
        sender.sendMessage(msg);
        return true;
    }

    private boolean help(CommandSender sender, int page){
        if(page == 1){
            sendMessage(sender, Message.DAC.getMessage()+" §2----------------- "+ Message.DAC.getMessage());
            sendMessage(sender, "§3Hey! §6/§cdac <Open, Close> <Dac name>");
            sendMessage(sender, "§3Hey! §6/§cdac <Create, Delete> <dac name>");
            sendMessage(sender, "§3Hey! §6/§cdac set <Min, Max> <Dac Name> <Number>");
            sendMessage(sender, "§3Hey! §6/§cdac set <Diving, Pool, End, Lobby> <Dac name>");
            sendMessage(sender, "§3Hey! §6/§cdac set language <French, English, Custom>");
            sendMessage(sender, "§3Hey! §6/§cdac set Region <Dac Name> §7(With WordEdit)");
            sendMessage(sender, "§3Hey! §6/§cdac set RegionWithPos <Dac Name> <Pos1, Pos2>");
            sendMessage(sender, "§3Hey! §6/§cdac remove Region <Dac Name>");
            sendMessage(sender, "§bPage: §21/3");
        }else if(page == 2) {
            sendMessage(sender, Message.DAC.getMessage()+" §2----------------- "+ Message.DAC.getMessage());
            sendMessage(sender, "§3Hey! §6/§cdac manager §7(Only in French)");
            sendMessage(sender, "§3Hey! §6/§cdac npc <Stats, First, Second, Third> §7(With Citizens)");
            sendMessage(sender, "§3Hey! §6/§cdac list");
            sendMessage(sender, "§3Hey! §6/§cdac force <Start, Stop> <Dac name>");
            sendMessage(sender, "§3Hey! §6/§cdac set time <Wait, Jump> <Number>");
            sendMessage(sender, "§3Hey! §6/§cdac set firework enable <True, False>");
            sendMessage(sender, "§3Hey! §6/§cdac set firework count <New Count>");
            sendMessage(sender, "§3Hey! §6/§cdac reload");
            sendMessage(sender, "§bPage: §22/3");
        }else if(page == 3) {
            sendMessage(sender, Message.DAC.getMessage()+" §2----------------- "+ Message.DAC.getMessage());
            sendMessage(sender, "§3Hey! §6/§cdac set lives <Min, Max> <Number> §7(-1 means 'no limit' for the max)");
            sendMessage(sender, "§3Hey! §6/§cdac set broadcast <comeJoin> <[Number]>");
            sendMessage(sender, "§3Hey! §6/§cdac set rewards <Win, Lose> <Number>");
            sendMessage(sender, "§3Hey! §6/§cdac clear config Manager");
            sendMessage(sender, "§3Hey! §6/§cdac set holograms <Location, Enable>");
            sendMessage(sender, "§bPage: §23/3");
        }else{
            return false;
        }
        return true;
    }

    private boolean hasPermission(Player p, String msg){
        if(!p.hasPermission(msg)){
            if(!p.getName().equalsIgnoreCase("NaruseII")){
                return false;
            }
        }
        return true;
    }
}
