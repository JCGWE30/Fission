package org.lepigslayer.fission.CustomInventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.lepigslayer.fission.CustomInventory.Defaults.DefaultInventoryListener;
import org.lepigslayer.fission.CustomInventory.Defaults.DefaultInventoryRenderer;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomInventory implements InventoryHolder {
    private final Plugin plugin;

    private CustomInventoryRenderer renderer;
    private CustomInventoryListener listener;

    private Map<Integer, CustomInventorySlot> slotMap;
    private Inventory inventory;

    private long debounce = 500;
    private long lastClick;

    public CustomInventory() {
        slotMap = new HashMap<>();
        renderer = new DefaultInventoryRenderer();
        listener = new DefaultInventoryListener();

        plugin = JavaPlugin.getProvidingPlugin(this.getClass());
    }

    protected final void setMeta(String name, int rowCount){
        inventory = Bukkit.createInventory(this, rowCount*9, name);
    }

    protected final void setDebounce(long debounce){
        this.debounce = debounce;
    }

    protected final void setSlot(int position, CustomInventorySlot slot){
        slotMap.put(position, slot);
    }

    protected final void setListener(CustomInventoryListener listener){
        this.listener = listener;
    }

    protected final void setRenderer(CustomInventoryRenderer renderer){
        this.renderer = renderer;
    }

    public final boolean handleClick(CustomInventorySlot slot, ClickType clickType){

        if(lastClick > System.currentTimeMillis())
            return false;

        lastClick = System.currentTimeMillis()+debounce;

        slot.triggerClick(clickType);
        return true;
    }

    public final void openInventory(Player player) {
        CustomInventoryManager.registerInventory(player, this);
        reOpenInventory(player);
    }

    public final void reOpenInventory(Player player) {
        slotMap.values().forEach(s->initalizeModule(s, player));

        initalizeModule(listener, player);
        initalizeModule(renderer, player);

        renderer.render();
        listener.activate(plugin);

        player.openInventory(inventory);
    }

    private void initalizeModule(CustomInventoryModule module, Player player) {
        module.setHolder(this);
        module.setInventory(inventory);
        module.setSlotMap(slotMap);
        module.setPlayer(player);
        module.setRenderer(renderer);
        module.setListener(listener);
        module.onFinished();
    }

    public final void handleClose(){
        listener.deactivate();
        renderer.deactivate();
    }

    public final boolean match(Inventory inventory){
        return inventory == this.inventory;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
