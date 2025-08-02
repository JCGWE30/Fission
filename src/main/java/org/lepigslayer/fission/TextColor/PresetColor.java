package org.lepigslayer.fission.TextColor;

import org.lepigslayer.fission.Utilities.StringUtils;

public enum PresetColor {
    RAINBOW(StringUtils::rainbowString);

    private TextColor textColor;
    PresetColor(TextColor color){
        this.textColor = color;
    }

    public String color(String text) {
        return textColor.colorString(text);
    }
}
