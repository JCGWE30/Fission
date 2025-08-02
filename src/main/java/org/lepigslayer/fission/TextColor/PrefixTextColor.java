package org.lepigslayer.fission.TextColor;

public class PrefixTextColor implements TextColor{
    private String prefix;

    public PrefixTextColor(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String colorString(String text) {
        return "";
    }
}
