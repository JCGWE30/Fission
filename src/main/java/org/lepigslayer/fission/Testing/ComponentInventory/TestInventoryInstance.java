package org.lepigslayer.fission.Testing.ComponentInventory;

import org.bukkit.entity.Player;
import org.lepigslayer.fission.InventorySystem.BaseComponents.BackgroundComponent;
import org.lepigslayer.fission.InventorySystem.InventoryInstance;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.SlotRendererComponent;

public class TestInventoryInstance extends InventoryInstance {
    public TestInventoryInstance(Player player){
        super(player);
        setMeta("test",3);

        addComponent(new TestComponent());
        addComponent(new SlotRendererComponent());
        addComponent(new BackgroundComponent());
    }
}
