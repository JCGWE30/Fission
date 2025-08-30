package org.lepigslayer.fission.InventorySystem.SlotMask;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public interface SlotMask extends Iterator<Integer> {
    static PresetSlotMask preset(Integer... slots){
        return new PresetSlotMask(List.of(slots));
    }

    static PresetSlotMask preset(List<Integer> slots){
        return new PresetSlotMask(slots);
    }

    static GridSlotMask grid(int upperCorner, int lowerCorner) {
        return new GridSlotMask(upperCorner, lowerCorner);
    }

    static GridSlotMask grid(int size){
        return new GridSlotMask(10,size-11);
    }

    void reset();
}
