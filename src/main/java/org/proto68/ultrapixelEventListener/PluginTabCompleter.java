package org.proto68.ultrapixelEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class PluginTabCompleter implements TabCompleter {
    private final EventListener plugin;

    public PluginTabCompleter(EventListener plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String @NonNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return null;
        } else {
            List<String> suggestions = new ArrayList<>();
            if (command.getName().equalsIgnoreCase("events")) {
                if (args.length == 1) {
                    if (sender.hasPermission("events.add")) {
                        suggestions.add("addRegion");
                    }

                    if (sender.hasPermission("events.erase")) {
                        suggestions.add("erase");
                    }

                    if (sender.hasPermission("events.reload")) {
                        suggestions.add("reload");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("addRegion")) {
                    if (sender.hasPermission("events.add")) {
                        suggestions.add("<regionName>");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("erase")) {
                    if (sender.hasPermission("events.erase")) {
                        suggestions.addAll(Objects.requireNonNull(this.plugin.getTempConfig().getConfigurationSection("players")).getKeys(false));
                    }
                } else if (args.length == 3 && args[0].equalsIgnoreCase("addRegion") && sender.hasPermission("events.add")) {
                    suggestions.add("first");
                    suggestions.add("last");
                }
            }

            if (command.getName().equalsIgnoreCase("result")) {
                if (args.length == 1) {
                    if (sender.hasPermission("events.result")) {
                        try {
                        suggestions.addAll(Objects.requireNonNull(this.plugin.getDataConfig().getConfigurationSection("regions")).getKeys(false));
                        } catch (Exception ingored){
                            sender.sendMessage("Error! Empty regions!");
                        }
                    }
                } else if (args.length == 2 && sender.hasPermission("events.result")) {
                    suggestions.add("[count]");
                }
            }

            return suggestions;
        }
    }
}
