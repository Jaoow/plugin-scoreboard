package com.joaolucas.scoreboard.manager;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.factory.ScoreBoardFactory;
import com.joaolucas.scoreboard.model.ScoreArgument;
import com.joaolucas.scoreboard.model.impl.Score;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static me.clip.placeholderapi.PlaceholderAPI.setPlaceholders;
import static org.bukkit.ChatColor.getLastColors;
import static org.bukkit.scoreboard.DisplaySlot.SIDEBAR;

public class ScoreBoardManager {

    private final ScoreBoardFactory score;
    private final FileConfiguration config;

    private String title;
    private List<String> lines;

    public ScoreBoardManager(final PluginScoreboard main, final ScoreBoardFactory score) {
        this.score = score;
        this.config = main.getConfig();
        load();
    }

    public void createScoreBoard(final Player player) {
        final Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective obj = sb.registerNewObjective("score", "dummy");

        obj.setDisplayName(title);
        obj.setDisplaySlot(SIDEBAR);

        final ScoreArgument scoreArg = new Score(sb, obj);
        for (int index = 0; index < lines.size(); index++) {
            String text = lines.get(index);

            if (StringUtils.isBlank(text)) {
                text = String.valueOf(ChatColor.values()[index]);
            }

            setLine(player, scoreArg, index, text);
        }

        score.getUserScore().put(player, scoreArg);
        setScoreBoard(player, scoreArg);
    }

    public void createNoScore(final Player p) {
        final Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        final ScoreArgument scoreArg = new Score(sb, null);
        score.getUserScore().put(p, scoreArg);
        setScoreBoard(p, scoreArg);
    }

    public void updateScore(final Player p) {
        if (!isScore(p)) return;
        final ScoreArgument scoreArg = score.getUserScore().get(p);
        for (int index = 0; index < lines.size(); index++) {
            String text = lines.get(index);

            if (StringUtils.isBlank(text)) {
                text = String.valueOf(ChatColor.values()[index]);
            }

            setLine(p, scoreArg, index, text);
        }
        setScoreBoard(p, scoreArg);
    }

    public void setLine(final Player p, final ScoreArgument scoreArg, final Integer line, String text) {
        final Scoreboard sb = scoreArg.getScore();
        final Objective obj = scoreArg.getObjective();

        String[] split = text.split("%");
        String holder = null;

        for (String args : split) {
            args = "%" + args.trim() + "%";
            if (!text.contains(args)) continue;

            final String placeholder = setPlaceholders(p, args);
            if (holder == null) {
                holder = placeholder;
            }
            text = text.replace(args, placeholder);
        }

        Team team = sb.getTeam("line" + line);
        if (team == null) team = sb.registerNewTeam("line" + line);

        if (holder != null) {
            split = text.split(holder, 2);
            split[1] = holder + split[1];

            final String color = getLastColors(split[0]);
            team.addEntry(color + split[0]);
            team.setSuffix(color + split[1]);
            obj.getScore(color + split[0]).setScore(line);
            return;
        }

        if (text.length() <= 16) {
            team.addEntry(text);
            obj.getScore(text).setScore(line);
            return;
        }

        if (text.length() <= 32) {
            final String prefix = text.substring(0, 16);
            final String color = getLastColors(prefix);
            final String entry = color + text.substring(16);
            team.addEntry(entry);
            team.setPrefix(prefix);
            obj.getScore(entry).setScore(line);
            return;
        }

        final String prefix = text.substring(0, 16);
        String color = getLastColors(prefix);
        final String entry = color + text.substring(16, 32);
        color = getLastColors(entry);
        final String suffix = color + text.substring(32);

        team.addEntry(entry);
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        obj.getScore(entry).setScore(line);
    }

    public boolean isScore(final Player p) {
        final Objective obj = score.getUserScore().get(p).getObjective();
        return obj != null;
    }

    public void setScoreBoard(final Player p, final ScoreArgument score) {
        final Scoreboard sb = score.getScore();
        p.setScoreboard(sb);
    }

    public void load() {
        title = colorize(config.getString("scoreboard.title"));
        lines = colorize(config.getStringList("scoreboard.lines"));

        Collections.reverse(lines);
    }

    private String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private List<String> colorize(List<String> s) {
        return s.stream().map(this::colorize).collect(Collectors.toList());
    }
}
