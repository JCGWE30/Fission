package org.lepigslayer.fission.TextColor;

import org.bukkit.ChatColor;

public class BasicTextColor implements TextColor {
    private ChatColor color;

    public BasicTextColor(ChatColor color) {
        this.color = color;
    }

    public PrefixTextColor withPrefix(String prefix) {
        return new PrefixTextColor(prefix,this);
    }

    @Override
    public String colorString(String text) {
        return color.toString()+text;
    }
}
