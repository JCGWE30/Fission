package org.lepigslayer.fission.InventorySystem.SlotRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreSection {
    private List<String> lines;
    private InventorySlot slot;

    public LoreSection() {
        lines = new ArrayList<>();
    }

    void setOwningSlot(InventorySlot slot) {
        this.slot = slot;
    }

    private void triggerSlotChange() {
        if (slot != null)
            slot.triggerChange();
    }

    public void reset() {
        lines = new ArrayList<>();
        triggerSlotChange();
    }

    public LoreSection addLine(String line) {
        lines.add(line);
        triggerSlotChange();
        return this;
    }

    public void setLines(List<String> lines) {
        this.lines = new ArrayList<>(lines);
        triggerSlotChange();
    }

    public void setLines(String... lines) {
        setLines(Arrays.asList(lines));
    }

    public List<String> getLines() {
        return lines;
    }
}
