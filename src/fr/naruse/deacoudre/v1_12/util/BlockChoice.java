package fr.naruse.deacoudre.v1_12.util;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.common.Reflection;
import net.minecraft.server.v1_12_R1.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BlockChoice {
    private List<Inventory> invs = Lists.newArrayList();
    private HashMap<Integer, Inventory> invOfInteger = new HashMap<>();
    public BlockChoice(){
        for(int i = 1; i <= 40; i++){
            Inventory inv = Bukkit.createInventory(null, 9*6, "§2§l"+Message.BLOCK_CHOICE.getMessage()+" §3- "+i);
            invs.add(inv);
            invOfInteger.put(i-1, inv);
        }
        ItemStack item;
        List<Material> materialsForData1 = Lists.newArrayList();
        materialsForData1.add(Material.SPONGE);
        materialsForData1.add(Material.LOG_2);
        List<Material> materialsForData3 = Lists.newArrayList();
        materialsForData3.add(Material.QUARTZ_BLOCK);
        materialsForData3.add(Material.SANDSTONE);
        List<Material> materialsForData = Lists.newArrayList();
        materialsForData.add(Material.WOOL);
        materialsForData.add(Material.STAINED_CLAY);
        if(Material.getMaterial(251) != null){
            materialsForData.add(Material.CONCRETE);
            materialsForData.add(Material.CONCRETE_POWDER);
        }
        if(Material.getMaterial(95) != null){
            materialsForData.add(Material.STAINED_GLASS);
        }
        List<Material> materialsForData6 = Lists.newArrayList();
        materialsForData6.add(Material.WOOD);
        materialsForData6.add(Material.STONE);
        List<Material> materialsForData4 = Lists.newArrayList();
        materialsForData4.add(Material.LOG);
        List<String> materialsToDelete = Lists.newArrayList();
        //1.13
        materialsToDelete.add("BED");
        materialsToDelete.add("BANNER");
        materialsToDelete.add("SIGN");
        materialsToDelete.add("DOOR");
        materialsToDelete.add("CAKE");
        materialsToDelete.add("CAULDRON");
        materialsToDelete.add("STAND");
        //1.12 and <
        materialsToDelete.add("BARRIER");
        materialsToDelete.add("SPONGE");
        materialsToDelete.add("SLIME");
        materialsToDelete.add("STRUCTURE_VOID");
        materialsToDelete.add("PISTON");
        materialsToDelete.add("TNT");
        materialsToDelete.add("PLATE");
        materialsToDelete.add("STAINED_GLASS_PANE");
        materialsToDelete.add("FENCE");
        materialsToDelete.add("STAIRS");
        materialsToDelete.add("TRAP");
        materialsToDelete.add("CACTUS");
        materialsToDelete.add("COMMAND");
        materialsToDelete.add("EGG");
        materialsToDelete.add("DETECTOR");
        materialsToDelete.add("CHEST");
        materialsToDelete.add("SLAB");
        materialsToDelete.add("GLASS_PANE");
        materialsToDelete.add("LEAVES");
        materialsToDelete.add("HOPPER");
        materialsToDelete.add("WALL");
        materialsToDelete.add("EMERALD");
        materialsToDelete.add("SPAWNER");
        materialsToDelete.add("THIN");
        materialsToDelete.add("ANVIL");
        materialsToDelete.add("STEP");
        materialsToDelete.add("FARMLAND");
        materialsToDelete.add("FURNACE");
        List<String> list = Lists.newArrayList();
        Inventory inv = invs.get(0);
        generateInv(inv);
        int invCount = 0;
        for(Material material : Material.values()){
            if(material == null){
                break;
            }
            if(material.isSolid()){
                item = new ItemStack(material);
                boolean canPass = true;
                for(String s : materialsToDelete){
                    if(item.getType().toString().contains(s)){
                        canPass = false;
                        break;
                    }
                }
                if(canPass){
                    inv.addItem(item);
                    list.add(item.getType()+":0");
                    if(materialsForData.contains(material)){
                        for(int o = 1; o != 15; o++){
                            if(inv.getItem(51) != null){
                                invCount++;
                                if(invOfInteger.containsKey(invCount)){
                                    inv = invOfInteger.get(invCount);
                                    generateInv(inv);
                                }else{
                                    break;
                                }
                            }
                            item = new ItemStack(material, 1, (byte) o);
                            inv.addItem(item);
                            list.add(item.getType()+":"+o);
                        }
                    }else if(materialsForData6.contains(material)){
                        for(int o = 1; o != 6; o++){
                            if(inv.getItem(51) != null){
                                invCount++;
                                if(invOfInteger.containsKey(invCount)){
                                    inv = invOfInteger.get(invCount);
                                    generateInv(inv);
                                }else{
                                    break;
                                }
                            }
                            item = new ItemStack(material, 1, (byte) o);
                            inv.addItem(item);
                            list.add(item.getType()+":"+o);
                        }
                    }else if(materialsForData4.contains(material)){
                        for(int o = 1; o != 4; o++){
                            if(inv.getItem(51) != null){
                                invCount++;
                                if(invOfInteger.containsKey(invCount)){
                                    inv = invOfInteger.get(invCount);
                                    generateInv(inv);
                                }else{
                                    break;
                                }
                            }
                            item = new ItemStack(material, 1, (byte) o);
                            inv.addItem(item);
                            list.add(item.getType()+":"+o);
                        }
                    }else if(materialsForData1.contains(material)){
                        for(int o = 1; o != 2; o++){
                            if(inv.getItem(51) != null){
                                invCount++;
                                if(invOfInteger.containsKey(invCount)){
                                    inv = invOfInteger.get(invCount);
                                    generateInv(inv);
                                }else{
                                    break;
                                }
                            }
                            item = new ItemStack(material, 1, (byte) o);
                            inv.addItem(item);
                            list.add(item.getType()+":"+o);
                        }
                    }
                }
            }
            if(inv.getItem(51) != null){
                invCount++;
                if(invOfInteger.containsKey(invCount)){
                    inv = invOfInteger.get(invCount);
                    generateInv(inv);
                }else{
                    break;
                }
            }
        }
    }

    private void generateInv(Inventory inv){
        for(int i = 1; i != 9; i++){
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
            inv.setItem(i, item);
        }
        for(int i = 9; i != 5*9; i += 9){
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
            inv.setItem(i, item);
        }
        ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 14);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c"+Message.LEAVE_CHEST.getMessage());
        item.setItemMeta(meta);
        inv.addItem(item);
        item = new ItemStack(Material.WOOL, 1, (byte) 9);
        meta = item.getItemMeta();
        meta.setDisplayName("§3"+Message.NEXT.getMessage());
        item.setItemMeta(meta);
        inv.setItem(53, item);
        item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
        inv.setItem(52, item);
        inv.setItem(44, item);
        inv.setItem(46, item);
        item = new ItemStack(Material.WOOL, 1, (byte) 3);
        meta = item.getItemMeta();
        meta.setDisplayName("§b"+Message.BACK.getMessage());
        item.setItemMeta(meta);
        inv.setItem(45, item);
    }

    public void openInventory(Player player, int inv){
        if(inv < 0){
            return;
        }
        if(invOfInteger.containsKey(inv)){
            Inventory inventory = invs.get(inv);
            if(inventory.getItem(10) != null){
                player.openInventory(inventory);
            }
        }
    }
}
