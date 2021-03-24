package com.joaolucas.scoreboard.factory;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.task.ScoreUpdateTask;
import lombok.Getter;

@Getter
public class TaskFactory {

    private final ScoreUpdateTask scoreUpdateTask;

    public TaskFactory(final PluginScoreboard main) {
        this.scoreUpdateTask = new ScoreUpdateTask(main);
    }
}
