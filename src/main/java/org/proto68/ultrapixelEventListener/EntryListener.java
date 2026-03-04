package org.proto68.ultrapixelEventListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EntryListener implements Listener {
    private final EventListener plugin;

    public EntryListener(EventListener plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onRegionEntered(RegionEnteredEvent event) {
        Player player = Bukkit.getPlayer(event.getUUID());
        if (player != null) {
            String regionName = event.getRegionName();
            Set<String> regionNames = Objects.requireNonNull(this.plugin.getDataConfig().getConfigurationSection("regions")).getKeys(false);
            if (regionNames.contains(regionName)) {
                this.plugin.getTempConfig().set("players." + regionName + "." + player.getName(), LocalDateTime.now().toString());
                this.plugin.saveDataFile();
            }

        }
    }
}
