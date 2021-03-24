package com.joaolucas.scoreboard;

import com.joaolucas.scoreboard.command.impl.ScoreBoardCommand;
import com.joaolucas.scoreboard.factory.ScoreBoardFactory;
import com.joaolucas.scoreboard.factory.TaskFactory;
import com.joaolucas.scoreboard.listener.JoinQuitListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.bukkit.event.HandlerList.unregisterAll;

@Getter
public class PluginScoreboard extends JavaPlugin {

    @Getter
    private static PluginScoreboard instance;

    private ExecutorService executor;
    private ScheduledExecutorService scheduled;

    private ScoreBoardFactory scoreBoardFactory;
    private TaskFactory taskFactory;


    @Override
    public void onEnable() {
        instance = this;

        executor = newCachedThreadPool();
        scheduled = newScheduledThreadPool(5);

        saveDefaultConfig();

        scoreBoardFactory = new ScoreBoardFactory(this);
        taskFactory = new TaskFactory(this);

        loadCommands();
        loadListeners();
        loadTasks();

        for (Player player : Bukkit.getOnlinePlayers()) {
            scoreBoardFactory.getScoreManager().createScoreBoard(player);
        }
    }

    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            scoreBoardFactory.getScoreManager().createNoScore(player);
        }

        executor.shutdown();
        scheduled.shutdown();
        unregisterAll();
    }

    private void loadCommands() {
        new ScoreBoardCommand(this);
    }

    private void loadListeners() {
        new JoinQuitListener(this);
    }

    private void loadTasks() {
        final int timeUpdateScore = getConfig().getInt("scoreboard.update-delay", 10);
        scheduled.scheduleWithFixedDelay(taskFactory.getScoreUpdateTask(), timeUpdateScore, timeUpdateScore, SECONDS);
    }
}
