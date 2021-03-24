package com.joaolucas.scoreboard.command.impl;

import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ScoreBoardCommand extends Command {

    private final FileConfiguration config;
    private final ScoreBoardManager score;

    public ScoreBoardCommand(final PluginScoreboard main) {
        super("scoreboard", "score", "sb");
        this.config = main.getConfig();
        this.score = main.getScoreBoardFactory().getScoreManager();
    }

    @Override
    public boolean execute(final CommandSender sender, final String cmd, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed from the console");
            return false;
        }

        final Player p = (Player) sender;
        final String hidePerm = config.getString("Permission.Hide-Score");
        if (!p.hasPermission(hidePerm)) {
            // FIXME: 23/03/2021 ADD MESSAGE
            return false;
        }


        if (score.isScore(p)) {
            score.createNoScore(p);
            // FIXME: 23/03/2021 ADD MESSAGE
        } else {
            score.createScoreBoard(p);
            // FIXME: 23/03/2021 ADD MESSAGE
        }

        return false;
    }
}
