package com.joaolucas.scoreboard.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public abstract class CommandArgument {
    private final Boolean isPlayerExclusive;
    private final String argumentName;
    private final String[] argumentAliases;

    public CommandArgument(final boolean isPlayerExclusive, final String argumentName, final String... argumentAliases) {
        this.isPlayerExclusive = isPlayerExclusive;
        this.argumentName = argumentName;
        this.argumentAliases = argumentAliases;
    }

    public abstract void execute(final CommandSender sender, final String[] args);
}
