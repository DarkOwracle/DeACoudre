package fr.naruse.deacoudre.main;

import fr.naruse.deacoudre.common.Utils;
import fr.naruse.deacoudre.manager.AbstractDacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;
import fr.naruse.deacoudre.manager.DacPluginV1_13;
import fr.naruse.deacoudre.v1_12.util.DACPlaceHolder;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DacPlugin extends JavaPlugin {
    private AbstractDacPlugin dacPlugin;
    public static DacPlugin INSTANCE;
    @Override
    public void onEnable(){
        super.onEnable();
        INSTANCE = this;
        if(dacPlugin == null){
            double version = Utils.getBukkitVersion();
            if(version >= 1.13) {
                this.dacPlugin = new DacPluginV1_13(this);
            }else {
                this.dacPlugin = new DacPluginV1_12(this);
            }
        }
        this.dacPlugin.onEnable();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        this.dacPlugin.onDisable();
    }

    public AbstractDacPlugin getDacPlugin() {
        return dacPlugin;
    }
}
