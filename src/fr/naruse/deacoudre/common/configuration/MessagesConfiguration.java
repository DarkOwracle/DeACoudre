package fr.naruse.deacoudre.common.configuration;

import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.v1_12.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class MessagesConfiguration {
    private DacPlugin pl;
    private File messagesFile;
    private FileConfiguration messages;
    public MessagesConfiguration(DacPlugin dacPlugin) {
        this.pl = dacPlugin;
        createConfig(false);
    }

    private void createConfig(boolean empty){
        messagesFile = new File(pl.getDataFolder(), "messages.yml");
        messages = new YamlConfiguration();
        try{
            if(!messagesFile.exists()){
                messagesFile.createNewFile();
            }
            if(!empty){
                Reader defConfigStream;
                defConfigStream = new InputStreamReader(pl.getResource(langFileName()), "UTF8");
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            messages.load(messagesFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        saveConfig();
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            @Override
            public void run() {
                setDefault();
            }
        },20);
    }

    public void saveConfig(){
        try {
            messages.save(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String langFileName(){
        if(pl.getConfig().getString("language").equalsIgnoreCase("fr")){
            return "other/languages/french.yml";
        }
        return "other/languages/english.yml";
    }

    public FileConfiguration getMessages(){
        return this.messages;
    }

    private void setDefault() {
        if(pl.getDacPlugin() instanceof DacPluginV1_12){
            for(Message msg : Message.values()){
                if(messages.getString(msg.getPath()) == null){
                    messages.set(msg.getPath(), msg.getEnglishMessage());
                }else{
                    messages.set(msg.getPath(), messages.getString(msg.getPath()));
                }
            }
            for(Message.SignColorTag sct : Message.SignColorTag.values()){
                if(messages.getString(sct.getPath()) == null){
                    messages.set(sct.getPath(), sct.getColorTag());
                }else{
                    messages.set(sct.getPath(), messages.getString(sct.getPath()));
                }
            }
            for(Message.ScoreboardMessage sm : Message.ScoreboardMessage.values()){
                if(messages.getString(sm.getPath()) == null){
                    messages.set(sm.getPath(), sm.getMessage());
                }else{
                    messages.set(sm.getPath(), messages.getString(sm.getPath()));
                }
            }
        }else{
            for(fr.naruse.deacoudre.v1_13.util.Message msg : fr.naruse.deacoudre.v1_13.util.Message.values()){
                if(messages.getString(msg.getPath()) == null){
                    messages.set(msg.getPath(), msg.getEnglishMessage());
                }else{
                    messages.set(msg.getPath(), messages.getString(msg.getPath()));
                }
            }
            for(fr.naruse.deacoudre.v1_13.util.Message.SignColorTag sct : fr.naruse.deacoudre.v1_13.util.Message.SignColorTag.values()){
                if(messages.getString(sct.getPath()) == null){
                    messages.set(sct.getPath(), sct.getColorTag());
                }else{
                    messages.set(sct.getPath(), messages.getString(sct.getPath()));
                }
            }
            for(fr.naruse.deacoudre.v1_13.util.Message.ScoreboardMessage sm : fr.naruse.deacoudre.v1_13.util.Message.ScoreboardMessage.values()){
                if(messages.getString(sm.getPath()) == null){
                    messages.set(sm.getPath(), sm.getMessage());
                }else{
                    messages.set(sm.getPath(), messages.getString(sm.getPath()));
                }
            }
        }
        saveConfig();
    }

    public void clearConfiguration(){
        messagesFile.delete();
    }

    public void generateConfig(boolean empty){
        createConfig(empty);
    }
}
