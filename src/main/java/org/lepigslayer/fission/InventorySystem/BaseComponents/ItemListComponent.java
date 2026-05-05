package org.lepigslayer.fission.InventorySystem.BaseComponents;

import org.lepigslayer.fission.InventorySystem.InventoryComponent;
import org.lepigslayer.fission.InventorySystem.SlotMask.SlotMask;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.InventorySlot;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.SlotRendererComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemListComponent extends InventoryComponent {

    private List<InventorySlot> slotList = new ArrayList<>();
    private SlotMask mask = null;

    public ItemListComponent() {}

    public ItemListComponent(SlotMask mask) {
        this.mask = mask;
    }

    public void setMask(SlotMask mask) {
        this.mask = mask;
    }

    public InventorySlot newSlot(){
        InventorySlot slot = new InventorySlot();
        slotList.add(slot);
        return slot;
    }

    public void add(InventorySlot slot) {
        slotList.add(slot);
    }

    public void setList(List<InventorySlot> slotList) {
        this.slotList = slotList;
    }

    @Override
    public void reset() {
        slotList.clear();
    }

    @Override
    public void initalize() {
        if(mask == null)
            mask = SlotMask.grid(instance.getSize());

        Iterator<InventorySlot> iterator = slotList.iterator();
        mask.reset();

        while(mask.hasNext()){
            int position = mask.next();
            if(!iterator.hasNext()){
                instance.setItem(position,null);
                continue;
            }
            instance.getComponent(SlotRendererComponent.class).assignSlot(position, iterator.next());
        }
    }
}
