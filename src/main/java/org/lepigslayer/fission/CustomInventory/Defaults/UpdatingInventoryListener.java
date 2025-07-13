package org.lepigslayer.fission.CustomInventory.Defaults;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.lepigslayer.fission.CustomInventory.Slots.DynamicSlot;
import org.lepigslayer.fission.Fission;

public class UpdatingInventoryListener extends DefaultInventoryListener {
    private UpdatingInventoryRenderer updatingRenderer;

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!super.handleClick(e))
            return;

        if(slotMap.get(e.getSlot()) instanceof DynamicSlot)
            updatingRenderer.clickReload();
    }

    @Override
    public void onFinished() {
        if(!(renderer instanceof UpdatingInventoryRenderer)){
            Fission.log("UpdatingInventoryListener must be used with a UpdatingInventoryRenderer");
            return;
        }
        updatingRenderer = ((UpdatingInventoryRenderer) renderer);
    }
}
