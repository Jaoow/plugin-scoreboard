package com.joaolucas.scoreboard.utils;

import org.apache.commons.lang3.tuple.Pair;

import static org.apache.commons.lang3.StringUtils.substring;
import static org.bukkit.ChatColor.getLastColors;

public class LineUtils {

    public static Pair<String, String> separate(String string) {
        return Pair.of(substring(string, 0, 16), substring(string, 16, 32));
    }

    public static String parseLine(String input) {
        if (input.length() > 16) {
            Pair<String, String> pair = separate(input);
            if (pair.getKey().endsWith("§")) {
                String value = pair.getKey() + pair.getValue().charAt(0);
                String last = getLastColors(value);
                String color = last.isEmpty() ? "§r" : last;
                String prefix = pair.getKey().substring(0, 14);
                String separatorColor = getLastColors(prefix);

                return prefix + "§r" + separatorColor + pair.getKey().charAt(14) + color + pair.getValue().substring(1);
            }

            String color = getLastColors(pair.getKey());
            String value = color.isEmpty() ? "§r" : color;
            return pair.getKey() + value + pair.getValue();
        }

        return input;
    }
}