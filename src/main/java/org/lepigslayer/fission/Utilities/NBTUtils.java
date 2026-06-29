package org.lepigslayer.fission.Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.*;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

    public static List<String> getFissionTags(ItemStack item){
        if (item == null || item.getType() == Material.AIR)
            return Collections.emptyList();

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        DataComponentMap map = nmsItem.getComponents();
        CustomData data = map.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if (!tag.contains(FISISON_TAG))
            return Collections.emptyList();
        ListTag listTag = tag.getListOrEmpty(FISISON_TAG);
        List<String> values = new ArrayList<>();
        for (Tag tagValue : listTag) {
            if (!(tagValue instanceof StringTag(String st)))
                continue;
            values.add(st);
        }
        return values;
    }

    public static ItemStack applyCustomData(ItemStack item, JsonElement data){
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        JsonObject jsonObject = extractCustomData(item).orElseGet(JsonObject::new).getAsJsonObject();
        jsonObject.add("fission_custom_data", data);

        CompoundTag tag = JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, jsonObject).asCompound().get();

        nmsItem.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static Optional<JsonElement> extractCustomData(ItemStack item){
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if(!nmsItem.has(DataComponents.CUSTOM_DATA))
            return Optional.empty();

        CompoundTag tag = nmsItem.get(DataComponents.CUSTOM_DATA).copyTag();

        JsonObject object = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE,tag).getAsJsonObject();
        if(!object.has("fission_custom_data"))
            return Optional.empty();

        JsonElement data = object.get("fission_custom_data");

        return Optional.of(data);
    }
}
