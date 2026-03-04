package org.proto68.ultrapixelEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ListRegionPlayersCommand implements CommandExecutor {
    private final EventListener plugin;

    public ListRegionPlayersCommand(EventListener plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NonNull [] args) {
        if (!sender.hasPermission("events.result")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        } else if (args.length < 1) {
            sender.sendMessage("Usage: /result <region> [amount]");
            return true;
        } else {
            String regionName = args[0];
            int amount = 3;
            if (args.length >= 2) {
                try {
                    amount = Integer.parseInt(args[1]);
                    if (amount <= 0) {
                        amount = 3;
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            String orderType = this.plugin.getDataConfig().getString("regions." + regionName + ".order", "first");
            ConfigurationSection section = this.plugin.getTempConfig().getConfigurationSection("players." + regionName);
            if (section != null && !section.getKeys(false).isEmpty()) {
                Map<String, LocalDateTime> playerTimes = new HashMap<>();

                for(String playerName : section.getKeys(false)) {
                    String timeString = section.getString(playerName);
                    if (timeString != null) {
                        try {
                            LocalDateTime time = LocalDateTime.parse(timeString);
                            playerTimes.put(playerName, time);
                        } catch (Exception ignored) {
                        }
                    }
                }

                List<Map.Entry<String, LocalDateTime>> sortedList = new ArrayList<>(playerTimes.entrySet());
                sortedList.sort(Entry.comparingByValue());
                if (orderType.equalsIgnoreCase("last")) {
                    Collections.reverse(sortedList);
                }

                String var10001 = String.valueOf(ChatColor.GOLD);
                sender.sendMessage(var10001 + "Top " + ChatColor.YELLOW + amount + ChatColor.GOLD + " players in region " + ChatColor.AQUA + "'" + regionName + "' " + ChatColor.GRAY + "(" + orderType + ")" + ChatColor.GOLD + ":");
                int counter = 1;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.n");

                for(Map.Entry<String, LocalDateTime> entry : sortedList) {
                    if (counter > amount) {
                        break;
                    }

                    String formattedTime = entry.getValue().format(formatter);
                    sender.sendMessage(ChatColor.GRAY + String.valueOf(counter) + ". " + ChatColor.GREEN + entry.getKey() + ChatColor.DARK_GRAY + " | " + ChatColor.AQUA + formattedTime);
                    ++counter;
                }

            } else {
                sender.sendMessage("No players found in region: " + regionName);
            }
            return true;
        }
    }
}
