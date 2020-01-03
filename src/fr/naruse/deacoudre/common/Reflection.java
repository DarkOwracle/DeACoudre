package fr.naruse.deacoudre.common;

import com.google.common.collect.Lists;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.util.List;

public class Reflection {

    public static Material[] getById(){
        try {
            Class materialClass = Class.forName("org.bukkit.Material");
            Field byIdField = materialClass.getDeclaredField("byId");
            byIdField.setAccessible(true);
            return (Material[]) byIdField.get(materialClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
