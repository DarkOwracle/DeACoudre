package fr.naruse.deacoudre.v1_12.event;

import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.v1_12.dac.Dac;
import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.v1_12.util.Message;
import fr.naruse.deacoudre.v1_12.util.PlayerStatistics;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Listeners implements Listener {
    private DacPluginV1_12 pl;
    public Listeners(DacPluginV1_12 dacPlugin) {
        this.pl = dacPlugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        PlayerStatistics playerStatistics = new PlayerStatistics(pl, e.getPlayer().getName());
        pl.statisticsOfPlayer.put(e.getPlayer(), playerStatistics);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        pl.statisticsOfPlayer.get(e.getPlayer()).saveStatistics();
        pl.dacs.removePlayer(e.getPlayer());
        pl.statisticsOfPlayer.remove(e.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void command(PlayerCommandPreprocessEvent e){
        if(pl.dacs.getDacOfPlayer().containsKey(e.getPlayer())){
            List<String> commands = pl.configurations.getCommands().getConfig().getStringList("commands");
            if(commands.contains(e.getMessage().split(" ")[0].replace("/", ""))){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getClickedBlock() != null){
            if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
                if(hasPermission(p, "dac.sign.break")){
                    return;
                }
            }
            if(e.getClickedBlock().getState() instanceof Sign){
                Sign sign = (Sign) e.getClickedBlock().getState();
                for(Dac dac : pl.dacs.getDacs()){
                    if(sign.getLine(0).equalsIgnoreCase(dac.getFullName())){
                        pl.dacs.addPlayer(p, dac);
                        e.setCancelled(true);
                        break;
                    }
                }
                if(hasPermission(p, "dac.sign.create")){
                    if(sign.getLine(0).equalsIgnoreCase("-!!-") && sign.getLine(3).equalsIgnoreCase("-!!-")){
                        if(sign.getLine(1).equalsIgnoreCase(sign.getLine(2))){
                            for(Dac dac : pl.dacs.getDacs()){
                                if(dac.getName().equalsIgnoreCase(sign.getLine(1))){
                                    sign.setLine(0, dac.getFullName());
                                    sign.update();
                                    dac.registerNewSigns(p.getWorld());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasPermission(Player p, String msg){
        if(!p.hasPermission(msg)){
            if(!p.getName().equalsIgnoreCase("NaruseII")){
                return false;
            }
        }
        return true;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(pl.dacs.getDacOfPlayer().containsKey(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void get(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        if(pl.dacs.getDacOfPlayer().containsKey(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            if(pl.dacs.getDacOfPlayer().containsKey(e.getWhoClicked())){
                e.setCancelled(true);
            }
        }
        if(e.getInventory().getName().contains("§2§l"+Message.BLOCK_CHOICE.getMessage())){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                Player p = (Player) e.getWhoClicked();
                int currentInv = Integer.valueOf(e.getInventory().getName().split(" ")[e.getInventory().getName().split(" ").length-1].replace("/6", ""))-1;
                if(e.getSlot() == 0){
                    p.closeInventory();
                    return;
                }
                if(e.getSlot() == 53){
                    p.closeInventory();
                    pl.blockChoice.openInventory(p, currentInv+1);
                    return;
                }
                if(e.getSlot() == 45){
                    p.closeInventory();
                    pl.blockChoice.openInventory(p, currentInv-1);
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if(item.getType() != Material.STAINED_GLASS_PANE){
                    if(pl.dacs.getDacOfPlayer().containsKey(p)){
                        pl.dacs.getDacOfPlayer().get(p).setBlockAndDataOfPlayer(p, item.getType(), item.getData().getData());
                        p.closeInventory();
                        p.sendMessage(Message.DAC.getMessage()+" §a"+Message.BLOCK_CHOOSE.getMessage());
                        return;
                    }else{
                        p.getInventory().clear();
                        p.updateInventory();
                    }
                }
            }
        }
    }
}
