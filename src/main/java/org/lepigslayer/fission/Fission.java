package org.lepigslayer.fission;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.lepigslayer.fission.CustomCommand.CustomCommandHandler;
import org.lepigslayer.fission.CustomInventory.CustomInventoryManager;
import org.lepigslayer.fission.Testing.CustomCommand.TestCustomCommand;
import org.lepigslayer.fission.Testing.CustomInventory.CustomInventoryTester;
import org.lepigslayer.fission.Testing.RunTestsCommand;

public final class Fission extends JavaPlugin {

    private static Fission instance;

    @Override
    public void onEnable() {
        instance = this;
        registerEvents(CustomInventoryManager.class);

        getCommand("runtests").setExecutor(new CustomCommandHandler(new TestCustomCommand()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(Class<? extends Listener> listenerClass) {
        try {
            Listener listenerObject = listenerClass.getDeclaredConstructor().newInstance();
            Bukkit.getPluginManager().registerEvents(listenerObject, this);
        }catch(Exception e){
            log("Failed to register listener: " + listenerClass.getName());
        }
    }

    public static void log(String msg) {
        instance.getLogger().info(msg);
    }
}
