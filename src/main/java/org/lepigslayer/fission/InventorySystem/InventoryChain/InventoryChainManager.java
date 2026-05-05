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
    private static Map<Player, PlayerChain> chains = new HashMap<>();

    public static boolean hasPrevious(Player player) {
        return !chains.get(player).getChainStack().isEmpty();
    }

    public static InventoryInstance getPrevious(Player player) {
        return chains.get(player).getChainStack().peek();
    }

    public static void traverse(Player player) {
        Deque<InventoryInstance> chain = chains.get(player).getChainStack();

        if (chain.isEmpty()){
            player.closeInventory();
            return;
        }

        InventoryInstance inventory = chain.pop();
        inventory.refresh();
        InventorySystemManager.setInventory(player, inventory);
    }

    public static void wipe(Player player) {
        chains.get(player).getChainStack().clear();
    }

    public static void pushChain(Player player) {
        chains.get(player).push();
    }

    public static void popChain(Player player) {
        chains.get(player).pop();
    }

    public static void enqueue(Player player, InventoryInstance inventory) {
        Deque<InventoryInstance> chain = chains.get(player).getChainStack();
        chain.push(inventory);
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        chains.put(e.getPlayer(), new PlayerChain());
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        chains.remove(e.getPlayer());
    }
}
