package org.lepigslayer.fission.Utilities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.TreeMap;

public class NumberUtils {
    private static NumberFormat numberFormat = new DecimalFormat("#,##0.##");
    private static TreeMap<Integer, String> numeralMap = new TreeMap<>();
    private static TreeMap<Double, String> abrevMap = new TreeMap<>();

    static{
        abrevMap.put(1000.0,"K");
        abrevMap.put(1000000.0,"M");
        abrevMap.put(1000000000.0,"B");
        abrevMap.put(1000000000000.0,"T");

        numeralMap.put(1000, "M");
        numeralMap.put(900, "CM");
        numeralMap.put(500, "D");
        numeralMap.put(400, "CD");
        numeralMap.put(100, "C");
        numeralMap.put(90, "XC");
        numeralMap.put(50, "L");
        numeralMap.put(40, "XL");
        numeralMap.put(10, "X");
        numeralMap.put(9, "IX");
        numeralMap.put(5, "V");
        numeralMap.put(4, "IV");
        numeralMap.put(1, "I");
    }

    public static String shorten(double number){
        Map.Entry<Double,String> entry = abrevMap.floorEntry(number);
        if(entry == null)
            return trim(number);

        double newValue = number/entry.getKey();

        return trim(newValue)+entry.getValue();
    }

    public static String trim(double number) {
        return numberFormat.format(number);
    }

    public static String asNumeral(int number) {
        int l =  numeralMap.floorKey(number);
        if ( number == l ) {
            return numeralMap.get(number);
        }
        return numeralMap.get(l) + asNumeral(number-l);
    }

    public static String formatChangeColored(double amount){
        if(amount>0){
            return "§a+"+trim(amount);
        }else{
            return "§c"+trim(amount);
        }
    }

    public static String formatChange(double amount){
        if(amount>0){
            return "+"+trim(amount);
        }else{
            return trim(amount);
        }
    }
}
