package com.frxnklxrd.playtimed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   public void onEnable() {
      this.logServerType();
      this.checkMinecraftVersion();
      this.registerExpansion();
      this.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&fPlugin created by &aOPmasterLEO"));
   }

   private void registerExpansion() {
      if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
         (new PlayTimePAPI()).register();
      }
   }

   private void logServerType() {
      String serverName = Bukkit.getServer().getName().toLowerCase();
      if (serverName.contains("folia")) {
         getLogger().info("Folia server detected. Enabling Folia support.");
      } else if (serverName.contains("paper")) {
         getLogger().info("Paper server detected. Enabling Paper support.");
      } else {
         getLogger().info("Unknown or vanilla server detected. Running in compatibility mode.");
      }
   }

   private void checkMinecraftVersion() {
      String version = Bukkit.getBukkitVersion();
      if (!version.startsWith("1.21.4")) {
         getLogger().warning("This plugin is designed for Minecraft 1.21.4. You are running: " + version);
      }
   }
}
