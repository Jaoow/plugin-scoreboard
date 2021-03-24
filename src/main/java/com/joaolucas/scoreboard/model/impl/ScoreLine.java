package com.joaolucas.scoreboard.model.impl;

import com.joaolucas.scoreboard.utils.LineUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public class ScoreLine {

    private final String entry;
    private final String prefix;
    private final String suffix;

    public static ScoreLine of(String text, int line) {
        Pair<String, String> separated = LineUtils.separate(LineUtils.parseLine(text));
        return new ScoreLine(String.valueOf(ChatColor.values()[line]), separated.getKey(), separated.getValue());
    }
}
