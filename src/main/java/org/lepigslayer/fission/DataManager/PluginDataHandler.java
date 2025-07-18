package org.lepigslayer.fission.DataManager;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PluginDataHandler {
    private Gson gson;
    private JavaPlugin plugin;

    private Map<Class<?>, StaticData<?>> staticClasses;
    private Map<Class<?>, InstancedData<?>> instancedClasses;

    PluginDataHandler(JavaPlugin plugin, Gson gson){
        this.staticClasses = new HashMap<>();
        this.instancedClasses = new HashMap<>();

        this.gson = gson;
        this.plugin = plugin;
    }

    <T> void registerStatic(Class<T> clazz, String path, String name){
        StaticData<T> staticData = new StaticData<>(clazz, new File(plugin.getDataFolder(),path), name);
        staticClasses.put(clazz, staticData);

        try {
            staticData.loadData(gson);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    <T> void registerInstanced(Class<T> clazz, String path){
        InstancedData<T> instancedData = new InstancedData<>(clazz,new File(plugin.getDataFolder(),path));
        instancedClasses.put(clazz, instancedData);
    }

    <T> T getStatic(Class<T> clazz){
        if(!staticClasses.containsKey(clazz))
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not registered");

        return staticClasses.get(clazz).getData(clazz);
    }

    <T> T getInstanced(Class<T> clazz, String name){
        if(!instancedClasses.containsKey(clazz))
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not registered");

        return instancedClasses.get(clazz).getData(clazz,name,gson);
    }

    void saveStatic(Class<?> clazz){
        try {
            if (!staticClasses.containsKey(clazz))
                throw new IllegalArgumentException("Class " + clazz.getName() + " is not registered");

            StaticData<?> staticData = staticClasses.get(clazz);
            staticData.saveData(gson);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    void saveInstanced(Class<?> clazz){
        try{
            if(!instancedClasses.containsKey(clazz))
                throw new IllegalArgumentException("Class " + clazz.getName() + " is not registered");

            InstancedData<?> instancedData = instancedClasses.get(clazz);
            instancedData.saveData(gson);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    void save(){
        staticClasses.forEach((k,v)-> saveStatic(k));
        instancedClasses.forEach((k,v)-> saveInstanced(k));
    }
}
