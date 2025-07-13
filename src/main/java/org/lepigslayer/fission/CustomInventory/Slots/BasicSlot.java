package org.lepigslayer.fission.CustomInventory.Slots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.Texture.ItemTexture;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.function.Consumer;

public class BasicSlot extends CustomInventorySlot {
    private ItemBuilder builder;
    private Consumer<Player> clickAction;

    public BasicSlot(ItemBuilder builder) {
        this.builder = builder;
    }

    public BasicSlot(String name, int amount, ItemTexture texture, String... lore) {
        this.builder = new ItemBuilder()
                .texture(texture)
                .amount(amount)
                .name(name)
                .lore(lore);
    }

    public BasicSlot(String name, int amount, Material texture, String... lore) {
        this.builder = new ItemBuilder()
                .texture(texture)
                .amount(amount)
                .name(name)
                .lore(lore);
    }

    public BasicSlot onClick(Consumer<Player> clickAction){
        this.clickAction = clickAction;
        return this;
    }

    @Override
    public ItemStack getItem() {
        return builder.build();
    }

    @Override
    public void triggerClick() {
        if(clickAction != null)
            clickAction.accept(player);
    }
}
