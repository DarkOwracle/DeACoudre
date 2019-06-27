package fr.naruse.deacoudre.common.configuration;

import fr.naruse.deacoudre.manager.AbstractDacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.v1_12.util.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

public class ManagerConfiguration {
    private AbstractDacPlugin pl;
    private File managerFile;
    private FileConfiguration manager;
    public ManagerConfiguration(AbstractDacPlugin dacPlugin) {
        this.pl = dacPlugin;
        createConfig();
    }

    private void createConfig(){
        managerFile = new File(pl.getDataFolder(), "manager.yml");
        manager = new YamlConfiguration();
        try{
            if(!managerFile.exists()){
                managerFile.createNewFile();
            }
            Reader defConfigStream;
            defConfigStream = new InputStreamReader(pl.getResource("other/manager.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            manager.setDefaults(defConfig);
        } catch (UnsupportedEncodingException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Manager.yml. You should perform a reload.");
            e.printStackTrace();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Manager.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            manager.load(managerFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        saveConfig();
    }

    public void saveConfig(){
        try {
            manager.save(managerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearConfiguration(){
        managerFile.delete();
        createConfig();
        if(pl instanceof DacPluginV1_12){
            if(((DacPluginV1_12) pl).manager != null){
                setDefault(((DacPluginV1_12) pl).manager.getQuestionsAndAnswers());
            }
        }
    }

    public FileConfiguration getConfig(){
        return this.manager;
    }

    public void setDefault(List<Manager.QuestionsAndAnswers> questions) {
        manager.set("settings.name", manager.get("settings.name"));
        manager.set("settings.prefix", manager.get("settings.prefix"));
        manager.set("settings.chat", manager.get("settings.chat"));
        manager.set("settings.namesOfQuestions", manager.get("settings.namesOfQuestions"));
        for(Manager.QuestionsAndAnswers qAndA : questions){
            manager.set(qAndA.getName()+".arguments.french", manager.get(qAndA.getName()+".arguments.french"));
            manager.set(qAndA.getName()+".arguments.english", manager.get(qAndA.getName()+".arguments.english"));
            manager.set(qAndA.getName()+".arguments.spanish", manager.get(qAndA.getName()+".arguments.spanish"));
            manager.set(qAndA.getName()+".answers.french", manager.get(qAndA.getName()+".answers.french"));
            manager.set(qAndA.getName()+".answers.english", manager.get(qAndA.getName()+".answers.english"));
            manager.set(qAndA.getName()+".answers.spanish", manager.get(qAndA.getName()+".answers.spanish"));
        }
        saveConfig();
    }
}
