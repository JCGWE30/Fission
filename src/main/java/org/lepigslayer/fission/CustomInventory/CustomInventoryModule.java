package org.lepigslayer.fission.CustomInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public abstract class CustomInventoryModule {
    protected CustomInventory holder;
    protected Inventory inventory;
    protected Map<Integer, CustomInventorySlot> slotMap;
    protected Player player;
    protected CustomInventoryListener listener;
    protected CustomInventoryRenderer renderer;

    void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    void setHolder(CustomInventory holder) {
        this.holder = holder;
    }

    void setSlotMap(Map<Integer, CustomInventorySlot> slotMap){
        this.slotMap = slotMap;
    }

    void setPlayer(Player player){
        this.player = player;
    }

    void setListener(CustomInventoryListener listener){
        this.listener = listener;
    }

    void setRenderer(CustomInventoryRenderer renderer){
        this.renderer = renderer;
    }

    public void onFinished(){

    }
}
