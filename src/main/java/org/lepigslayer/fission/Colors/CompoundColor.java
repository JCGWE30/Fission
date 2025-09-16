package org.lepigslayer.fission.Colors;

import java.util.ArrayList;
import java.util.List;

public class CompoundColor implements CustomColor {
    private List<CustomColor> colors;

    public static CompoundColor of(CustomColor... colors){
        return new CompoundColor(List.of(colors));
    }

    public static CompoundColor ofBase(CustomColor base, CustomColor... colors){
        List<CustomColor> colorList = new ArrayList<>();
        colorList.add(base);
        colorList.addAll(List.of(colors));
        return new CompoundColor(colorList);
    }

    private CompoundColor(List<CustomColor> colors){
        this.colors = colors;
    }

    @Override
    public String colorString(String text) {
        String coloring = text;
        for (CustomColor color : colors) {
            coloring = color.colorString(coloring);
        }
        return coloring;
    }
}
