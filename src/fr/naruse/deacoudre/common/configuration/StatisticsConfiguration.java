package fr.naruse.deacoudre.common.configuration;

import fr.naruse.deacoudre.main.DacPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class StatisticsConfiguration {
    private DacPlugin pl;
    private File statisticsFile;
    private FileConfiguration statistics;
    public StatisticsConfiguration(DacPlugin dacPlugin) {
        this.pl = dacPlugin;
        createConfig();
    }

    private void createConfig(){
        statisticsFile = new File(pl.getDataFolder(), "statistics.yml");
        statistics = new YamlConfiguration();
        try{
            if(!statisticsFile.exists()){
                statisticsFile.createNewFile();
            }
        } catch (UnsupportedEncodingException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[DAC] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            statistics.load(statisticsFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        saveConfig();
    }

    public void saveConfig(){
        try {
            statistics.save(statisticsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getStatistics() {
        return statistics;
    }
}
