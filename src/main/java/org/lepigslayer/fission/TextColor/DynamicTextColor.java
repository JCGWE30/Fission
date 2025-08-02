package org.lepigslayer.fission.TextColor;

import org.bukkit.ChatColor;

import java.util.function.Supplier;

public class DynamicTextColor implements TextColor {
    private Supplier<ChatColor> colorSupplier;

    public void setSupplier(Supplier<ChatColor> colorSupplier) {
        this.colorSupplier = colorSupplier;
    }

    @Override
    public String colorString(String text) {
        StringBuilder builder = new StringBuilder();

        for (char c : text.toCharArray()) {
            builder.append(this.colorSupplier.get());
            builder.append(c);
        }

        return builder.toString();
    }
}
