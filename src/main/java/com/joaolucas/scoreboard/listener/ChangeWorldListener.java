package com.joaolucas.scoreboard.listener;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class ChangeWorldListener implements Listener {

    private static final List<String> WORLD_BLACKLIST = PluginScoreboard.getInstance().getConfig().getStringList("scoreboard.world-black-list");

    private final ScoreBoardManager score;

    public ChangeWorldListener(PluginScoreboard main) {
        this.score = main.getScoreBoardFactory().getScoreManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (WORLD_BLACKLIST.contains(event.getPlayer().getWorld().getName())) {
            score.createNoScore(event.getPlayer());
        } else {
            score.createScoreBoard(event.getPlayer());
        }
    }
}
