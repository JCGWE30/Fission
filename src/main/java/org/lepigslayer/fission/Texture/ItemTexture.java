package org.lepigslayer.fission.Texture;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ItemTexture {
    private static Map<Material, ItemTexture> materialMap = new HashMap<>();

    private Material material;

    private boolean isSkull = false;
    private PlayerProfile profile;

    private boolean isMimic = false;
    private Supplier<ItemStack> mimicItem;

    private ItemTexture(Supplier<ItemStack> mimic){
        this.mimicItem = mimic;
        this.isMimic = true;
    }

    private ItemTexture(Material material) {
        this.material = material;
    }

    private ItemTexture(String skullValue){
        try {
            this.material = Material.PLAYER_HEAD;
            this.profile = Bukkit.createPlayerProfile(UUID.randomUUID());
            this.profile.getTextures().setSkin(URI.create(skullValue).toURL());
            this.isSkull = true;
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid skull value: " + skullValue, e);
        }
    }

    private ItemTexture(PlayerProfile profile) {
        this.material = Material.PLAYER_HEAD;
        this.profile = profile;
        this.isSkull = true;
    }

    public ItemStack fetch(){
        if(isMimic)
            return mimicItem.get();

        ItemStack item = new ItemStack(material);

        if(isSkull)
            return fetchSkull(item);

        return item;
    }

    private ItemStack fetchSkull(ItemStack item){
        SkullMeta meta = ((SkullMeta) item.getItemMeta());

        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemTexture of(Material material) {
        if(!materialMap.containsKey(material))
            materialMap.put(material, new ItemTexture(material));
        return materialMap.get(material);
    }

    public static ItemTexture of(String skullValue) {
        return new ItemTexture(skullValue);
    }

    public static ItemTexture of(OfflinePlayer player) {
        return new ItemTexture(player.getPlayerProfile());
    }

    public static ItemTexture of(PlayerProfile profile) {
        return new ItemTexture(profile);
    }

    public static ItemTexture of(ItemStack item) {
        return of(()->item);
    }

    public static ItemTexture of(Supplier<ItemStack> supplier){
        return new ItemTexture(supplier);
    }
}
