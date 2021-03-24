package com.joaolucas.scoreboard.task;

import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import com.joaolucas.scoreboard.PluginScoreboard;
import org.bukkit.Bukkit;

public class ScoreUpdateTask implements Runnable {

    private final ScoreBoardManager score;

    public ScoreUpdateTask(final PluginScoreboard main) {
        this.score = main.getScoreBoardFactory().getScoreManager();
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(score::updateScore);
    }
}
