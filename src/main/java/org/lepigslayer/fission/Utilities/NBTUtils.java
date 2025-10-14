package org.lepigslayer.fission.Utilities;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R5.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {
    private static final String FISISON_TAG = "FissionTags";

    public static ItemStack setStackSize(ItemStack item, int amount) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.MAX_STACK_SIZE, amount)
                .build();

        nmsItem.remove(DataComponents.BLOCK_ENTITY_DATA);
        nmsItem.applyComponents(patch);
        nmsItem.setCount(amount);

        ItemStack itemStack = CraftItemStack.asBukkitCopy(nmsItem);
        return itemStack;
    }

    public static ItemStack addFissionTag(ItemStack item, String value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        DataComponentMap map = nmsItem.getComponents();

        CustomData data = map.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        CompoundTag tag = data.copyTag();
        if (!tag.contains(FISISON_TAG))
            tag.put(FISISON_TAG, new ListTag());

        ListTag listTag = tag.getListOrEmpty(FISISON_TAG);
        listTag.add(StringTag.valueOf(value));

        DataComponentPatch patch = DataComponentPatch.builder().set(DataComponents.CUSTOM_DATA, CustomData.of(tag)).build();
        nmsItem.applyComponents(patch);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasFissionTag(ItemStack item, String value) {
        if (item == null || item.getType() == Material.AIR)
            return false;

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        DataComponentMap map = nmsItem.getComponents();
        CustomData data = map.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if (!tag.contains(FISISON_TAG))
            return false;
        ListTag listTag = tag.getListOrEmpty(FISISON_TAG);
        for (Tag tagValue : listTag) {
            if (!(tagValue instanceof StringTag(String st)))
                continue;
            if (st.equals(value))
                return true;
        }
        return false;
    }
}
