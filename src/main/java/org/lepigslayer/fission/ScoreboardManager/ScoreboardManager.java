package org.lepigslayer.fission.ScoreboardManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lepigslayer.fission.Fission;
import org.lepigslayer.fission.Utilities.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager implements Listener{
    private static ScoreboardManager instance;

    private HashMap<Player, PlayerScoreboard> playerScoreboardMap;

    public ScoreboardManager() {
        instance = this;
        this.playerScoreboardMap = new HashMap<>();

        TimeUtils.runTask(Fission.class,this::tick,10);
    }

    public static void assignScoreboard(Player player, ScoreboardSet set){
        instance.playerScoreboardMap.get(player).setCurrentSet(set);
    }

    private void tick(){
        for (Map.Entry<Player, PlayerScoreboard> entry : playerScoreboardMap.entrySet()) {
            entry.getValue().update();
        }
    }

    @EventHandler
    private void playerQuit(PlayerQuitEvent e) {
        playerScoreboardMap.remove(e.getPlayer());
    }

    @EventHandler
    private void playerJoin(PlayerJoinEvent e) {
        playerScoreboardMap.put(e.getPlayer(), new PlayerScoreboard(e.getPlayer()));
    }
}
