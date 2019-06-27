package fr.naruse.deacoudre.common.configuration;

import com.google.common.collect.Lists;
import fr.naruse.deacoudre.main.DacPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CommandsConfiguration {
    private DacPlugin pl;
    private File commandsFile;
    private FileConfiguration commands;
    public CommandsConfiguration(DacPlugin dacPlugin) {
        this.pl = dacPlugin;
        createConfig();
    }

    private void createConfig(){
        commandsFile = new File(pl.getDataFolder(), "commands.yml");
        commands = new YamlConfiguration();
        try {
            if(!commandsFile.exists()){
                commandsFile.createNewFile();
            }
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §C There is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            commands.load(commandsFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        setDefault();
        saveConfig();
    }

    private void setDefault() {
        if(commands.getStringList("commands").size() == 0){
            commands.set("commands", Lists.newArrayList());
        }
    }

    public void saveConfig(){
        try {
            commands.save(commandsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        return this.commands;
    }
}
