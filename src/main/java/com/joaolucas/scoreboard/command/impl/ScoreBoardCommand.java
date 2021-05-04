package com.joaolucas.scoreboard.command.impl;

import com.joaolucas.scoreboard.PluginScoreboard;
import com.joaolucas.scoreboard.command.Command;
import com.joaolucas.scoreboard.manager.ScoreBoardManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreBoardCommand extends Command {

    private final ScoreBoardManager score;

    public ScoreBoardCommand(final PluginScoreboard main) {
        super("scoreboard", "score", "sb");
        this.score = main.getScoreBoardFactory().getScoreManager();
    }

    @Override
    public boolean execute(final CommandSender sender, final String cmd, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed from the console");
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("reload")) {
                return true;
            }

            if (player.hasPermission("score.reload")) {
                score.load();
                sender.sendMessage(ChatColor.GREEN + "Scoreboard successfully reloaded.");
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to this.");
            }
            return true;

        }

        if (score.isScore(player)) {
            score.createNoScore(player);
            player.sendMessage(ChatColor.RED + "Scoreboard desativada com sucesso.");
        } else {
            score.createScoreBoard(player);
            player.sendMessage(ChatColor.GREEN + "Scoreboard ativada novamente.");
        }

        return false;
    }
}
