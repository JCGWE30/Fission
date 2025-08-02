package org.lepigslayer.fission.CustomInventory.Slots;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ClickableInventorySlot<T> extends CustomInventorySlot {
    private Consumer<Player> clickAction;
    private Map<ClickType, Consumer<Player>> detailedClickActions;

    public ClickableInventorySlot() {
        detailedClickActions = new HashMap<>();
    }

    public T onClick(Consumer<Player> clickAction) {
        this.clickAction = clickAction;
        return (T) this;
    }

    public T addClickAction(Consumer<Player> clickAction, ClickType clickType) {
        detailedClickActions.put(clickType, clickAction);
        return (T) this;
    }

    @Override
    public void triggerClick(ClickType clickType) {
        Consumer<Player> action = detailedClickActions.getOrDefault(clickType, clickAction);
        if(action!=null)
            action.accept(player);
    }
}
