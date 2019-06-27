package fr.naruse.deacoudre.v1_12.util;

import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DacPlayer {
    private Inventory inv;
    private Player p;
    private DacPluginV1_12 pl;
    private GameMode gameMode;
    public DacPlayer(DacPluginV1_12 pl, Player p){
        this.pl = pl;
        this.p = p;
    }

    public void registerInventory(){
        inv = Bukkit.createInventory(null, 9*6, p.getInventory().getName());
        for(int i = 0; i < inv.getSize(); i++){
            try{
                if(p.getInventory().getItem(i) != null){
                    inv.setItem(i, p.getInventory().getItem(i));
                }
            }catch (Exception e){
                break;
            }
        }
    }

    public void setPlayerInventory(){
        if(inv == null){
            return;
        }
        for(int i = 0; i < 9*6; i++){
            if(inv.getItem(i) != null){
                p.getInventory().setItem(i, inv.getItem(i));
            }
        }
        for(int i = 0; i < p.getInventory().getSize(); i++){
            if(p.getInventory().getItem(i) != null){
                ItemStack item = p.getInventory().getItem(i);
                if(item.getType() == Material.MAGMA_CREAM){
                    if(item.getItemMeta().getDisplayName() != null){
                        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§c"+Message.LEAVE_THIS_GAME.getMessage())){
                            p.getInventory().remove(item);
                        }
                    }
                }else if(item.getType() == Material.BLAZE_POWDER){
                    if(item.getItemMeta().getDisplayName() != null){
                        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§2"+Message.BLOCK_CHOICE.getMessage())){
                            p.getInventory().remove(item);
                        }
                    }
                }
            }
        }
    }

    public void registerGameMode(){
        this.gameMode = p.getGameMode();
    }

    public void setPlayerGameMode(){
        if(gameMode != null){
            p.setGameMode(gameMode);
        }
    }
}
