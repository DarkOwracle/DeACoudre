package fr.naruse.deacoudre.v1_12.dac;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.common.LaunchFireworks;
import fr.naruse.deacoudre.v1_12.util.Message;
import fr.naruse.deacoudre.v1_12.util.PlayerStatistics;
import fr.naruse.deacoudre.v1_12.util.ScoreboardSign;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Dac implements Listener {
    private DacPluginV1_12 pl;
    private String name;
    private Location diving, pool, end, lobby;
    private int min;
    private int max;
    private int startTimer = 30;
    private int divingTimer = 15;
    private boolean isOpen;
    private boolean isStopping = false;
    private Location a;
    private Location b;
    private List<Sign> signs = Lists.newArrayList();
    private List<Player> playerInGame = Lists.newArrayList();
    private HashMap<Player, Integer> livesOfPlayer = new HashMap<>();
    private HashMap<Player, Byte> dataBlockOfPlayer = new HashMap<>();
    private HashMap<Player, Material> materialBlockOfPlayer = new HashMap<>();
    private List<Block> blocks = Lists.newArrayList();
    private List<Block> blocksOfRegionVerif = Lists.newArrayList();
    private List<Block> blocksOfRegion = Lists.newArrayList();
    private Player jumper;
    private Material[] blockTypes = new Material[] {Material.WOOL, Material.STAINED_CLAY};
    public Game game;
    public ScoreboardSign scoreboardSign;
    public Dac(DacPluginV1_12 pl, String name, Location diving, Location pool, Location end, Location lobby, int min, int max, boolean isOpen) {
        this.pl = pl;
        this.name = name;
        this.diving = diving;
        this.pool = pool;
        this.end = end;
        this.lobby = lobby;
        this.min = min;
        this.max = max;
        this.isOpen = isOpen;
        this.game = new Game();
        this.scoreboardSign = new ScoreboardSign();
        this.startTimer = getOriginalStartTimer();
        this.divingTimer = getOriginalDivingTimer();
        timer();
    }

    private int count = 0;
    private int task;
    public void timer(){
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getDacPlugin(), new Runnable() {
            @Override
            public void run() {
                if(count >= 20){
                    count = 0;
                    for(int i = 0; i < playerInGame.size(); i++){
                        Player p = playerInGame.get(i);
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        if(!p.getInventory().contains(Material.MAGMA_CREAM)){
                            ItemStack item = new ItemStack(Material.MAGMA_CREAM);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName("§c"+Message.LEAVE_THIS_GAME.getMessage());
                            item.setItemMeta(meta);
                            p.getInventory().addItem(item);
                            if(game.WAIT){
                                item = new ItemStack(Material.BLAZE_POWDER);
                                meta = item.getItemMeta();
                                meta.setDisplayName("§2"+Message.BLOCK_CHOICE.getMessage());
                                item.setItemMeta(meta);
                                p.getInventory().setItem(8, item);
                            }
                            p.updateInventory();
                        }
                    }
                    if(game.WAIT){
                        if(startTimer != 0){
                            if(playerInGame.size() < min){
                                startTimer = getOriginalStartTimer();
                            }else{
                                startTimer--;
                            }
                            updateScoreboardTimers(startTimer);
                        }
                        if(startTimer == 0){
                            startTimer = getOriginalStartTimer();
                            if(playerInGame.size() >= min){
                                start();
                            }else{
                                sendMessage(getFullName()+" §c"+Message.NOT_ENOUGH_PLAYER.getMessage()+" ("+playerInGame.size()+"/"+min+")");
                            }
                        }
                    }else if(game.GAME){
                        if(a != null){
                            if(blocksOfRegionVerif.size() == 0){
                                String names = " ";
                                String lives = " ";
                                for(Player p : playerInGame){
                                    PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(p);
                                    playerStatistics.addWins(1);
                                    names += ", "+p.getName();
                                    lives += ", "+livesOfPlayer.get(p);
                                    if (pl.otherPluginSupport.getVaultPlugin().getEconomy() != null) {
                                        if (pl.getConfig().getDouble("rewards.win") != 0) {
                                            pl.otherPluginSupport.getVaultPlugin().getEconomy().depositPlayer(p, pl.getConfig().getDouble("rewards.win"));
                                            p.sendMessage(getFullName() +" §aVous venez de gagner 15 CoinTies.");
                                        }
                                    }
                                }
                                names = names.replace(" , ", "");
                                lives = lives.replace(" , ", "");
                                if(pl.manager != null){
                                    Bukkit.broadcastMessage("§2>> §5§l[§6Manager§5§l] §6Mevo§2: §6"+names+" §3remportent la partie §c§l[§5"+name+"§c§l]§3!");
                                }else{
                                    Bukkit.broadcastMessage(getFullName() +" §6"+names+" §7"+Message.THEY_WINS_THE_GAME_WITH.getMessage()+"§6 "+lives+" "+Message.LIVES.getMessage()+"§7!");
                                }
                                restart(true);
                                return;
                            }
                        }
                        if(min != 1){
                            if(playerInGame.size() == 1){
                                if(!pl.statisticsOfPlayer.containsKey(playerInGame.get(0))){
                                    pl.statisticsOfPlayer.put(playerInGame.get(0), new PlayerStatistics(pl, playerInGame.get(0).getName()));
                                }
                                PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(playerInGame.get(0));
                                playerStatistics.addWins(1);
                                if(pl.manager != null){
                                    Bukkit.broadcastMessage("§2>> §5§l[§6Manager§5§l] §6Mevo§2: §6"+playerInGame.get(0).getName()+" §3remporte la partie §c§l[§5"+name+"§c§l]§3 avec §6"+livesOfPlayer.get(playerInGame.get(0))+" vies§3!");
                                }else{
                                    Bukkit.broadcastMessage(getFullName() +" §6"+playerInGame.get(0).getName()+" §7"+Message.WINS_THE_GAME_WITH.getMessage()+" §6"+livesOfPlayer.get(playerInGame.get(0))+" "+Message.LIVES.getMessage()+"§7!");
                                }
                                if (pl.otherPluginSupport.getVaultPlugin().getEconomy() != null) {
                                    if (pl.getConfig().getDouble("rewards.win") != 0) {
                                        pl.otherPluginSupport.getVaultPlugin().getEconomy().depositPlayer(getPlayerInGame().get(0), pl.getConfig().getDouble("rewards.win"));
                                        getPlayerInGame().get(0).sendMessage(getFullName() +" §aVous venez de gagner 15 CoinTies.");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "level giveexp "+getPlayerInGame().get(0).getName()+" 2");
                                    }
                                }
                                restart(true);
                            }
                        }
                        if(playerInGame.size() == 0){
                            restart(false);
                        }
                        if(divingTimer != 0){
                            divingTimer--;
                            updateScoreboardTimers(divingTimer);
                        }
                        if(divingTimer == 0){
                            divingTimer = getOriginalDivingTimer();
                            Player p = jumper;
                            nextPlayer();
                            pl.dacs.removePlayer(p);
                        }
                    }
                }
                if(game.GAME){
                    if(jumper != null){
                        if(jumper.getLocation().getBlock().getType() == Material.WATER || jumper.getLocation().getBlock().getType() == Material.WATER_LILY ||
                                jumper.getLocation().getBlock().getType() == Material.STATIONARY_WATER){
                            if((jumper.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.WATER && jumper.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.WATER_LILY &&
                                    jumper.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.STATIONARY_WATER) &&
                                    (jumper.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.WATER && jumper.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.WATER_LILY &&
                                            jumper.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.STATIONARY_WATER) &&
                                    (jumper.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.WATER && jumper.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.WATER_LILY &&
                                            jumper.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.STATIONARY_WATER) &&
                                    (jumper.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.WATER && jumper.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.WATER_LILY &&
                                            jumper.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.STATIONARY_WATER)){
                                divingTimer = getOriginalDivingTimer();
                                Location loc = jumper.getLocation().getBlock().getLocation().clone();
                                if(a != null && !blocksOfRegion.contains(loc.getBlock())){
                                    makeLose(jumper);
                                }else{
                                    loc.setY(loc.getY()+1);
                                    while (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER){
                                        blocksOfRegionVerif.remove(loc.getBlock());
                                        loc.getBlock().setType(Material.EMERALD_BLOCK);
                                        blocks.add(loc.getBlock());
                                        loc.setY(loc.getY()+1);
                                    }
                                    loc = jumper.getLocation().getBlock().getLocation().clone();
                                    loc.setY(loc.getY()-1);
                                    while (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER){
                                        blocksOfRegionVerif.remove(loc.getBlock());
                                        loc.getBlock().setType(Material.EMERALD_BLOCK);
                                        blocks.add(loc.getBlock());
                                        loc.setY(loc.getY()-1);
                                    }
                                    blocksOfRegionVerif.remove(jumper.getLocation().getBlock());
                                    jumper.getLocation().getBlock().setType(Material.EMERALD_BLOCK);
                                    blocks.add(jumper.getLocation().getBlock());
                                    if(pl.getConfig().getBoolean("firework.isEnable")){
                                        new LaunchFireworks(pl.getDacPlugin(), jumper.getLocation().clone().add(0, 2, 0), pl.getConfig().getInt("firework.count"));
                                    }
                                    sendMessage(getFullName() +" §6"+jumper.getName()+"§d "+Message.MADE_PERFECT.getMessage());
                                    if (pl.otherPluginSupport.getVaultPlugin().getEconomy() != null) {
                                        pl.otherPluginSupport.getVaultPlugin().getEconomy().depositPlayer(jumper, 5d);
                                        jumper.sendMessage(getFullName() +" §aVous venez de gagner 5 CoinTies.");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "level giveexp "+jumper.getName()+" 2");
                                    }
                                    int lives = pl.getConfig().getInt("lives.max");
                                    livesOfPlayer.put(jumper, livesOfPlayer.get(jumper)+1);
                                    if(lives >= 1){
                                        if(livesOfPlayer.get(jumper) > lives){
                                            livesOfPlayer.put(jumper, livesOfPlayer.get(jumper)-1);
                                        }
                                    }
                                    jumper.sendMessage(getFullName() +" §a"+Message.SUCCESFULL_JUMP.getMessage());
                                    PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(jumper);
                                    playerStatistics.addJumps(1);
                                    playerStatistics.addPerfects(1);
                                    nextPlayer();
                                    updateScoreboards();
                                }
                            }else{
                                divingTimer = getOriginalDivingTimer();
                                Location loc = jumper.getLocation().getBlock().getLocation().clone();
                                if(a != null && !blocksOfRegion.contains(loc.getBlock())){
                                    makeLose(jumper);
                                }else{
                                    loc.setY(loc.getY()+1);
                                    while (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER){
                                        blocksOfRegionVerif.remove(loc.getBlock());
                                        loc.getBlock().setType(materialBlockOfPlayer.get(jumper));
                                        loc.getBlock().setData(dataBlockOfPlayer.get(jumper));
                                        blocks.add(loc.getBlock());
                                        loc.setY(loc.getY()+1);
                                    }
                                    loc = jumper.getLocation().getBlock().getLocation().clone();
                                    loc.setY(loc.getY()-1);
                                    while (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER){
                                        blocksOfRegionVerif.remove(loc.getBlock());
                                        loc.getBlock().setType(materialBlockOfPlayer.get(jumper));
                                        loc.getBlock().setData(dataBlockOfPlayer.get(jumper));
                                        blocks.add(loc.getBlock());
                                        loc.setY(loc.getY()-1);
                                    }
                                    blocksOfRegionVerif.remove(jumper.getLocation().getBlock());
                                    loc = jumper.getLocation().getBlock().getLocation().clone();
                                    loc.getBlock().setType(materialBlockOfPlayer.get(jumper));
                                    loc.getBlock().setData(dataBlockOfPlayer.get(jumper));
                                    blocks.add(jumper.getLocation().getBlock());
                                    jumper.sendMessage(getFullName() +" §a"+Message.SUCCESFULL_JUMP.getMessage());
                                    PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(jumper);
                                    playerStatistics.addJumps(1);
                                    nextPlayer(); }
                            }
                        }
                    }
                }
                count++;
                timer();
            }
        }, 1);
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(task);
        isStopping = true;
    }

    public void start(){
        startTimer = getOriginalStartTimer();
        divingTimer = getOriginalDivingTimer();
        sendMessage(getFullName() +" §a"+ Message.GAME_START.getMessage());
        game.WAIT = false;
        game.GAME = true;
        for(Player p : playerInGame){
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().remove(Material.BLAZE_POWDER);
        }
        nextPlayer();
        updateSigns();
    }

    public void nextPlayer(){
        if(jumper != null){
            jumper.setGameMode(GameMode.CREATIVE);
            jumper.teleport(pool);
            jumper.setGameMode(GameMode.ADVENTURE);
            playerInGame.remove(jumper);
            playerInGame.add(jumper);
        }
        if(playerInGame.size() != 0){
            jumper = playerInGame.get(0);
            jumper.setGameMode(GameMode.CREATIVE);
            jumper.teleport(diving);
            if(!isStopping){
                Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getDacPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        if(jumper != null){
                            jumper.setGameMode(GameMode.ADVENTURE);
                        }
                    }
                }, 5);
            }else{
                jumper.setGameMode(GameMode.ADVENTURE);
            }
            if(playerInGame.size() != 1){
                playerInGame.get(1).sendMessage(getFullName() +" §b"+Message.NEXT_TO_JUMP.getMessage());
            }
        }
        updateScoreboards();
    }

    public void onDisable(){
        stop();
        restart(false);
        for(Sign sign : signs){
            sign.setLine(0, "§c§l[§5"+name+"§c§l]");
            sign.setLine(1, "");
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();
        }
    }

    public void restart(boolean healPlayers){
        List<Player> list = Lists.newArrayList();
        for(Player p : playerInGame){
            if(healPlayers){
                Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getDacPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        p.setHealth(20);
                        p.setFoodLevel(20);
                    }
                },40);
            }
            list.add(p);
        }
        for(Player p : list){
            pl.dacs.removePlayer(p);
        }
        jumper = null;
        playerInGame.clear();
        blocksOfRegionVerif.clear();
        for(Block block : blocksOfRegion){
            blocksOfRegionVerif.add(block);
        }
        for(Block b : blocks){
            b.setType(Material.WATER);
        }
        game.WAIT = true;
        game.GAME = false;
        updateSigns();
    }

    public boolean addPlayer(Player p){
        if(!playerInGame.contains(p)){
            if(game.GAME){
                p.sendMessage("§c"+Message.IN_GAME.getMessage());
                return false;
            }
            if(playerInGame.size() >= max){
                p.sendMessage("§c"+Message.FULL_GAME.getMessage());
                return false;
            }
            playerInGame.add(p);
            livesOfPlayer.put(p, pl.getConfig().getInt("lives.min"));
            dataBlockOfPlayer.put(p, (byte) new Random().nextInt(15));
            materialBlockOfPlayer.put(p, blockTypes[new Random().nextInt(blockTypes.length)]);
            p.getInventory().clear();
            p.getInventory().setHeldItemSlot(1);
            ItemStack item = new ItemStack(Material.MAGMA_CREAM);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§c"+Message.LEAVE_THIS_GAME.getMessage());
            item.setItemMeta(meta);
            p.getInventory().addItem(item);
            item = new ItemStack(Material.BLAZE_POWDER);
            meta = item.getItemMeta();
            meta.setDisplayName("§2"+Message.BLOCK_CHOICE.getMessage());
            item.setItemMeta(meta);
            p.getInventory().setItem(8, item);
            p.updateInventory();
            sendMessage(getFullName() +" §6"+p.getName()+"§a "+Message.JOINED_THE_GAME.getMessage());
            updateSigns();
            updateScoreboards();
            p.setScoreboard(scoreboardSign.getScoreboard());
            p.setGameMode(GameMode.CREATIVE);
            p.teleport(lobby);
            p.setGameMode(GameMode.ADVENTURE);
            if(pl.getConfig().getBoolean("broadcast.comeJoin.enable")){
                int i = min;
                if(pl.getConfig().getInt("broadcast.comeJoin.number") != 0){
                    i = pl.getConfig().getInt("broadcast.comeJoin.number");
                }
                if(playerInGame.size() == i){
                    Bukkit.broadcastMessage(getFullName() +" §a"+Message.COME_JOIN.getMessage().replace("**", name));
                }
            }
            return true;
        }
        return false;
    }

    public void removePlayer(Player p){
        if(playerInGame.contains(p)){
            if(p == jumper){
                nextPlayer();
            }
            playerInGame.remove(p);
            livesOfPlayer.remove(p);
            dataBlockOfPlayer.remove(p);
            materialBlockOfPlayer.remove(p);
            p.getInventory().clear();
            p.updateInventory();
            updateSigns();
            updateScoreboards();
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            p.setGameMode(GameMode.CREATIVE);
            p.teleport(end);
            p.setGameMode(GameMode.ADVENTURE);
        }
    }

    public void open(){
        isOpen = true;
        updateSigns();
    }

    public void close(){
        isOpen = false;
        updateSigns();
    }

    public void sendMessage(String msg){
        for(Player p : playerInGame){
            p.sendMessage(msg);
        }
    }

    public void updateScoreboardTimers(int time){
        scoreboardSign.getObjective().setDisplayName(Message.ScoreboardMessage.NAME.getMessage().replace("**", name)+" "+Message.ScoreboardMessage.TIME_TAG.getMessage()+time);
    }

    public void updateSigns(){
        for(Sign sign : signs){
            if(!isOpen){
                sign.setLine(0, getFullName());
                sign.setLine(1, "");
                sign.setLine(2, Message.SignColorTag.CLOSE_LINE2.getColorTag()+Message.CLOSED.getMessage());
                sign.setLine(3, "");
                sign.update();
            }else{
                if(game.WAIT){
                    sign.setLine(0, getFullName());
                    if(playerInGame.size() >= (max/4)*3){
                        sign.setLine(1, Message.SignColorTag.OPEN_WAIT_LINE2_0.getColorTag()+playerInGame.size()+"/"+max);
                    }else if(playerInGame.size() >= max/2){
                        sign.setLine(1, Message.SignColorTag.OPEN_WAIT_LINE2_1.getColorTag()+playerInGame.size()+"/"+max);
                    }else {
                        sign.setLine(1, Message.SignColorTag.OPEN_WAIT_LINE2_2.getColorTag()+playerInGame.size()+"/"+max);
                    }
                    if(playerInGame.size() >= min){
                        sign.setLine(2, Message.SignColorTag.OPEN_WAIT_LINE3_0.getColorTag()+Message.READY.getMessage());
                    }else{
                        sign.setLine(2, Message.SignColorTag.OPEN_WAIT_LINE3_1.getColorTag()+(min-playerInGame.size())+" "+Message.MISSING.getMessage());
                    }
                    sign.setLine(3, Message.SignColorTag.OPEN_WAIT_LINE4.getColorTag()+Message.JOIN.getMessage());
                    sign.update();
                }else if(game.GAME){
                    sign.setLine(0, getFullName());
                    if(playerInGame.size() >= (max/4)*3){
                        sign.setLine(1, Message.SignColorTag.OPEN_GAME_LINE2_0.getColorTag()+playerInGame.size()+"/"+max);
                    }else if(playerInGame.size() >= max/2){
                        sign.setLine(1, Message.SignColorTag.OPEN_GAME_LINE2_1.getColorTag()+playerInGame.size()+"/"+max);
                    }else {
                        sign.setLine(1, Message.SignColorTag.OPEN_GAME_LINE2_2.getColorTag()+playerInGame.size()+"/"+max);
                    }
                    sign.setLine(2, "");
                    sign.setLine(3, Message.SignColorTag.OPEN_GAME_LINE4.getColorTag()+Message.IN_GAME2.getMessage());
                    sign.update();
                }
            }
        }
    }

    public void updateScoreboards(){
        scoreboardSign.clearLines();
        HashMap<Integer, List<Player>> pAndP = new HashMap<>();
        List<Integer> list = Lists.newArrayList();
        for(Player p : playerInGame){
            if(livesOfPlayer.containsKey(p)){
                int lives = livesOfPlayer.get(p);
                list.add(lives);
                if(!pAndP.containsKey(lives)){
                    pAndP.put(lives, Lists.newArrayList());
                }
                pAndP.get(lives).add(p);
            }
        }
        Collections.sort(list);
        HashMap<Integer, List<Player>> placeAndPlayer = new HashMap<>();
        for(int i : list){
            placeAndPlayer.put(i, pAndP.get(i));
        }
        int count = 0;
        for(int i : placeAndPlayer.keySet()){
            count++;
            for(Player p : placeAndPlayer.get(i)){
                String name = Message.ScoreboardMessage.NAME_TAG.getMessage()+p.getName();
                if(jumper != null){
                    if(jumper == p){
                        name = Message.ScoreboardMessage.JUMPER_TAG.getMessage()+"-> "+name;
                    }
                }
                scoreboardSign.setLine(livesOfPlayer.get(p), name);
            }
            if(count == 10){
                break;
            }
        }
    }

    public void registerNewSigns(World world) {
        for(Chunk c : world.getLoadedChunks()){
            for(BlockState state : c.getTileEntities()){
                if(state instanceof Sign){
                    Sign sign = (Sign) state;
                    if(sign.getLine(0).equals("§c§l[§5"+name+"§c§l]")){
                        if(!signs.contains(sign) && !pl.dacs.getSignsTaken().contains(sign)){
                            signs.add(sign);
                            pl.dacs.getSignsTaken().add(sign);
                        }
                    }
                }
            }
        }
        updateSigns();
    }

    public void registerSign(Sign sign){
        if(sign.getLine(0).equals("§c§l[§5"+name+"§c§l]")){
            if(!signs.contains(sign) && !pl.dacs.getSignsTaken().contains(sign)){
                signs.add(sign);
                pl.dacs.getSignsTaken().add(sign);
            }
        }
        updateSigns();
    }

    private List<Player> playersNoDamage = Lists.newArrayList();
    public void makeLose(Player p){
        if(pl.getConfig().getBoolean("firework.isEnable")){
            new LaunchFireworks(pl.getDacPlugin(), jumper.getLocation().clone().add(0, 2, 0), 1);
        }
        playersNoDamage.add(p);
        divingTimer = getOriginalDivingTimer();
        nextPlayer();
        livesOfPlayer.put(p, livesOfPlayer.get(p)-1);
        if(livesOfPlayer.get(p) == 0){
            sendMessage(getFullName()+" §6"+p.getName()+" §c"+Message.FAILED.getMessage());
            pl.dacs.removePlayer(p);
            PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(p);
            playerStatistics.addLoses(1);
            playerStatistics.addFails(1);
            playerStatistics.addGames(1);
            updateSigns();
        }else{
            sendMessage(getFullName() +" §6"+p.getName()+" §e"+Message.LOST_LIFE.getMessage());
        }
        if (pl.otherPluginSupport.getVaultPlugin().getEconomy() != null) {
            if (pl.getConfig().getDouble("rewards.lose") != 0) {
                pl.otherPluginSupport.getVaultPlugin().getEconomy().depositPlayer(getPlayerInGame().get(0), pl.getConfig().getDouble("rewards.lose"));
            }
        }
        updateScoreboards();
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(playerInGame.contains(p)){
                if(jumper == p){
                    e.setCancelled(true);
                    if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                        makeLose(p);
                    }
                }else{
                    e.setCancelled(true);
                }
            }else if(playersNoDamage.contains(p)){
                e.setCancelled(true);
                playersNoDamage.remove(p);
            }
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        if(playerInGame.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void breakBlock(BlockPlaceEvent e){
        if(playerInGame.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getItem() != null){
            ItemStack item = e.getItem();
            if(item.getType() == Material.MAGMA_CREAM){
                if(item.getItemMeta().getDisplayName() != null){
                    if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§c"+Message.LEAVE_THIS_GAME.getMessage())){
                        if(playerInGame.contains(p)){
                            sendMessage(getFullName()+" §6"+p.getName()+"§c "+Message.LEAVED.getMessage());
                            pl.dacs.removePlayer(p);
                            if(game.GAME){
                                PlayerStatistics playerStatistics = pl.statisticsOfPlayer.get(p);
                                playerStatistics.addGames(1);
                                playerStatistics.addLoses(1);
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }else if(item.getType() == Material.BLAZE_POWDER){
                if(item.getItemMeta().getDisplayName() != null){
                    if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§2"+Message.BLOCK_CHOICE.getMessage())){
                        if(playerInGame.contains(p)){
                            pl.blockChoice.openInventory(p, 0);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    public void setStartTimer(int timer){
        startTimer = timer;
    }

    private int getOriginalStartTimer(){
        return pl.getConfig().getInt("times.wait");
    }

    private int getOriginalDivingTimer(){
        return pl.getConfig().getInt("times.jump");
    }

    public List<Player> getPlayerInGame() {
        return playerInGame;
    }

    public void setBlockAndDataOfPlayer(Player p, Material matarial, Byte data){
        if(playerInGame.contains(p)){
            materialBlockOfPlayer.put(p, matarial);
            dataBlockOfPlayer.put(p, data);
        }
    }

    public Dac buildRegion(Location a, Location b){
        if(a == null || b == null){
            return this;
        }
        this.a = a;
        this.b = b;
        int size = 0;
        for(Block block : blocksFromTwoPoints(a, b)){
            if(block.getType().name().contains("WATER")){
                blocksOfRegionVerif.add(block);
                blocksOfRegion.add(block);
                size++;
            }
        }
        Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" §aRegion built for "+name+". §7("+size+" blocks found)");
        return this;
    }

    private List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = Lists.newArrayList();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public String getName() {
        return name;
    }

    public String getFullName(){
        return Message.ARENA_NAME.getMessage().replace("**", name);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public class Game{
        public boolean WAIT = true;
        public boolean GAME = false;
    }
}
