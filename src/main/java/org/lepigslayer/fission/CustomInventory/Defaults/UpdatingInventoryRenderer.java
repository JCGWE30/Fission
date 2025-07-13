package org.lepigslayer.fission.CustomInventory.Defaults;

import org.bukkit.scheduler.BukkitRunnable;
import org.lepigslayer.fission.CustomInventory.CustomInventorySlot;
import org.lepigslayer.fission.CustomInventory.Slots.DynamicSlot;
import org.lepigslayer.fission.Utilities.TimeUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UpdatingInventoryRenderer extends DefaultInventoryRenderer {
    private boolean updatesOnClick = false;
    private boolean updatesOnInterval = false;
    private long updateInterval = 0L;
    private Map<Integer, DynamicSlot> dynamicSlots;
    private BukkitRunnable updateRunnable;

    public UpdatingInventoryRenderer setUpdateInterval(long updateInterval) {
        this.updatesOnInterval = true;
        this.updateInterval = (long) ((updateInterval/1000.0) * 20);
        return this;
    }

    public UpdatingInventoryRenderer setUpdateOnClick() {
        this.updatesOnClick = true;
        return this;
    }

    public void loadSlots() {
        dynamicSlots = new HashMap<>();
        for (Map.Entry<Integer, CustomInventorySlot> entry : slotMap.entrySet()) {
            if(!(entry.getValue() instanceof DynamicSlot))
                continue;

            DynamicSlot dynamicSlot = (DynamicSlot) entry.getValue();
            dynamicSlots.put(entry.getKey(), dynamicSlot);
        }
    }

    void updateSlots(){
        for (Map.Entry<Integer, DynamicSlot> entry : dynamicSlots.entrySet()) {
            int index = entry.getKey();
            DynamicSlot slot = entry.getValue();

            inventory.setItem(index,slot.getItem());
        }
    }

    void clickReload(){
        if(updatesOnClick)
            updateSlots();
    }

    @Override
    public void render() {
        super.render();
        loadSlots();
        if(updatesOnInterval){
            updateRunnable = TimeUtils.runTask(holder.getClass(),this::updateSlots, updateInterval);
        }
    }

    @Override
    public void deactivate() {
        if(updateRunnable != null)
            updateRunnable.cancel();
    }
}
