package org.lepigslayer.fission.InventorySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.InventorySlot;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.SlotRendererComponent;

public abstract class InventoryComponent {
    public static enum ClickResult {
        IGNORE,
        ALLOW,
        DENY
    }
    public static enum CloseResult {
        IGNORE,
        REOPEN,
        HANDLED,
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

    public ClickResult processInventoryClick(int slot, ClickType type) {
        return ClickResult.IGNORE;
    }

    public ClickResult processPlayerClick(int slot, ClickType type) {
        return ClickResult.IGNORE;
    }

    public CloseResult processClose(){
        return CloseResult.IGNORE;
    }

    public void update(){

    };

    public void reset(){

    }

    public abstract void initalize();
}
