package fr.naruse.deacoudre.manager;

import fr.naruse.deacoudre.main.DacPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.InputStream;

public abstract class AbstractDacPlugin {
    private DacPlugin dacPlugin;
    public AbstractDacPlugin(DacPlugin pl){
        this.dacPlugin = pl;
        Bukkit.getConsoleSender().sendMessage("§c§l[§3DAC§c§l] §6AbstractVersion : Using Spigot "+Bukkit.getBukkitVersion()+".");
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onLoad();

    public FileConfiguration getConfig(){
        return this.dacPlugin.getConfig();
    }

    public void saveConfig(){
        this.dacPlugin.saveConfig();
    }

    public PluginCommand getCommand(String name){
        return this.dacPlugin.getCommand(name);
    }

    public File getDataFolder(){
        return this.dacPlugin.getDataFolder();
    }

    public InputStream getResource(String name){
        return this.dacPlugin.getResource(name);
    }

    public DacPlugin getDacPlugin() {
        return dacPlugin;
    }
}
