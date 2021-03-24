package com.joaolucas.scoreboard.factory;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import com.joaolucas.scoreboard.model.ScoreArgument;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ScoreBoardFactory {

    private final ScoreBoardManager scoreManager;
    private final Map<Player, ScoreArgument> userScore;

    public ScoreBoardFactory(final PluginScoreboard main) {
        this.scoreManager = new ScoreBoardManager(main, this);
        this.userScore = new HashMap<>();
    }
}
