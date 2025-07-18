package org.lepigslayer.fission.Testing.ScoreboardManager;

import org.bukkit.entity.Player;
import org.lepigslayer.fission.ScoreboardManager.ScoreboardSection;
import org.lepigslayer.fission.Utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestScoreboardSection implements ScoreboardSection {
    private String preText;
    private int tickCounter;

    public TestScoreboardSection(String preText) {
        this.preText = preText;
    }

    @Override
    public boolean shouldDisplay(Player player) {
        return true;
    }

    @Override
    public List<String> getLines(Player player) {
        tickCounter--;

        if(tickCounter < 0)
            tickCounter = Integer.MAX_VALUE;

        List<String> lines = new ArrayList<>();
        lines.add(StringUtils.rainbowString(preText,true,3,tickCounter));
        return lines;
    }
}
