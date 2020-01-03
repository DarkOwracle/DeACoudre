package fr.naruse.deacoudre.common;

import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.v1_12.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DacPlayer {
    private Inventory inv;
    private Player p;
    private GameMode gameMode;
    public DacPlayer(Player p){
        this.p = p;
    }

    public void registerInventory(){
        inv = Bukkit.createInventory(null, 9*6);
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
                        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§c"+ Message.LEAVE_THIS_GAME.getMessage())){
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
