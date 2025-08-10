package org.lepigslayer.fission.Utilities;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R5.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {

    public static ItemStack setStackSize(ItemStack item, int amount) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.MAX_STACK_SIZE,amount)
                .build();

        nmsItem.remove(DataComponents.BLOCK_ENTITY_DATA);
        nmsItem.applyComponents(patch);
        nmsItem.setCount(amount);

        ItemStack itemStack = CraftItemStack.asBukkitCopy(nmsItem);
        return itemStack;
    }
}
