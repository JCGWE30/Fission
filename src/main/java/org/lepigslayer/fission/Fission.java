package org.lepigslayer.fission;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.lepigslayer.fission.CustomInventory.CustomInventoryManager;
import org.lepigslayer.fission.DataManager.DataManager;
import org.lepigslayer.fission.InventorySystem.InventorySystemManager;
import org.lepigslayer.fission.ScoreboardManager.ScoreboardManager;
import org.lepigslayer.fission.Testing.RunTestsCommand;

public final class Fission extends JavaPlugin {

    private static Fission instance;

    @Override
    public void onEnable() {
        instance = this;

        registerEvents(InventorySystemManager.class);
        registerEvents(CustomInventoryManager.class);
        registerEvents(ScoreboardManager.class);
    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
    }

    public static void registerEvents(Class<? extends Listener> listenerClass) {
        try {
            JavaPlugin plugin = JavaPlugin.getProvidingPlugin(listenerClass);
            Listener listenerObject = listenerClass.getDeclaredConstructor().newInstance();
            Bukkit.getPluginManager().registerEvents(listenerObject, plugin);
        }catch(Exception e){
            log("Failed to register listener: " + listenerClass.getName());
        }
    }

    public static void log(String msg) {
        instance.getLogger().info(msg);
    }
}
