# Ultrapixel Event Listener (Bukkit)

Simple minecraft plugin, that listens for players entering a region and then creating a leaderboard

>Feel free to alter the code of this plugin to your needs

> [!IMPORTANT]
> The plugin is tested only on 1.21.10 Paper <br />
> The plugin needs [WorldEdit](https://dev.bukkit.org/projects/worldedit/files), [WorldGuard](https://dev.bukkit.org/projects/worldguard/files) and [WorldGuard Events](https://www.spigotmc.org/resources/worldguard-events.65176/) installed in order to enable! <br />
> The last plugin `WorldGuard Events` is seriously outdated, so proceed with caution


## Features
* Multiple regions
* Two types of leaderboard (first to win or last)
* Commands for the plugin
> /events reload \
> /events addRegion `regionName` `first/last` - sets the region to be monitored \
> /events erase `regionName/all` - deletes all player data for certain region or for all of them \
> /result `regionName` `count` - displays the leaderboard for a region, by default shows Top 3, could be altered for any number 


## Installation
1. Download the latest release on the right side of the page.
2. Drop the `.jar` file in your `plugins` folder.
3. Download `WorldEdit` from [here](https://dev.bukkit.org/projects/worldedit/files)
4. Download `WorldGuard` from [here](https://dev.bukkit.org/projects/worldguard/files)
5. Download `WorldGuard Events` from [here](https://www.spigotmc.org/resources/worldguard-events.65176/) 
6. Add the plugins in your `plugins` folder.
7. Start the server and set up some regions!
