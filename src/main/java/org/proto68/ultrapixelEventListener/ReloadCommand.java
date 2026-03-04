package org.proto68.ultrapixelEventListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class ReloadCommand implements CommandExecutor {
    private final EventListener plugin;

    public ReloadCommand(EventListener plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (sender.hasPermission("events.reload")) {
            long before = System.currentTimeMillis();
            this.plugin.reloadConfig();
            this.plugin.setupDataFile();
            long after = System.currentTimeMillis();
            long time = after - before;
            sender.sendMessage("Plugin EventListener reloaded in " + time + "ms!");
        }

        return true;
    }
}
