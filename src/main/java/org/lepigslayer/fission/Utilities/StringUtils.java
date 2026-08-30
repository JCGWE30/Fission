package org.lepigslayer.fission.Utilities;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.lepigslayer.fission.Colors.CustomColor;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {
    private static final char STYLE_CHARACTER = '§';

    public static String makeProgressBar(double progress, int segments){
        int activeSegments = (int) Math.floor(segments * progress);

        StringBuilder builder = new StringBuilder("§a");

        for(int i = 0; i < activeSegments; i++){
            builder.append("-");
        }

        builder.append("§7");

        for(int i = activeSegments;i < segments; i++){
            builder.append("-");
        }
        return builder.toString();
    }

    public static List<String> smartWrap(String total, int characterLimit){
        List<String> lines = new ArrayList<>();
        StringBuilder textBuilder = new StringBuilder();
        StringBuilder styleBuilder = new StringBuilder();
        StringBuilder activeStyleBuilder = new StringBuilder();

        boolean isChangingStyle = false;
        int runningTotal = 0;

        for (char c : total.toCharArray()) {
            textBuilder.append(c);

            if(isChangingStyle){
                if(c == STYLE_CHARACTER)
                    throw new IllegalArgumentException("2 Style Characters in a row");
                activeStyleBuilder.append(c);
                isChangingStyle = false;
                continue;
            }

            if(c == STYLE_CHARACTER){
                activeStyleBuilder.append(STYLE_CHARACTER);
                isChangingStyle = true;
                continue;
            }

            if(runningTotal++<characterLimit)
                continue;

            if(c != ' ')
                continue;

            String legacy = styleBuilder.toString()+textBuilder;
            String cleaned = TextComponent.fromLegacy(legacy).toLegacyText();

            lines.add(cleaned.substring(2));
            styleBuilder.append(activeStyleBuilder);
            textBuilder = new StringBuilder();
            runningTotal = 0;
        }

        String legacy = styleBuilder.toString()+textBuilder;
        String cleaned = TextComponent.fromLegacy(legacy).toLegacyText();
        lines.add(cleaned.substring(2));

        return lines;
    }

    public static List<String> wrap(String total, int characterLimit, String regex){
        Pattern pattern = Pattern.compile(regex);
        int runningCount = 0;
        StringBuilder builder = new StringBuilder();
        List<String> totalLines = new ArrayList<>();
        for(char c: total.toCharArray()){
            if(runningCount++>characterLimit){
                if(pattern.matcher(c+"").find()){
                    totalLines.add(builder.toString());
                    builder = new StringBuilder();
                    runningCount = 0;
                    continue;
                }
            }
            if(pattern.matcher(c+"").find() && runningCount == 1) continue;
            builder.append(c);
        }
        totalLines.add(builder.toString());
        return totalLines;
    }

    public static List<String> wrap(String total, int characterLimit, CustomColor color){
        Pattern pattern = Pattern.compile("[, ]");
        int runningCount = 0;
        StringBuilder builder = new StringBuilder();
        List<String> totalLines = new ArrayList<>();
        for(char c: total.toCharArray()){
            if(runningCount++>characterLimit){
                if(pattern.matcher(c+"").find()){
                    totalLines.add(color.colorString(builder.toString()));
                    builder = new StringBuilder();
                    runningCount = 0;
                    continue;
                }
            }
            if(pattern.matcher(c+"").find() && runningCount == 1) continue;
            builder.append(c);
        }
        totalLines.add(color.colorString(builder.toString()));
        return totalLines;
    }

    public static Duration getReadTime(String read){
        double wordsPerMinute = 225.0;
        double wordsPerMillisecond = wordsPerMinute / 60000.0;

        if (read == null || read.trim().isEmpty()) {
            return Duration.ZERO;
        }

        // Replace multiple spaces with single space and trim
        String cleanedText = read.trim().replaceAll("\\s+", " ");
        // Split by whitespace
        String[] words = cleanedText.split(" ");
        int wordCount = words.length;

        long durationMs = (long) (wordCount / wordsPerMillisecond);
        return Duration.ofMillis(durationMs);
    }
}
