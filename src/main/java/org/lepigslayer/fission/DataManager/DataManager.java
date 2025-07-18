package org.lepigslayer.fission.DataManager;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DataManager {
    private static Map<JavaPlugin, PluginDataHandler> pluginHandlers;

    static{
        pluginHandlers = new HashMap<>();
    }

    public static void registerStatic(Class<?> dataClass, String filePath, String name){
        PluginDataHandler handler = enforcePlugin(dataClass);
        handler.registerStatic(dataClass, filePath, name);
    }

    public static <T> void registerInstanced(Class<T> dataClass, String filePath){
        PluginDataHandler handler = enforcePlugin(dataClass);
        handler.registerInstanced(dataClass,filePath);
    }

    public static void registerPlugin(JavaPlugin plugin, Gson gson){
        pluginHandlers.put(plugin, new PluginDataHandler(plugin,gson));
    }

    public static <T> T getStatic(Class<T> dataClass){
        PluginDataHandler handler = enforcePlugin(dataClass);
        return handler.getStatic(dataClass);
    }

    public static <T> T getInstanced(Class<T> dataClass, String name){
        PluginDataHandler handler = enforcePlugin(dataClass);
        return handler.getInstanced(dataClass,name);
    }

    public static <T> List<T> getAllInstanced(Class<T> dataClass){
        PluginDataHandler handler = enforcePlugin(dataClass);
        return handler.getAllInstanced(dataClass);
    }

    public static boolean instancedExists(Class<?> dataClass, String name){
        PluginDataHandler handler = enforcePlugin(dataClass);
        return handler.instancedExists(dataClass,name);
    }

    public static void saveAll(){
        pluginHandlers.keySet().forEach(DataManager::save);
    }

    public static void save(JavaPlugin plugin){
        pluginHandlers.get(plugin).save();
    }

    private static PluginDataHandler enforcePlugin(Class<?> dataClass){
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(dataClass);
        if(!pluginHandlers.containsKey(plugin))
            throw new IllegalArgumentException("Plugin is not registered");

        return pluginHandlers.get(plugin);
    }
}
