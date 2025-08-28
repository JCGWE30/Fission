package org.lepigslayer.fission.CustomInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public abstract class CustomInventoryRenderer extends CustomInventoryModule {
    public abstract void render();
    public void deactivate(){}
    public void slotUpdate(int slot){}
}
