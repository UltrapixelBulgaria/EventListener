package org.proto68.ultrapixelEventListener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddRegionCommand implements CommandExecutor {
    private final EventListener plugin;

    public AddRegionCommand(EventListener plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player player) {
            if (!sender.hasPermission("events.add")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            } else if (args.length != 3) {
                player.sendMessage(ChatColor.YELLOW + "Usage: /events addRegion <regionName> <order>");
                return true;
            } else {
                String name = args[1];
                String order = args[2];
                RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));

                assert regionManager != null;

                ProtectedRegion region = regionManager.getRegion(name);
                if (region == null) {
                    player.sendMessage("There is no region with this name in this world!");
                } else {
                    this.plugin.getDataConfig().set("regions." + name + ".order", order);
                    this.plugin.getDataConfig().set("regions." + name + ".world", player.getWorld().getName());
                    this.plugin.saveDataFile();
                    player.sendMessage(ChatColor.GOLD + "Region " + ChatColor.GREEN + name + ChatColor.GOLD + " saved successfully!");
                }
                return true;
            }
        } else {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
    }
}
