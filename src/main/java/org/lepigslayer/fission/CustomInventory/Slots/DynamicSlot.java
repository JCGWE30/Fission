package org.lepigslayer.fission.CustomInventory.Slots;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.Texture.ItemTexture;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.List;
import java.util.function.Consumer;

public class DynamicSlot extends ClickableInventorySlot<DynamicSlot> {

    //This could also use a custom interface to not build the item from the ground up if thats needed.
    private Consumer<ItemBuilder> updateConsumer;

    public DynamicSlot(Consumer<ItemBuilder> updateConsumer){
        this.updateConsumer = updateConsumer;
    }

    @Override
    public ItemStack getItem() {
        ItemBuilder builder = new ItemBuilder();
        updateConsumer.accept(builder);
        return builder.build();
    }
}
