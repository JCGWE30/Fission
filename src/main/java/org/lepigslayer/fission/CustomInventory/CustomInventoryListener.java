package org.lepigslayer.fission.CustomInventory;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class CustomInventoryListener extends CustomInventoryModule implements Listener  {
    void activate(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    void deactivate() {
        HandlerList.unregisterAll(this);
    }
}
