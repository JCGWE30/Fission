package org.lepigslayer.fission.CustomInventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.lepigslayer.fission.Utilities.TimeUtils;

import java.lang.reflect.Method;
import java.util.*;

public class CustomInventoryManager implements Listener {
    private static CustomInventoryManager instance;

    private final HashMap<UUID, Stack<CustomInventory>> openInventories;
    
    public CustomInventoryManager() {
        instance = this;
        openInventories = new HashMap<>();
    }

    static void registerInventory(Entity player, CustomInventory inventory, boolean discrete) {
        Stack<CustomInventory> inventoryStack = instance.openInventories
                .computeIfAbsent(player.getUniqueId(), k -> new Stack<>());

        if(discrete)
            inventoryStack.pop();

        inventoryStack.push(inventory);
    }

    @EventHandler
    private void inventoryClose(InventoryCloseEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        if(!(e.getInventory().getHolder() instanceof CustomInventory customInventory))
            return;

        customInventory.handleClose();

        if(openInventories.containsKey(uuid)) {
            Stack<CustomInventory> playerStack = openInventories.get(uuid);

            if(playerStack.peek() != e.getInventory().getHolder())
                return;

            playerStack.pop();

            if(playerStack.isEmpty()){
                openInventories.remove(uuid);
                return;
            }

            TimeUtils.nextTick(customInventory.getClass(),
                    ()->playerStack.peek().reOpenInventory((Player) e.getPlayer())
            );
        }
    }

    @EventHandler
    private void disconnect(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        openInventories.remove(uuid);
    }
}
