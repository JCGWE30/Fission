package org.lepigslayer.fission.Colors;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class GradientColor implements CustomColor {
    private static final List<ChatColor> GRADIENT_CHART = Arrays.asList(
            ChatColor.DARK_RED,
            ChatColor.RED,
            ChatColor.GOLD,
            ChatColor.YELLOW,
            ChatColor.DARK_GREEN,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_BLUE,
            ChatColor.BLUE,
            ChatColor.LIGHT_PURPLE,
            ChatColor.DARK_PURPLE,
            ChatColor.WHITE,
            ChatColor.GRAY,
            ChatColor.DARK_GRAY,
            ChatColor.BLACK
    );

    private double minStep;
    private int startIndex;
    private int endIndex;

    public GradientColor(ChatColor startColor, ChatColor endColor) {
        this(startColor, endColor, Double.MIN_VALUE);
    }

    public GradientColor(ChatColor startColor, ChatColor endColor, double minStep) {
        if (!GRADIENT_CHART.contains(startColor) || !GRADIENT_CHART.contains(endColor))
            throw new IllegalArgumentException("Invalid gradient color");

        this.minStep = minStep;
        this.startIndex = GRADIENT_CHART.indexOf(startColor);
        this.endIndex = GRADIENT_CHART.indexOf(endColor);
    }

    @Override
    public String colorString(String text) {
        double gradientLength = Math.abs(endIndex - startIndex);
        double step = gradientLength / text.length();
        step *= Integer.signum(endIndex - startIndex);

        step = Math.max(step, minStep);

        StringBuilder color = new StringBuilder();

        double lastIndex = Double.MIN_VALUE;
        double currentIndex = startIndex;

        for (char c : text.toCharArray()) {

            if (Character.isSpaceChar(c)){
                color.append(c);
                continue;
            }

            double realValue = Math.floor(currentIndex);

            if(realValue != lastIndex) {
                lastIndex = realValue;
                color.append(GRADIENT_CHART.get((int) realValue));
            }

            currentIndex += step;
            currentIndex %= endIndex;

            color.append(c);
        }

        return color.toString();
    }
}
