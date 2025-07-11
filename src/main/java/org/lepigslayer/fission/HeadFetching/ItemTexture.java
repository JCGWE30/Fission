package org.lepigslayer.fission.HeadFetching;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.net.URI;
import java.net.URL;
import java.util.UUID;

public class ItemTexture {
    private Material material;
    private boolean isSkull = false;
    private URL skullURL;

    public ItemTexture(Material material) {
        this.material = material;
    }

    public ItemTexture(String skullValue){
        try {
            this.material = Material.PLAYER_HEAD;
            this.skullURL = URI.create(skullValue).toURL();
            this.isSkull = true;
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid skull value: " + skullValue, e);
        }
    }

    public ItemStack fetch(){
        ItemStack item = new ItemStack(material);
        if(!isSkull)
            return item;

        SkullMeta meta = ((SkullMeta) item.getItemMeta());
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        profile.getTextures().setSkin(skullURL);
        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }
}
