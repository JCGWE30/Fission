package org.lepigslayer.fission.InventorySystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class InventoryInstance extends InventoryComponent implements InventoryHolder {
    protected Player player;
    private Map<Class<?>, InventoryComponent> componentMap;
    private Set<Integer> usedSlots;
    private Inventory inventory;

    private String title;
    private int size;

    public InventoryInstance(Player player){
        this.player = player;
        this.usedSlots = new HashSet<>();
        this.componentMap = new LinkedHashMap<>();
        addComponent(this);
    }

    public final void open(){
        InventorySystemManager.openInventory(player, this);
        load();
    }

    public final void forceClose(){
        InventorySystemManager.forceCloseInventory(player);
    }

    public final void load(){
        for (InventoryComponent component : componentMap.values()) {
            component.initalize();
        }
        for (InventoryComponent component : componentMap.values()) {
            component.update();
        }
    }

    public final void refresh(){
        componentMap.values().forEach(InventoryComponent::reset);
        usedSlots.clear();

        for(int i = 0;i<size;i++){
            inventory.setItem(i,null);
        }

        load();
    }

    public final String getTitle() {
        return title;
    }

    public final int getSize() {
        return size;
    }

    public final boolean hasComponent(Class<?> type){
        return componentMap.containsKey(type);
    }

    public final <T> T getComponent(Class<T> type) {
        return (T) componentMap.get(type);
    }

    protected final void setMeta(String title, int rowCount){
        if(rowCount > 6)
            throw new IllegalArgumentException("Rows must be less than 6");

        this.title = title;
        this.size = rowCount * 9;

        inventory = Bukkit.createInventory(this, size, title);
    }

    protected final <T extends InventoryComponent> T addComponent(T component){
        if(componentMap.containsKey(component.getClass()))
            throw new IllegalArgumentException("Component already exists!");
        component.setInstance(this,player);
        componentMap.put(component.getClass(), component);
        return component;
    }

    public final ItemStack getItem(int slot){
        return inventory.getItem(slot);
    }

    public final void setItem(int slot, ItemStack item){
        inventory.setItem(slot, item);
        usedSlots.add(slot);
    }

    public final boolean hasItem(int slot){
        return usedSlots.contains(slot);
    }

    final void handleClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null)
            return;

        boolean isInvenClick = e.getClickedInventory().equals(inventory);
        int slot = e.getSlot();
        ClickType clickType = e.getClick();

        for (InventoryComponent comp : componentMap.values()) {
            ClickResult result = isInvenClick ? comp.processInventoryClick(slot,clickType) : comp.processPlayerClick(slot,clickType);

            if(result == ClickResult.IGNORE)
                continue;

            e.setCancelled(result == ClickResult.DENY);
            return;
        }
    }

    final CloseResult handleClose(InventoryCloseEvent e){
        for (InventoryComponent comp : componentMap.values()) {
            CloseResult result = comp.processClose();

            if(result == CloseResult.IGNORE)
                continue;

            return result;
        }

        return CloseResult.IGNORE;
    }

    final void updateInventory(){
        componentMap.values().forEach(InventoryComponent::update);
    }

    @Override
    public void initalize() {}

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
