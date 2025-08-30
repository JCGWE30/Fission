package org.lepigslayer.fission.InventorySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.InventorySlot;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.SlotRendererComponent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class InventoryComponent {
    public static enum ClickResult{
        IGNORE,
        ALLOW,
        DENY
    }

    private record ClickAction(Runnable action, ClickType type) {
    }

    protected InventoryInstance instance;
    protected Player player;

    protected final void setSlot(int position, InventorySlot slot) {
        instance.getComponent(SlotRendererComponent.class).assignSlot(position,slot);
    }

    final void setInstance(InventoryInstance instance, Player player) {
        this.instance = instance;
        this.player = player;
    }

    public ClickResult handleInventoryClick(int slot, ClickType type) {
        return ClickResult.IGNORE;
    }

    public ClickResult handlePlayerClick(int slot, ClickType type) {
        return ClickResult.IGNORE;
    }

    public void update(){

    };

    public void reset(){

    }

    public abstract void initalize();
}
