package org.lepigslayer.fission.ScoreboardManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerScoreboard {
    private Player player;
    private ScoreboardSet currentSet;

    private Scoreboard scoreboard;
    private Objective objective;
    private String displayName;

    public PlayerScoreboard(Player player) {
        this.player = player;
        this.displayName = " ";

        scoreboard = player.getScoreboard();
        objective = scoreboard.registerNewObjective(player.getUniqueId().toString(), Criteria.DUMMY," ");
        player.setScoreboard(scoreboard);
    }

    void setCurrentSet(ScoreboardSet currentSet) {
        this.currentSet = currentSet;
        this.displayName = currentSet.name;

        objective.setDisplayName(displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        update();
    }

    void update(){
        if(currentSet == null)
            return;

        List<String> lines = currentSet.getLines(player);
        int startingIndex = (int) Math.floor((lines.size() / 2.0) * -1);
        startingIndex = startingIndex == -1 ? lines.size() : startingIndex;

        objective.unregister();
        objective = scoreboard.registerNewObjective(player.getUniqueId().toString(), Criteria.DUMMY, displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (String line : lines)
            objective.getScore(line).setScore(startingIndex++);
    }
}
