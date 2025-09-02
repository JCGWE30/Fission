package org.lepigslayer.fission.InventorySystem.InventoryChain;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lepigslayer.fission.InventorySystem.InventoryInstance;
import org.lepigslayer.fission.InventorySystem.InventorySystemManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class InventoryChainManager implements Listener {
    private static Map<Player, Deque<InventoryInstance>> chains = new HashMap<>();

    public static boolean hasPrevious(Player player) {
        return !chains.get(player).isEmpty();
    }

    public static InventoryInstance getPrevious(Player player) {
        return chains.get(player).peek();
    }

    public static void traverse(Player player) {
        Deque<InventoryInstance> chain = chains.get(player);

        if (chain.isEmpty())
            return;

        InventoryInstance inventory = chain.pop();
        inventory.refresh();
        InventorySystemManager.setInventory(player, inventory);
    }

    public static void wipe(Player player) {
        chains.get(player).clear();
    }

    public static void enqueue(Player player, InventoryInstance inventory) {
        Deque<InventoryInstance> chain = chains.get(player);
        chain.push(inventory);
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        chains.put(e.getPlayer(), new ArrayDeque<>());
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        chains.remove(e.getPlayer());
    }
}
