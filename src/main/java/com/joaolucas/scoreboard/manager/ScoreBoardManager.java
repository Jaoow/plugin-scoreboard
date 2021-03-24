package com.joaolucas.scoreboard.manager;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.factory.ScoreBoardFactory;
import com.joaolucas.scoreboard.model.ScoreArgument;
import com.joaolucas.scoreboard.model.impl.Score;
import com.joaolucas.scoreboard.model.impl.ScoreLine;
import com.joaolucas.scoreboard.utils.LineUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
            setLine(p, scoreArg, index, text);
        }
        setScoreBoard(p, scoreArg);
    }

    public void setLine(final Player p, final ScoreArgument scoreArg, final Integer line, String text) {
        final Scoreboard score = scoreArg.getScore();
        final Objective obj = scoreArg.getObjective();

        // Replace all placeholders
        text = setPlaceholders(p, text);

        Team team = score.getTeam("line" + line);
        if (team == null) team = score.registerNewTeam("line" + line);

        ScoreLine scoreLine = ScoreLine.of(text, line);
        team.addEntry(scoreLine.getEntry());
        team.setPrefix(scoreLine.getPrefix());
        team.setSuffix(scoreLine.getSuffix());

        obj.getScore(scoreLine.getEntry()).setScore(line);
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
