package org.proto68.ultrapixelEventListener;

import org.proto68.wgevents.events.RegionEnteredEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class EntryListener implements Listener {
    private final EventListener plugin;

    public EntryListener(EventListener plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRegionEntered(RegionEnteredEvent event) {
        Player player = Bukkit.getPlayer(event.getUUID());
        if (player != null) {
            String regionName = event.getRegionName();
            Set<String> regionNames = Objects
                    .requireNonNull(this.plugin.getDataConfig().getConfigurationSection("regions"))
                    .getKeys(false);

            if (regionNames.contains(regionName)) {
                String path = "players." + regionName + "." + player.getName();

                if (!this.plugin.getTempConfig().contains(path)) {
                    this.plugin.getTempConfig().set(path, LocalDateTime.now().toString());
                    this.plugin.saveDataFile();
                }
            }
        }
    }
}