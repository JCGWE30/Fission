package org.lepigslayer.fission.InventorySystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Predicate;

public abstract class InventoryInstance extends InventoryComponent implements InventoryHolder {
    protected Player player;
    private Map<Class<?>, InventoryComponent> componentMap;
    private Set<Integer> usedSlots;
    private Inventory inventory;

    private int size;

    public InventoryInstance(Player player){
        this.player = player;
        this.usedSlots = new HashSet<>();
        this.componentMap = new LinkedHashMap<>();
        addComponent(this);
    }

    public void open(){
        load();
        InventorySystemManager.openInventory(player, this);
    }

    public void load(){
        for (InventoryComponent component : componentMap.values()) {
            component.initalize();
            component.update();
        }
    }

    public void refresh(){
        componentMap.values().forEach(InventoryComponent::reset);
        usedSlots.clear();

        for(int i = 0;i<size;i++){
            inventory.setItem(i,null);
        }

        load();
    }

    public int getSize() {
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

        this.size = rowCount * 9;

        inventory = Bukkit.createInventory(this, size, title);
    }

    protected final void addComponent(InventoryComponent component){
        if(componentMap.containsKey(component.getClass()))
            throw new IllegalArgumentException("Component already exists!");
        component.setInstance(this,player);
        componentMap.put(component.getClass(), component);
    }

    public ItemStack getItem(int slot){
        return inventory.getItem(slot);
    }

    public void setItem(int slot, ItemStack item){
        inventory.setItem(slot, item);
        usedSlots.add(slot);
    }

    public boolean hasItem(int slot){
        return usedSlots.contains(slot);
    }

    void handleClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null)
            return;

        boolean isInvenClick = e.getClickedInventory().equals(inventory);
        int slot = e.getSlot();
        ClickType clickType = e.getClick();

        for (InventoryComponent comp : componentMap.values()) {
            ClickResult result = isInvenClick ? comp.handleInventoryClick(slot,clickType) : comp.handlePlayerClick(slot,clickType);

            if(result == ClickResult.IGNORE)
                continue;

            e.setCancelled(result == ClickResult.DENY);
            return;
        }
    }

    void updateInventory(){
        componentMap.values().forEach(InventoryComponent::update);
    }

    @Override
    public void initalize() {}

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
