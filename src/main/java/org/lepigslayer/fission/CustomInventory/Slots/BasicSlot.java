package org.lepigslayer.fission.CustomInventory.Slots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.Texture.ItemTexture;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BasicSlot extends ClickableInventorySlot<BasicSlot> {
    private ItemBuilder builder;

    public BasicSlot(ItemBuilder builder) {
        this.builder = builder;
    }

    public BasicSlot(String name, int amount, ItemTexture texture, String... lore) {
        this(new ItemBuilder()
                .texture(texture)
                .amount(amount)
                .name(name)
                .lore(lore));
    }

    public BasicSlot(String name, int amount, Material texture, String... lore) {
        this(new ItemBuilder()
                .texture(texture)
                .amount(amount)
                .name(name)
                .lore(lore));
    }

    @Override
    public ItemStack getItem() {
        return builder.build();
    }
}
