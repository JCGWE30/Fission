package org.lepigslayer.fission.CustomInventory.Defaults;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.lepigslayer.fission.CustomInventory.CustomInventoryListener;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;

public class DefaultInventoryListener extends CustomInventoryListener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        handleClick(e);
    }

    protected final boolean handleClick(InventoryClickEvent e) {
        if (e.getInventory() != inventory)
            return false;
        e.setCancelled(true);

        if (e.getClickedInventory() != inventory)
            return false;

        CustomInventorySlot slot = slotMap.get(e.getSlot());

        if (slot != null)
            return holder.handleClick(slot);

        return false;
    }
}
