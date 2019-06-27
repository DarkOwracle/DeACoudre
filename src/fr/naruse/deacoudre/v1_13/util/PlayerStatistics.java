package fr.naruse.deacoudre.v1_13.util;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.manager.DacPluginV1_13;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerStatistics implements Listener {
    private DacPluginV1_13 pl;
    private String name;
    private long perfects;
    private long fails;
    private long wins;
    private long loses;
    private long games;
    private long jumps;
    private FileConfiguration statistic;
    private Inventory inv;
    private Inventory inventoryBeforePlay;
    public PlayerStatistics(DacPluginV1_13 pl, String name){
        this.pl = pl;
        this.name = name;
        this.statistic = pl.configurations.getStatistics().getStatistics();
        this.inv = Bukkit.createInventory(null, 9*2, "§2§l"+name+"'s "+ Message.STATISTICS.getMessage());
        refreshStatisticFromConfig();
        Bukkit.getPluginManager().registerEvents(this, pl.getDacPlugin());
    }

    public void refreshStatisticFromConfig(){
        if(statistic.getString(name+".wins") == null){
            createStatistics();
        }
        this.perfects = statistic.getLong(name+".perfects");
        this.fails = statistic.getLong(name+".fails");
        this.wins = statistic.getLong(name+".wins");
        this.loses = statistic.getLong(name+".loses");
        this.games = statistic.getLong(name+".games");
        this.jumps = statistic.getLong(name+".jumps");
    }

    private void createStatistics(){
        statistic.set(name+".perfects", 0);
        statistic.set(name+".fails", 0);
        statistic.set(name+".wins", 0);
        statistic.set(name+".loses", 0);
        statistic.set(name+".games", 0);
        statistic.set(name+".jumps", 0);
        pl.configurations.getStatistics().saveConfig();
        System.out.println("[DeACoudre] Statistics for "+name+" created.");
    }

    public void saveStatistics(){
        statistic.set(name+".perfects", perfects);
        statistic.set(name+".fails", fails);
        statistic.set(name+".wins", wins);
        statistic.set(name+".loses", loses);
        statistic.set(name+".games", games);
        statistic.set(name+".jumps", jumps);
        pl.configurations.getStatistics().saveConfig();
    }

    public long getFails() {
        return fails;
    }

    public long getGames() {
        return loses+wins;
    }

    public long getPerfects() {
        return perfects;
    }

    public long getWins() {
        return wins;
    }

    public long getLoses() {
        return loses;
    }

    public long getJumps() {
        return jumps;
    }

    public void addFails(int fails){
        this.fails += fails;
    }

    public void addGames(int games){
        this.games += games;
    }

    public void addPerfects(int perfects){
        this.perfects += perfects;
    }

    public void addWins(int wins){
        this.games += wins;
        this.wins += wins;
    }

    public void addLoses(int loses){
        this.games += loses;
        this.loses += loses;
    }

    public void addJumps(int jumps){
        this.jumps += jumps;
    }

    public void openInventory(Player target){
        inv.clear();
        List<String> lore = Lists.newArrayList();
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aDés à coudre");
        lore.add("§5-§a"+ Message.PERFECTS.getMessage().replace("**", "§6").replace("!!", "§a").replace(":", perfects+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(2, item);
        lore.clear();

        item = new ItemStack(Material.WOOL, 1, (byte) 14);
        meta = item.getItemMeta();
        meta.setDisplayName("§cFails");
        lore.add("§5-§c"+ Message.FAILS.getMessage().replace("**", "§6").replace("!!", "§c").replace(":", fails+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(3, item);
        lore.clear();

        item = new ItemStack(Material.WOOL, 1, (byte) 5);
        meta = item.getItemMeta();
        meta.setDisplayName("§2Wins");
        lore.add("§5-§2"+ Message.WINS.getMessage().replace("**", "§6").replace("!!", "§2").replace(":", wins+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(4, item);
        lore.clear();

        item = new ItemStack(Material.WOOL, 1, (byte) 7);
        meta = item.getItemMeta();
        meta.setDisplayName("§4Loses");
        lore.add("§5-§4"+ Message.LOSES.getMessage().replace("**", "§6").replace("!!", "§4").replace(":", loses+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(5, item);
        lore.clear();

        item = new ItemStack(Material.WOOL, 1, (byte) 9);
        meta = item.getItemMeta();
        meta.setDisplayName("§3Games");
        lore.add("§5-§3"+ Message.GAMES.getMessage().replace("**", "§6").replace("!!", "§3").replace(":", games+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(6, item);
        lore.clear();

        item = new ItemStack(Material.WOOL, 1, (byte) 13);
        meta = item.getItemMeta();
        meta.setDisplayName("§2Jumps");
        lore.add("§5-§2"+ Message.JUMPS.getMessage().replace("**", "§6").replace("!!", "§2").replace(":", jumps+""));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(13, item);
        lore.clear();

        target.openInventory(inv);
    }

    @EventHandler
    public void inventory(InventoryClickEvent e){
        if(e.getInventory() == inv){
            e.setCancelled(true);
        }
    }
}
