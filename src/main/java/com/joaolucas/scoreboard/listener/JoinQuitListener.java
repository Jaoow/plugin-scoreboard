package com.joaolucas.scoreboard.listener;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.factory.ScoreBoardFactory;
import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private final ScoreBoardFactory score;
    private final ScoreBoardManager scoreManager;

    @SneakyThrows
    public JoinQuitListener(final PluginScoreboard main) {
        this.score = main.getScoreBoardFactory();
        this.scoreManager = score.getScoreManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        scoreManager.createScoreBoard(p);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        score.getUserScore().remove(p);
    }
}