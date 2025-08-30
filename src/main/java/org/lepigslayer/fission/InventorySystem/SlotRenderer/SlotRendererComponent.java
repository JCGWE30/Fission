package org.lepigslayer.fission.InventorySystem.SlotRenderer;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.InventorySystem.InventoryComponent;
import org.lepigslayer.fission.Utilities.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlotRendererComponent extends InventoryComponent {
    private Map<Integer, InventorySlot> slotMap;

    public SlotRendererComponent() {
        slotMap = new HashMap<>();
    }

    public void assignSlot(int slotPosition, InventorySlot slot) {
        if(instance.hasItem(slotPosition))
            return;

        slotMap.put(slotPosition, slot);
    }

    private void updateSlot(int position) {
        InventorySlot slot = slotMap.get(position);

        ItemBuilder builder = new ItemBuilder()
                .name(slot.getName())
                .amount(slot.getAmount())
                .texture(slot.getTexture())
                .glow(slot.isGlowing());

        List<String> lore = new ArrayList<>();

        for (LoreSection section : slot.getLore()) {
            lore.addAll(section.getLines());
        }

        builder.lore(lore);

        instance.setItem(position, builder.build());
    }

    @Override
    public void initalize() {
        for (Map.Entry<Integer, InventorySlot> entry : slotMap.entrySet()) {
            int position = entry.getKey();
            updateSlot(position);
        }
    }

    @Override
    public void reset() {
        slotMap.clear();
    }

    @Override
    public void update() {
        for (Map.Entry<Integer, InventorySlot> ent : slotMap.entrySet()) {
            int position = ent.getKey();
            InventorySlot slot = ent.getValue();

            if (!slot.isDirty())
                continue;

            updateSlot(position);
        }

        slotMap.values().forEach(InventorySlot::clean);
    }

    @Override
    public ClickResult handlePlayerClick(int slot, ClickType type) {
        return ClickResult.DENY;
    }

    @Override
    public ClickResult handleInventoryClick(int slot, ClickType type) {
        if (!slotMap.containsKey(slot))
            return ClickResult.DENY;

        InventorySlot inventorySlot = slotMap.get(slot);

        inventorySlot.triggerClickAction(type);
        inventorySlot.triggerClickAction(ClickType.UNKNOWN);

        return ClickResult.DENY;
    }
}
