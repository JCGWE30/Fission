package org.lepigslayer.fission.InventorySystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
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
    private static Set<Player> processingPlayers = new HashSet<>();

    public InventorySystemManager() {
        TimeUtils.runTask(Fission.class, this::update, 1);
    }

    private void update() {
        openInventories.values().forEach(InventoryInstance::updateInventory);
    }

    public static void openInventory(Player player, InventoryInstance inventory) {
        if(processingPlayers.contains(player))
            return;
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

    public static void forceCloseInventory(Player player) {
        openInventories.remove(player);
        processingPlayers.remove(player);
        switchingPlayers.remove(player);
        player.closeInventory();
        InventoryChainManager.wipe(player);
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player player = ((Player) e.getWhoClicked());

        if (!openInventories.containsKey(player))
            return;

        try {
            openInventories.get(player).handleClick(e);
        }catch (Exception ex) {
            e.setCancelled(true);
            ex.printStackTrace();
            e.getWhoClicked().sendMessage("§cAn error occurred, please report this!");
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if(!openInventories.containsKey(p))
            return;

        if (switchingPlayers.contains(p))
            return;

        InventoryInstance inventory = openInventories.get(p);

        openInventories.remove(p);

        processingPlayers.add(p);
        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Fission.class), () -> {
            if(!processingPlayers.contains(p))
                return;

            processingPlayers.remove(p);
            openInventories.put(p, inventory);
            InventoryComponent.CloseResult result = inventory.handleClose(e);

            if (result == InventoryComponent.CloseResult.HANDLED)
                return;

            if (result == InventoryComponent.CloseResult.REOPEN) {
                p.openInventory(inventory.getInventory());
                return;
            }

            openInventories.remove(p, inventory);
            InventoryChainManager.wipe(p);
        }, 3);
    }
}
