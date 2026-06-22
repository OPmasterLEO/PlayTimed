package net.opmasterleo.playtimed;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.bukkit.plugin.java.JavaPlugin;

import net.opmasterleo.playtimed.scheduler.TaskScheduler;

public final class Main extends JavaPlugin {
   private static Main instance;
   private static TaskScheduler scheduler;

    @Override
    public void onEnable() {
       instance = this;
       scheduler = new TaskScheduler(this);
       registerExpansion();
       
       getLogger().info(String.format("PlayTimed v%s has been enabled!", getPluginMeta().getVersion()));
       getLogger().info("Plugin created by OPmasterLEO (Discord: opmasterleo)");
       getLogger().info("Using Minecraft's PLAY_ONE_MINUTE statistic for playtime tracking");
    }

   @Override
   public void onDisable() {
      if (scheduler != null) {
         scheduler.cancelTasks();
      }
      
      getLogger().info("PlayTimed has been disabled!");
      instance = null;
   }


   public <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
      CompletableFuture<T> future = new CompletableFuture<>();
      
      scheduler.runTaskAsynchronously(() -> {
         try {
            T result = supplier.get();
            future.complete(result);
         } catch (Exception e) {
            future.completeExceptionally(e);
            getLogger().severe(String.format("Error in async task: %s", e.getClass().getSimpleName()));
         }
      });
      
      return future;
   }

   public <T> CompletableFuture<T> runSync(Supplier<T> supplier) {
      CompletableFuture<T> future = new CompletableFuture<>();
      
      scheduler.runTask(() -> {
         try {
            T result = supplier.get();
            future.complete(result);
         } catch (Exception e) {
            future.completeExceptionally(e);
            getLogger().severe(String.format("Error in sync task: %s", e.getClass().getSimpleName()));
         }
      });
      
      return future;
   }

   private void registerExpansion() {
      PlayTimedExpansion expansion = new PlayTimedExpansion(this);
      if (expansion.canRegister() && expansion.register()) {
         getLogger().info("Registered PlaceholderAPI expansion: %playtime_timed%");
      } else {
         getLogger().warning("Failed to register PlaceholderAPI expansion - PlaceholderAPI not installed?");
      }
   }

   public static Main getInstance() {
      return instance;
   }

   public static TaskScheduler getScheduler() {
      return scheduler;
   }
}