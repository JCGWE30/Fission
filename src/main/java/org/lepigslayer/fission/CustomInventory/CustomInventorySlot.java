package org.lepigslayer.fission.CustomInventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class CustomInventorySlot extends CustomInventoryModule {
    public abstract ItemStack getItem();
    public abstract void triggerClick(ClickType clickType);
}
