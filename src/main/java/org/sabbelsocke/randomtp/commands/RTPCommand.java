package org.sabbelsocke.randomtp.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.sabbelsocke.randomtp.RandomTP;
import org.sabbelsocke.randomtp.utils.LocationGenerator;

import java.util.concurrent.ExecutionException;

public class RTPCommand implements CommandExecutor {

    private final RandomTP plugin;

    public RTPCommand(RandomTP rtp) {
        this.plugin = rtp;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            try {
                player.teleportAsync(LocationGenerator.generateLocationAsync(plugin, player.getWorld()));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

        return false;
    }
}
