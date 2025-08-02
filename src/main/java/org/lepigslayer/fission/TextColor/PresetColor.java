package org.lepigslayer.fission.TextColor;

import org.bukkit.ChatColor;
import org.lepigslayer.fission.Utilities.StringUtils;

public class PresetColor {
    public static TextColor RAINBOW = StringUtils::rainbowString;
    public static TextColor BLACK = new BasicTextColor(ChatColor.BLACK);
    public static TextColor DARK_GREEN = new BasicTextColor(ChatColor.DARK_GREEN);
    public static TextColor DARK_RED = new BasicTextColor(ChatColor.DARK_RED);
    public static TextColor GOLD = new BasicTextColor(ChatColor.GOLD);
    public static TextColor DARK_GRAY = new BasicTextColor(ChatColor.DARK_GRAY);
    public static TextColor GREEN = new BasicTextColor(ChatColor.GREEN);
    public static TextColor RED = new BasicTextColor(ChatColor.RED);
    public static TextColor YELLOW = new BasicTextColor(ChatColor.YELLOW);
    public static TextColor DARK_BLUE = new BasicTextColor(ChatColor.DARK_BLUE);
    public static TextColor DARK_AQUA = new BasicTextColor(ChatColor.DARK_AQUA);
    public static TextColor DARK_PURPLE = new BasicTextColor(ChatColor.DARK_PURPLE);
    public static TextColor GRAY = new BasicTextColor(ChatColor.GRAY);
    public static TextColor BLUE = new BasicTextColor(ChatColor.BLUE);
    public static TextColor AQUA = new BasicTextColor(ChatColor.AQUA);
    public static TextColor LIGHT_PURPLE = new BasicTextColor(ChatColor.LIGHT_PURPLE);
    public static TextColor WHITE = new BasicTextColor(ChatColor.WHITE);
}
