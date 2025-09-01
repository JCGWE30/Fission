package org.lepigslayer.fission.InventorySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.lepigslayer.fission.Fission;
import org.lepigslayer.fission.InventorySystem.InventoryChain.InventoryChainManager;
import org.lepigslayer.fission.Utilities.TimeUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class InventorySystemManager implements Listener {
    private static Map<Player, InventoryInstance> openInventories = new HashMap<>();
    private static Set<Player> switchingPlayers = new HashSet<>();

    public InventorySystemManager() {
        TimeUtils.runTask(Fission.class, this::update, 10);
    }

    private void update() {
        openInventories.values().forEach(InventoryInstance::updateInventory);
    }

    public static void openInventory(Player player, InventoryInstance inventory) {
        if (openInventories.containsKey(player))
            InventoryChainManager.enqueue(player, openInventories.get(player));
        setInventory(player, inventory);
    }

    public static void setInventory(Player player, InventoryInstance inventory) {
        openInventories.put(player, inventory);

        switchingPlayers.add(player);
        player.openInventory(inventory.getInventory());
        switchingPlayers.remove(player);

    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player player = ((Player) e.getWhoClicked());

        if (!openInventories.containsKey(player))
            return;

        openInventories.get(player).handleClick(e);
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        if(switchingPlayers.contains(e.getPlayer()))
            return;

        if (!openInventories.get(e.getPlayer()).handleClose(e)) {
            openInventories.remove(e.getPlayer());
            InventoryChainManager.wipe(((Player) e.getPlayer()));
            return;
        }

        openInventories.get(e.getPlayer()).open();
    }
}
