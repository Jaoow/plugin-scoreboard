package com.joaolucas.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@AllArgsConstructor
@Data
public abstract class ScoreArgument {

    private final Scoreboard score;
    private final Objective objective;

}
