package net.opmasterleo.playtimed;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   @Override
   public void onEnable() {
      this.registerExpansion();
      this.getLogger().info("Plugin created by OPmasterLEO (Discord: leqop)");
   }

   private void registerExpansion() {
      if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
         (new PlayTimePAPI()).register();
      }

   }
}
