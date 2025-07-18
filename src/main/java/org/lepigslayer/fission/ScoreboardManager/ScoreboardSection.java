package org.lepigslayer.fission.ScoreboardManager;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreboardSection {
    boolean shouldDisplay(Player player);
    List<String> getLines(Player player);
}
