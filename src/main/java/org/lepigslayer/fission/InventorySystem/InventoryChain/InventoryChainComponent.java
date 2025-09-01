package org.lepigslayer.fission.InventorySystem.InventoryChain;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.InventorySystem.InventoryComponent;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.InventorySlot;
import org.lepigslayer.fission.Utilities.ItemBuilder;

public class InventoryChainComponent extends InventoryComponent {
    private int slot;

    public InventoryChainComponent(int slot) {
        this.slot = slot;
    }

    @Override
    public void initalize() {
        if(!InventoryChainManager.hasPrevious(player))
            return;

        String name = InventoryChainManager.getPrevious(player).getTitle();

        ItemBuilder builder = new ItemBuilder()
                .name("§eGo Back")
                .lore("§7Return to previous menu", "§8" + name)
                .texture(Material.ARROW)
                .amount(1);

        instance.setItem(slot, builder.build());
    }

    @Override
    public EventResult processInventoryClick(int slot, ClickType type) {
        if(this.slot != slot || !InventoryChainManager.hasPrevious(player))
            return EventResult.IGNORE;

        InventoryChainManager.traverse(player);

        return EventResult.DENY;
    }
}
