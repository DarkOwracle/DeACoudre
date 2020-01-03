package fr.naruse.deacoudre.v1_12.util.manager;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.v1_12.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Manager extends BukkitRunnable implements Listener {
    private DacPluginV1_12 pl;
    private Location location;
    private Parrot manager;
    private String name;
    private String prefix;
    private String chat;
    private Random random;
    private List<QuestionsAndAnswers> questionsAndAnswers = Lists.newArrayList();
    private HashMap<String[], QuestionsAndAnswers> questionsAndAnswersOfArguments = new HashMap<>();
    private List<String[]> allArguments = Lists.newArrayList();
    public Manager(DacPluginV1_12 pl, Location location){
        this.pl = pl;
        for(Entity e : location.getWorld().getEntities()){
            if(e instanceof Parrot){
                e.remove();
            }
        }
        this.random = new Random();
        this.location = location;
        this.manager = (Parrot) location.getWorld().spawnEntity(location, EntityType.PARROT);
        this.name = pl.configurations.getManager().getConfig().getString("settings.name").replace("&", "§");
        this.prefix = pl.configurations.getManager().getConfig().getString("settings.prefix").replace("&", "§");
        this.chat = replacer(pl.configurations.getManager().getConfig().getString("settings.chat"), "");
        manager.setCustomName(prefix+" "+name);
        manager.setCustomNameVisible(true);
        generateQuestionsAndAnswers();
        this.runTaskTimer(pl.getDacPlugin(), 20, 20);
    }

    private void sendMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(msg);
        }
    }

    @Override
    public void run() {
        if(manager.getLocation().distance(location) >= 40){
            manager.teleport(location);
        }
        double closest = 50;
        Player target = null;
        for(Player p : Bukkit.getOnlinePlayers()){
            double d = p.getLocation().distance(manager.getLocation());
            if(d < closest){
                closest = d;
                target = p;
            }
        }
        manager.setTarget(target);
        for(Player p : answerOfPlayer.keySet()){
            int temp = timeOfPlayer.get(p);
            if(temp != 0){
                temp--;
                timeOfPlayer.put(p, temp);
            }else if(temp == 0){
                needToClear.add(p);
                sendMessage(chat+" "+answerOfPlayer.get(p));
            }
        }
        for(Player p : needToClear){
            answerOfPlayer.remove(p);
            timeOfPlayer.remove(p);
        }
        needToClear.clear();
    }

    private void generateQuestionsAndAnswers(){
        List<String> paths = pl.configurations.getManager().getConfig().getStringList("settings.namesOfQuestions");
        int count = 0;
        for(int i = 0; i != paths.size(); i++){
            String qName = paths.get(i);
            if(pl.configurations.getManager().getConfig().getString(qName) != null){
                if(pl.configurations.getManager().getConfig().getStringList(qName+".arguments") != null) {
                    if(pl.configurations.getManager().getConfig().getStringList(qName+".answers") != null) {
                        QuestionsAndAnswers qAndA = new QuestionsAndAnswers(qName);
                        if(qAndA.registerQuestionAndAnswers()){
                            questionsAndAnswers.add(qAndA);
                            questionsAndAnswersOfArguments.put(qAndA.getArguments(), qAndA);
                            count++;
                        }
                    }
                }
            }
        }
        for(QuestionsAndAnswers qAndA : questionsAndAnswers){
            allArguments.add(qAndA.getArguments());
        }
        pl.configurations.getManager().setDefault(questionsAndAnswers);
        Bukkit.getConsoleSender().sendMessage(Message.DAC.getMessage()+" "+chat+" §aI found "+count+" questions.");
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();
        QuestionsAndAnswers qAndA = null;
        int lastCorrectArguments = 0;
        for(String[] arguments : allArguments){
            boolean containsArgs = true;
            int correctArguments = 0;
            List<String> orArgs = Lists.newArrayList();
            List<String> andArgs = Lists.newArrayList();
            for(String arg : arguments){
                arg = replacer(arg, "").toLowerCase();
                if(arg.toCharArray().length < 2){
                    andArgs.add(arg);
                }else {
                    if((arg.toCharArray()[0]+"").equalsIgnoreCase("|") && (arg.toCharArray()[1]+"").equalsIgnoreCase("|")){
                        orArgs.add(arg.replace("||", ""));
                    }else{
                        andArgs.add(arg);
                    }
                }
            }
            boolean onArgsAreTrue = true;
            int notInMsg = 0;
            for(String orArg : orArgs){
                if(!msg.contains(orArg)){
                    notInMsg++;
                    correctArguments++;
                }
            }
            if((notInMsg == orArgs.size()) && orArgs.size() != 0){
                onArgsAreTrue = false;
            }
            for(String andArg : andArgs){
                if(!msg.contains(andArg.toLowerCase())){
                    containsArgs = false;
                }else{
                    correctArguments++;
                }
            }
            if(!onArgsAreTrue){
                containsArgs = false;
            }
            if(containsArgs){
                if(questionsAndAnswersOfArguments.containsKey(arguments)){
                    if(lastCorrectArguments < correctArguments){
                        lastCorrectArguments = correctArguments;
                        qAndA = questionsAndAnswersOfArguments.get(arguments);
                    }
                }
            }
        }
        if(qAndA == null){
            return;
        }
        if(qAndA.getName().equalsIgnoreCase("callManager")){
            answerOfPlayer.put(p, qAndA.getRandomAnswer(p.getName()));
            timeOfPlayer.put(p, 2);
            if(!askedaQuestion.contains(p)){
                askedaQuestion.add(p);
            }
            return;
        }
        if(!askedaQuestion.contains(p)){
            if(!msg.contains(name.toLowerCase()) && !msg.contains("manager")){
                return;
            }
        }
        askedaQuestion.remove(p);
        answerOfPlayer.put(p, qAndA.getRandomAnswer(p.getName()));
        timeOfPlayer.put(p, 2);
    }

    @EventHandler
    public void item(EntityDeathEvent e){
        if(e.getEntity() == manager){
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void teleport(EntityTeleportEvent e){
        if(e.getEntity() instanceof Parrot){
            if(e.getEntity() == manager){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(e.getEntity() instanceof Parrot){
            if(e.getEntity() == manager){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void block(EntityChangeBlockEvent e){
        if(e.getEntity() instanceof Parrot){
            if(e.getEntity() == manager){
                e.setCancelled(true);
            }
        }
    }

    private String replacer(String s, String playerName){
        return s.replace("&", "§").replace("{managerName}", name).replace("{playerName}", playerName).replace("{prefix}", prefix);
    }

    private HashMap<Player, String> answerOfPlayer = new HashMap<>();
    private HashMap<Player, Integer> timeOfPlayer = new HashMap<>();
    private List<Player> askedaQuestion = Lists.newArrayList();
    private List<Player> needToClear = Lists.newArrayList();

    private Vector genVector(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);
        Vector vector = new Vector(x, z, y);
        return vector;
    }

    public List<QuestionsAndAnswers> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public void onDisable(){
        manager.setHealth(0);
    }

    public void setDir(Location loc){
        manager.getLocation().setDirection(genVector(manager.getLocation(), loc));
    }

    public class QuestionsAndAnswers{
        private String[] arguments = null;
        private String[] answers = null;
        private String name;
        public QuestionsAndAnswers(String name){
            this.name = name;
        }

        public boolean registerQuestionAndAnswers(){
            if(pl.language.equalsIgnoreCase("fr")){
                if(pl.configurations.getManager().getConfig().getStringList(name+".arguments.french") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the question ! §7("+name+")");
                    return false;
                }
                if(pl.configurations.getManager().getConfig().getStringList(name+".answers.french") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the answers ! §7("+name+")");
                    return false;
                }
                arguments = pl.configurations.getManager().getConfig().getStringList(name+".arguments.french").toArray(new String[]{});
                answers = pl.configurations.getManager().getConfig().getStringList(name+".answers.french").toArray(new String[]{});
            }else if(pl.language.equalsIgnoreCase("en")){
                if(pl.configurations.getManager().getConfig().getStringList(name+".arguments.english") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the question ! §7("+name+")");
                    return false;
                }
                if(pl.configurations.getManager().getConfig().getStringList(name+".answers.english") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the answers ! §7("+name+")");
                    return false;
                }
                arguments = pl.configurations.getManager().getConfig().getStringList(name+".arguments.english").toArray(new String[]{});
                answers = pl.configurations.getManager().getConfig().getStringList(name+".answers.english").toArray(new String[]{});
            }else{
                if(pl.configurations.getManager().getConfig().getStringList(name+".arguments.spanish") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the question ! §7("+name+")");
                    return false;
                }
                if(pl.configurations.getManager().getConfig().getStringList(name+".answers.spanish") == null){
                    Bukkit.getConsoleSender().sendMessage(chat+" I don't find the answers ! §7("+name+")");
                    return false;
                }
                arguments = pl.configurations.getManager().getConfig().getStringList(name+".arguments.spanish").toArray(new String[]{});
                answers = pl.configurations.getManager().getConfig().getStringList(name+".answers.spanish").toArray(new String[]{});
            }
            if(arguments == null || answers == null){
                return false;
            }
            return true;
        }
        public String getRandomAnswer(String playerName){
            if(answers.length != 0){
                return replacer(answers[random.nextInt(answers.length)], playerName);
            }
            return "";
        }

        public String[] getAnswers() {
            return answers;
        }

        public String[] getArguments() {
            return arguments;
        }

        public String getName() {
            return name;
        }
    }
}
