package org.lepigslayer.fission.Profile;

import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileBuilder {
    private UUID uuid;
    private String name;

    private String textureValue;
    private String textureSignature;

    public ProfileBuilder(){
        uuid = UUID.randomUUID();
    }

    public ProfileBuilder uuid(UUID uuid){
        this.uuid = uuid;
        return this;
    }

    public ProfileBuilder name(String name){
        this.name = name;
        return this;
    }

    public ProfileBuilder texture(String textureValue){
        this.textureValue = textureValue;
        return this;
    }

    public ProfileBuilder signature(String signature){
        this.textureSignature = signature;
        return this;
    }

    public ProfileBuilder fromPlayer(Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();

        Property property = ((CraftPlayer) player).getHandle().getGameProfile().properties().get("textures").iterator().next();

        this.textureValue = property.value();
        this.textureSignature = property.signature();
        return this;
    }

    public GameProfile build(){
        Property property = new Property("textures", this.textureValue, this.textureSignature);

        ImmutableMultimap.Builder<String, Property> builder = ImmutableMultimap.builder();
        builder.put("textures", property);

        return new GameProfile(uuid, name,new PropertyMap(builder.build()));
    }
}
