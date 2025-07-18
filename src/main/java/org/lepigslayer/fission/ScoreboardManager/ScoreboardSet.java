package org.lepigslayer.fission.ScoreboardManager;

import java.util.*;

public class PlayerScoreboard {
    private HashMap<Integer, ScoreboardSection> sectionMap;
    private List<ScoreboardSection> orderedSections;

    public PlayerScoreboard() {
        this.sectionMap = new HashMap<>();
    }

    public PlayerScoreboard section(ScoreboardSection section, int priority){
        sectionMap.put(priority, section);
        orderedSections = sectionMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getValue)
                .toList();
        return this;
    }

    public void

}
