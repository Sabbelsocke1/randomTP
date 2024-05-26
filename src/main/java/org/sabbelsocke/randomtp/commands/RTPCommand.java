package org.sabbelsocke.randomtp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.sabbelsocke.randomtp.RandomTP;
import org.sabbelsocke.randomtp.utils.LocationGenerator;

import java.util.concurrent.CompletableFuture;

public class RTPCommand implements CommandExecutor {

    private final RandomTP plugin;

    public RTPCommand(RandomTP rtp) {
        this.plugin = rtp;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            LocationGenerator.generateLocationAsync(plugin, player.getWorld()).thenAccept(location -> {
                player.teleportAsync(location).thenAccept(success -> {
                    if (success) {
                        player.sendMessage("Du wurdest Teleportiert!");
                    } else {
                        player.sendMessage("Teleportation fehlgeschlagen!");
                    }
                }).exceptionally(ex -> {
                    player.sendMessage("Ein fehler ist während der teleportation aufgetreten!");
                    ex.printStackTrace();
                    return null;
                });
            }).exceptionally(ex -> {
                player.sendMessage("Ein fehler ist beim finden der Location aufgetreten!");
                ex.printStackTrace();
                return null;
            });

            return true;
        }

        commandSender.sendMessage("Dieser Command ist nur für Spieler");
        return false;
    }
}
