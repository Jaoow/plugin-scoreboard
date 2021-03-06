package com.joaolucas.scoreboard.listener;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.factory.ScoreBoardFactory;
import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private final ScoreBoardFactory score;
    private final ScoreBoardManager scoreManager;

    @SneakyThrows
    public JoinQuitListener(PluginScoreboard main) {
        this.score = main.getScoreBoardFactory();
        this.scoreManager = score.getScoreManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val player = event.getPlayer();
        scoreManager.createScoreBoard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        val player = event.getPlayer();
        score.getUserScore().remove(player);
    }

    @EventHandler
    public void onQuit(PlayerKickEvent event) {
        val player = event.getPlayer();
        score.getUserScore().remove(player);
    }
}