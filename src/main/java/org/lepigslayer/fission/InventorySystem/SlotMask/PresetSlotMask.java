package org.lepigslayer.fission.InventorySystem.SlotMask;

import java.util.Iterator;
import java.util.List;

public class PresetSlotMask implements SlotMask {
    private List<Integer> slots;
    private Iterator<Integer> currentIterator;

    public PresetSlotMask(List<Integer> slots) {
        this.slots = slots;
    }

    @Override
    public void reset() {
        currentIterator = slots.iterator();
    }

    @Override
    public boolean hasNext() {
        return currentIterator.hasNext();
    }

    @Override
    public Integer next() {
        return currentIterator.next();
    }
}
