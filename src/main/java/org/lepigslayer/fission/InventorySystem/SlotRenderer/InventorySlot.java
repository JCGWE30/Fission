package org.lepigslayer.fission.InventorySystem.SlotRenderer;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.lepigslayer.fission.Texture.ItemTexture;

import java.util.*;

public class InventorySlot {
    private boolean dirty = false;
    private String name = "Slot";
    private ItemTexture texture = ItemTexture.of(Material.QUARTZ);
    private int amount = 1;
    private List<LoreSection> lore;
    private Map<ClickType, Runnable> clickActions;
    private boolean glowing = false;

    public InventorySlot() {
        lore = new ArrayList<>();
        clickActions = new HashMap<>();
    }

    boolean isDirty() {
        return dirty;
    }

    String getName() {
        return name;
    }

    ItemTexture getTexture() {
        return texture;
    }

    int getAmount() {
        return amount;
    }

    List<LoreSection> getLore() {
        return lore;
    }

    public LoreSection getLore(int index) {
        return lore.get(index);
    }

    public void triggerClickAction(ClickType clickType) {
        if (!clickActions.containsKey(clickType))
            return;

        clickActions.get(clickType).run();
    }

    public boolean isGlowing() {
        return glowing;
    }

    public InventorySlot name(String name) {
        this.name = name;
        this.dirty = true;
        return this;
    }

    public InventorySlot texture(Material texture) {
        return texture(ItemTexture.of(texture));
    }

    public InventorySlot texture(ItemTexture texture) {
        this.texture = texture;
        this.dirty = true;
        return this;
    }

    public InventorySlot amount(int amount) {
        this.amount = amount;
        this.dirty = true;
        return this;
    }

    public InventorySlot lore(String... lore){
        return lore(Arrays.asList(lore));
    }

    public InventorySlot lore(List<String> lore) {
        LoreSection section = new LoreSection();
        section.setLines(lore);
        return loreSection(section);
    }

    public InventorySlot loreSection(LoreSection section) {
        lore.add(section);
        section.setOwningSlot(this);
        this.dirty = true;
        return this;
    }

    public InventorySlot setGlowing(boolean glowing) {
        this.glowing = glowing;
        this.dirty = true;
        return this;
    }

    public InventorySlot onClick(Runnable clickAction) {
        return clickAction(ClickType.UNKNOWN, clickAction);
    }

    public InventorySlot clickAction(ClickType clickType, Runnable clickAction) {
        this.clickActions.put(clickType, clickAction);
        return this;
    }

    void triggerChange() {
        this.dirty = true;
    }

    void clean() {
        this.dirty = false;
    }
}
