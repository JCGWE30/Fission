package org.lepigslayer.fission.Testing.ScoreboardManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.lepigslayer.fission.Fission;
import org.lepigslayer.fission.ScoreboardManager.ScoreboardManager;
import org.lepigslayer.fission.ScoreboardManager.ScoreboardSet;
import org.lepigslayer.fission.ScoreboardManager.ScoreboardSection;

public class GenerateScoreboardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;
        Player player = (Player) commandSender;
        ScoreboardSet playerScoreboard = new ScoreboardSet("Cool Scoreboard")
                .section(new TestScoreboardSection("I should be first :)"), 500)
                .section(new TestScoreboardSection("I should be last :("), 0)
                .section(new TestScoreboardSection("I should be somewhere in the middle :|"), 250);
        ScoreboardManager.assignScoreboard(player, playerScoreboard);
        Fission.log("Done");

        return true;
    }
}
