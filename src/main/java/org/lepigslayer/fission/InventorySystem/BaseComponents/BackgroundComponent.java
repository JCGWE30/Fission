package org.lepigslayer.fission.InventorySystem.BaseComponents;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.InventorySystem.InventoryComponent;
import org.lepigslayer.fission.Utilities.ItemBuilder;

public class BackgroundComponent extends InventoryComponent {

    @Override
    public void initalize() {
        ItemBuilder builder = new ItemBuilder()
                .name(" ")
                .texture(Material.GRAY_STAINED_GLASS_PANE)
                .amount(1);

        for (int i = 0; i < instance.getSize(); i++) {
            if (!instance.hasItem(i))
                instance.setItem(i, builder.build());
        }
    }

    @Override
    public EventResult processInventoryClick(int slot, ClickType type) {
        return EventResult.DENY;
    }
}
