package com.joaolucas.scoreboard.model.impl;

import com.joaolucas.scoreboard.model.ScoreArgument;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Score extends ScoreArgument {

    public Score(final Scoreboard score, final Objective obj) {
        super(score, obj);
    }
}
