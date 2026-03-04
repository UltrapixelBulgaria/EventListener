package org.proto68.ultrapixelEventListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.IOException;

public class EraseCommand implements CommandExecutor {
    private final EventListener plugin;

    public EraseCommand(EventListener plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NonNull [] args) {
        if (!sender.hasPermission("events.erase")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        } else {

            if (args.length != 2){
                sender.sendMessage(ChatColor.GOLD + "Usage: /events erase <regionName/all>");
            }

            String eventName = args[1];
            String path = "players." + eventName;
            if (this.plugin.getTempConfig().contains(path)) {
                this.plugin.getTempConfig().set(path, null);
                this.plugin.saveDataFile();
                sender.sendMessage(ChatColor.GREEN + "Players for region " + eventName + " were deleted successfully!");
            } else if (args[1].equalsIgnoreCase("all")) {
                try {
                    this.plugin.eraseTempFile();
                    sender.sendMessage(ChatColor.GREEN + "Temp file has been completely cleared.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Can't find region " + eventName);
            }

        }
        return true;
    }
}
