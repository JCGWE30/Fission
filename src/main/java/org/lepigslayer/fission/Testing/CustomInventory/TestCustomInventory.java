package org.lepigslayer.fission.Testing.CustomInventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.lepigslayer.fission.CustomInventory.CustomInventory;
import org.lepigslayer.fission.CustomInventory.Defaults.UpdatingInventoryListener;
import org.lepigslayer.fission.CustomInventory.Defaults.UpdatingInventoryRenderer;
import org.lepigslayer.fission.CustomInventory.Slots.BasicSlot;
import org.lepigslayer.fission.CustomInventory.Slots.DynamicSlot;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.UUID;

public class TestCustomInventory extends CustomInventory {
    private int depth;

    public TestCustomInventory(int depth) {
        this.depth = depth;

        setMeta("Custom Inventory " + depth, 1);
        setRenderer(new UpdatingInventoryRenderer().setUpdateInterval(100));
        setListener(new UpdatingInventoryListener());

        setSlot(3, new BasicSlot("§3Quirky Item", 1, Material.COBBLESTONE).onClick(this::click));
        setSlot(4, new DynamicSlot(b -> assembleSlot("Slot 1", b)));
    }

    private void click(Player player) {
        TestCustomInventory inven = new TestCustomInventory(depth + 1);
        inven.openInventory(player);
        player.sendMessage("§eCLICK WHOO");
    }

    private void assembleSlot(String prefixName, ItemBuilder builder) {
        builder
                .texture(Material.SLIME_BALL)
                .amount(99)
                .name(prefixName)
                .lore(UUID.randomUUID().toString());
    }
}
