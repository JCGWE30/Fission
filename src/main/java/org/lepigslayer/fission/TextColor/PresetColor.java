package org.lepigslayer.fission.TextColor;

import org.bukkit.ChatColor;
import org.lepigslayer.fission.Utilities.StringUtils;

public enum PresetColor {
    RAINBOW(StringUtils::rainbowString),
    BLACK(new BasicTextColor(ChatColor.BLACK)),
    DARK_GREEN(new BasicTextColor(ChatColor.GREEN)),
    DARK_RED(new BasicTextColor(ChatColor.RED)),
    GOLD(new BasicTextColor(ChatColor.GOLD)),
    DARK_GRAY(new BasicTextColor(ChatColor.DARK_GRAY)),
    GREEN(new BasicTextColor(ChatColor.GREEN)),
    RED(new BasicTextColor(ChatColor.RED)),
    YELLOW(new BasicTextColor(ChatColor.YELLOW)),
    DARK_BLUE(new BasicTextColor(ChatColor.BLUE)),
    DARK_AQUA(new BasicTextColor(ChatColor.AQUA)),
    DARK_PURPLE(new BasicTextColor(ChatColor.DARK_PURPLE)),
    GRAY(new BasicTextColor(ChatColor.GRAY)),
    BLUE(new BasicTextColor(ChatColor.BLUE)),
    AQUA(new BasicTextColor(ChatColor.AQUA)),
    LIGHT_PURPLE(new BasicTextColor(ChatColor.LIGHT_PURPLE)),
    WHITE(new BasicTextColor(ChatColor.WHITE));

    private TextColor textColor;
    PresetColor(TextColor color){
        this.textColor = color;
    }

    public String color(String text) {
        return textColor.colorString(text);
    }
}
