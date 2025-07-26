package org.lepigslayer.fission.CustomInventory.Defaults;

import org.bukkit.Material;
import org.lepigslayer.fission.CustomInventory.CustomInventoryRenderer;
import org.lepigslayer.fission.Utilities.ItemBuilder;

public class DefaultInventoryRenderer extends CustomInventoryRenderer {
    protected static ItemBuilder BACKGROUND_PANE = new ItemBuilder()
            .texture(Material.GRAY_STAINED_GLASS_PANE)
            .amount(1)
            .name(" ");

    @Override
    public void render() {
        for(int i = 0; i<inventory.getSize(); i++) {
            if(slotMap.containsKey(i)) {
                inventory.setItem(i, slotMap.get(i).getItem());
            }else{
                inventory.setItem(i, BACKGROUND_PANE.build());
            }
        }
    }
}
