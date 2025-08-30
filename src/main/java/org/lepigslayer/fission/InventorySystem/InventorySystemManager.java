package org.lepigslayer.fission.InventorySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.lepigslayer.fission.Fission;
import org.lepigslayer.fission.Utilities.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public final class InventorySystemManager implements Listener {
    private static Map<Player, InventoryInstance> openInventories = new HashMap<>();

    public InventorySystemManager() {
        TimeUtils.runTask(Fission.class,this::update,10);
    }

    private void update(){
        openInventories.values().forEach(InventoryInstance::updateInventory);
    }

    static void openInventory(Player player, InventoryInstance inventory) {
        openInventories.put(player, inventory);
        player.openInventory(inventory.getInventory());
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player player = ((Player) e.getWhoClicked());

        if(!openInventories.containsKey(player))
            return;

        openInventories.get(player).handleClick(e);
    }
}
