package org.lepigslayer.fission.Colors;

import org.bukkit.ChatColor;

public class ModifierColor implements CustomColor {

    private ChatColor color;

    public ModifierColor(ChatColor color) {
        this.color = color;
    }

    @Override
    public String colorString(String text) {

        boolean isColor = false;
        boolean lastColor = false;
        boolean isAdded = false;

        StringBuilder colorString = new StringBuilder();

        for (char c : text.toCharArray()) {
            if(c=='§'){
                isColor = true;
                lastColor = true;
                colorString.append(c);
                continue;
            }

            if(lastColor){
                lastColor = false;
                colorString.append(c);
                continue;
            }

            if(isColor){
                isAdded = true;
                colorString.append(color.toString());
                isColor = false;
            }

            colorString.append(c);
        }

        return colorString.toString();
    }
}
