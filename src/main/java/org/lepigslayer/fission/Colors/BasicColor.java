package org.lepigslayer.fission.Colors;

import org.bukkit.ChatColor;

public class BasicColor implements CustomColor {
    private ChatColor chatColor;

    public BasicColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public String colorString(String text) {
        return chatColor.toString()+text;
    }
}
