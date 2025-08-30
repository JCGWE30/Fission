package org.lepigslayer.fission.InventorySystem.SlotMask;

public class GridSlotMask implements SlotMask {
    private final int start;
    private final int end;
    private final int mod;
    private final int offset;

    private int currentSlot;


    public GridSlotMask(int upperCorner, int lowerCorner) {
        this.start = upperCorner;
        this.end = lowerCorner;

        int startMod = upperCorner % 9;
        int endMod = lowerCorner % 9;

        this.mod = endMod + 1;
        this.offset = (startMod + 8) - endMod ;
    }

    @Override
    public void reset() {
        currentSlot = start;
    }

    @Override
    public boolean hasNext() {
        return currentSlot <= end;
    }

    @Override
    public Integer next() {
        int slot = currentSlot;

        currentSlot ++;

        if(currentSlot % 9 == mod)
            currentSlot += offset;

        return slot;
    }
}
