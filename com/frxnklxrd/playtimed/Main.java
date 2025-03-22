package com.frxnklxrd.playtimed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   public void onEnable() {
      this.registerExpansion();
      this.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&fPlugin created by &aFrxnklxrd"));
   }

   private void registerExpansion() {
      if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
         (new PlayTimePAPI()).register();
      }

   }
}
