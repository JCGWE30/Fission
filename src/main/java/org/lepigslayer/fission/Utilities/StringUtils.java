package org.lepigslayer.fission.Utilities;

import org.bukkit.Bukkit;

public class StringUtils {
    private static char[] rainbowColors = "4c6e2ab319d5".toCharArray();

    public static String rainbowString(String input) {
        return rainbowString(input, false, 1, 0);
    }

    public static String rainbowString(String input, boolean bold) {
        return rainbowString(input, bold, 1, 0);
    }

    public static String rainbowString(String input, boolean bold, int step) {
        return rainbowString(input, bold, step, 0);
    }

    public static String rainbowString(String input, boolean bold, int step, int offset){
        StringBuilder output = new StringBuilder();
        int counter = offset % rainbowColors.length;
        for(int i = 0; i < input.length(); i+=step){
            output.append("§");
            output.append(rainbowColors[counter++ % rainbowColors.length]);

            if(bold)
                output.append("§l");

            output.append(input, i, Math.min(i+step,input.length()));
        }
        return output.toString();

    }
}
