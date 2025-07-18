package org.lepigslayer.fission.ScoreboardManager;

import org.bukkit.entity.Player;

import java.util.*;

public class ScoreboardSet {
    private HashMap<Integer, ScoreboardSection> sectionMap;
    private List<ScoreboardSection> orderedSections;
    String name;

    public ScoreboardSet(String name) {
        this.sectionMap = new HashMap<>();
        this.orderedSections = new ArrayList<>();
        this.name = name;
    }

    public ScoreboardSet section(ScoreboardSection section, int priority){
        sectionMap.put(priority, section);
        orderedSections = sectionMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getValue)
                .toList();
        return this;
    }

    List<String> getLines(Player player){
        List<String> lines = new ArrayList<>();
        for (ScoreboardSection section : orderedSections) {
            if(!section.shouldDisplay(player))
                continue;

            List<String> newLines = section.getLines(player);

            if(lines.size()+newLines.size()>15)
                break;
            lines.addAll(newLines);

            if(lines.size()+1>15)
                break;
            lines.add(" ");
        }

        if(lines.getLast().isBlank()){
            lines.removeLast();
        }

        int blankIndex = 0;
        for(int i = 0; i<lines.size(); i++){
            String line = lines.get(i);

            if(line.isBlank()){
                lines.set(i, generateMarker(blankIndex++));
            }
        }

        return lines.reversed();
    }

    private String generateMarker(int number){
        StringBuilder builder = new StringBuilder();
        for (char c : String.valueOf(number).toCharArray()) {
            builder.append("§")
                    .append(c);
        }
        return builder.toString();
    }
}
