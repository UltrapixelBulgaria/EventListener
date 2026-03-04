package org.proto68.ultrapixelEventListener;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public class EventListener extends JavaPlugin implements CommandExecutor {
    private File dataFile;
    private File tempFile;
    private FileConfiguration dataConfig;
    private FileConfiguration tempConfig;

    public void onEnable() {
        this.setupDataFile();
        this.saveDataFile();
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") == null) {
            this.getLogger().severe("WorldGuard not found! Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            Bukkit.getPluginManager().registerEvents(new EntryListener(this), this);
            this.registerCommands();
            this.getLogger().info("EventListener enabled!");
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("events")).setExecutor(this);
        Objects.requireNonNull(this.getCommand("events")).setTabCompleter(new PluginTabCompleter(this));
        Objects.requireNonNull(this.getCommand("result")).setExecutor(new ListRegionPlayersCommand(this));
        Objects.requireNonNull(this.getCommand("result")).setTabCompleter(new PluginTabCompleter(this));
    }

    public boolean onCommand(@NonNull CommandSender sender, Command command, @NonNull String label, String @NonNull [] args) {
        if (command.getName().equalsIgnoreCase("events")) {
            if (args.length != 0) {
                String subCommand = args[0].toLowerCase();
                if (subCommand.equalsIgnoreCase("reload")) {
                    ReloadCommand reload = new ReloadCommand(this);
                    reload.onCommand(sender, command, label, args);
                } else if (subCommand.equalsIgnoreCase("addRegion")) {
                    AddRegionCommand cmd = new AddRegionCommand(this);
                    cmd.onCommand(sender, command, label, args);
                } else if (subCommand.equalsIgnoreCase("erase")) {
                    EraseCommand cmd = new EraseCommand(this);
                    cmd.onCommand(sender, command, label, args);
                } else {
                    sender.sendMessage("Unknown subcommand.");
                }

            }
            return true;
        } else {
            return false;
        }
    }

    public void setupDataFile() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.dataFile = new File(this.getDataFolder(), "data.yml");
        this.tempFile = new File(this.getDataFolder(), "temp.yml");
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!this.tempFile.exists()) {
            try {
                this.tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.dataFile);
        this.tempConfig = YamlConfiguration.loadConfiguration(this.tempFile);
    }

    public void eraseTempFile() throws IOException {
        this.tempFile.delete();
        this.tempFile.createNewFile();
        this.setupDataFile();
        this.saveDataFile();
    }

    public FileConfiguration getDataConfig() {
        return this.dataConfig;
    }

    public FileConfiguration getTempConfig() {
        return this.tempConfig;
    }

    public void saveDataFile() {
        try {
            this.dataConfig.save(this.dataFile);
            this.tempConfig.save(this.tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
