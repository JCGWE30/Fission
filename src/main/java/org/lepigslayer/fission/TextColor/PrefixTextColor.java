package org.lepigslayer.fission.TextColor;

public class PrefixTextColor implements TextColor{
    private String prefix;
    private TextColor textColor;

    public PrefixTextColor(String prefix, TextColor color) {
        this.prefix = prefix;
        this.textColor = color;
    }

    @Override
    public String colorString(String text) {
        return prefix+textColor.colorString(text);
    }
}
