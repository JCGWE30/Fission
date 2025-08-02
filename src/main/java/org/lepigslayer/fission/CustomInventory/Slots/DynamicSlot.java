package org.lepigslayer.fission.CustomInventory.Slots;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.Texture.ItemTexture;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.List;
import java.util.function.Consumer;

public class DynamicSlot extends CustomInventorySlot {

    //This could also use a custom interface to not build the item from the ground up if thats needed.
    private Consumer<ItemBuilder> updateConsumer;
    private Consumer<Player> clickAction;

    public DynamicSlot(Consumer<ItemBuilder> updateConsumer){
        this.updateConsumer = updateConsumer;
    }

    public DynamicSlot onClick(Consumer<Player> clickAction){
        this.clickAction = clickAction;
        return this;
    }

    @Override
    public ItemStack getItem() {
        ItemBuilder builder = new ItemBuilder();
        updateConsumer.accept(builder);
        return builder.build();
    }

    @Override
    public void triggerClick(ClickType clickType) {
        if(clickAction != null)
            clickAction.accept(player);
    }
}
