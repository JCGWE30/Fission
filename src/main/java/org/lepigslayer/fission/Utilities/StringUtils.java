package org.lepigslayer.fission.Utilities;

import org.bukkit.Bukkit;
import org.lepigslayer.fission.Colors.CustomColor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    public static String makeProgressBar(double progress, int segments){
        int activeSegments = (int) Math.floor(segments * progress);

        StringBuilder builder = new StringBuilder("§a");

        for(int i = 0; i < activeSegments; i++){
            builder.append("☷");
        }

        builder.append("§7");

        for(int i = activeSegments;i < segments; i++){
            builder.append("☷");
        }
        return builder.toString();
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
