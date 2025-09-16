package org.lepigslayer.fission.Colors;

import org.bukkit.ChatColor;

public class Colors {
    public static CustomColor DARK_RED = new BasicColor(ChatColor.DARK_RED);
    public static CustomColor RED = new BasicColor(ChatColor.RED);
    public static CustomColor GOLD = new BasicColor(ChatColor.GOLD);
    public static CustomColor YELLOW = new BasicColor(ChatColor.YELLOW);
    public static CustomColor DARK_GREEN = new BasicColor(ChatColor.DARK_GREEN);
    public static CustomColor GREEN = new BasicColor(ChatColor.GREEN);
    public static CustomColor AQUA = new BasicColor(ChatColor.AQUA);
    public static CustomColor DARK_AQUA = new BasicColor(ChatColor.DARK_AQUA);
    public static CustomColor DARK_BLUE = new BasicColor(ChatColor.DARK_BLUE);
    public static CustomColor BLUE = new BasicColor(ChatColor.BLUE);
    public static CustomColor LIGHT_PURPLE = new BasicColor(ChatColor.LIGHT_PURPLE);
    public static CustomColor DARK_PURPLE = new BasicColor(ChatColor.DARK_PURPLE);
    public static CustomColor WHITE = new BasicColor(ChatColor.WHITE);
    public static CustomColor GRAY = new BasicColor(ChatColor.GRAY);
    public static CustomColor DARK_GRAY = new BasicColor(ChatColor.DARK_GRAY);
    public static CustomColor BLACK = new BasicColor(ChatColor.BLACK);

    public static CustomColor BOLD = new ModifierColor(ChatColor.BOLD);
    public static CustomColor MAGIC = new ModifierColor(ChatColor.MAGIC);
    public static CustomColor STRIKETHROUGH = new ModifierColor(ChatColor.STRIKETHROUGH);
    public static CustomColor ITALIC = new ModifierColor(ChatColor.ITALIC);

    public static CustomColor RAINBOW = new GradientColor(ChatColor.DARK_RED, ChatColor.DARK_PURPLE,1);
    public static CustomColor BOLD_RAINBOW = CompoundColor.of(RAINBOW,BOLD);
    public static CustomColor MAGIC_RAINBOW = CompoundColor.of(RAINBOW,MAGIC);
}
