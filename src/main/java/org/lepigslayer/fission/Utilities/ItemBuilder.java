package org.lepigslayer.fission.Utilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.lepigslayer.fission.Texture.ItemTexture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private ItemTexture texture;
    private int amount;
    private String name;
    private List<String> lore;
    private boolean isDisplay;
    private boolean isGlowing;
    private List<String> tags;

    public ItemBuilder() {
        this.tags = new ArrayList<>();
        this.lore = new ArrayList<>();
    }

    public ItemBuilder texture(ItemTexture material) {
        this.texture = material;
        return this;
    }

    public ItemBuilder texture(Material material) {
        this.texture = ItemTexture.of(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder lore(String... lore){
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder glow(){
        return glow(true);
    }

    public ItemBuilder glow(boolean glowing){
        this.isGlowing = glowing;
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        this.lore = lore;
        return this;
    }

    public ItemBuilder markDisplay(){
        this.isDisplay = true;
        return this;
    }

    public ItemBuilder tag(String tag){
        this.tags.add(tag);
        return this;
    }

    public ItemStack build(){
        ItemStack item = texture.fetch();
        ItemMeta meta = item.getItemMeta();
        if(name!=null)
            meta.setDisplayName(name);
        if(lore.size()>0)
            meta.setLore(lore);
        if(isDisplay)
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ADDITIONAL_TOOLTIP,ItemFlag.HIDE_UNBREAKABLE);
        if(isGlowing){
            meta.setEnchantmentGlintOverride(true);
        }
        item.setItemMeta(meta);

        for (String tag : tags) {
            item = NBTUtils.addFissionTag(item, tag);
        }

        return NBTUtils.setStackSize(item, amount);
    }
}
