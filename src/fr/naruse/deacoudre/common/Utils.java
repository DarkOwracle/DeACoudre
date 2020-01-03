package fr.naruse.deacoudre.common;

import org.bukkit.Bukkit;

public class Utils {

    public static double getBukkitVersion(){
        if(Bukkit.getVersion().contains("1.15")){
            return 1.15;
        }
        if(Bukkit.getVersion().contains("1.14")){
            return 1.14;
        }
        if(Bukkit.getVersion().contains("1.13")){
            return 1.13;
        }
        if(Bukkit.getVersion().contains("1.12")){
            return 1.12;
        }
        if(Bukkit.getVersion().contains("1.11")){
            return 1.11;
        }
        if(Bukkit.getVersion().contains("1.10")){
            return 1.10;
        }
        if(Bukkit.getVersion().contains("1.9")){
            return 1.9;
        }
        if(Bukkit.getVersion().contains("1.8")){
            return 1.8;
        }
        return 0.0;
    }
}
