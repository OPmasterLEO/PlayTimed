package net.opmasterleo.playtimed;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlayTimePAPI extends PlaceholderExpansion {
   @Override
   @NotNull
   public String getIdentifier() {
      return "playtime";
   }

   @Override
   @NotNull
   public String getAuthor() {
      return "FrxnkLxrd";
   }

   @Override
   @NotNull
   public String getVersion() {
      return "1.1";
   }

   @Override
   public String onPlaceholderRequest(Player player, String identifier) {
      if (player == null) {
         return "";
      }

      String key = identifier.toLowerCase();
      String mode = null;
      if (key.contains(":")) {
         String[] parts = key.split(":", 2);
         key = parts[0];
         mode = parts[1];
      } else if (key.contains("_")) {
         String[] parts = key.split("_", 2);
         key = parts[0];
         mode = parts[1];
      }

      boolean askAj = key.contains("aj") || "ajlb".equals(key) || "aj".equals(key);
      long seconds = -1L;

      if (askAj) {
         Long aj = getAJLeaderboardsPlaytimeSeconds(player);
         if (aj != null) {
            seconds = aj;
         }
      }

      if (seconds < 0) {
         long ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
         seconds = ticks / 20L;
      }

      if ("seconds".equals(key) || "second".equals(key) || "s".equals(key)) {
         return String.valueOf(seconds);
      }
      if ("minutes".equals(key) || "minute".equals(key) || "m".equals(key)) {
         return String.valueOf(seconds / 60);
      }
      if ("hours".equals(key) || "hour".equals(key) || "h".equals(key)) {
         return String.valueOf(seconds / 3600);
      }

      boolean compact = "compact".equals(mode) || "short".equals(mode) || "c".equals(mode) || "compact".equals(key);
      boolean full = "full".equals(mode) || "long".equals(mode) || "f".equals(mode);

      if (compact) {
         return formatCompact(seconds, 2);
      } else if (full) {
         return formatFull(seconds);
      } else {
         return formatCompact(seconds, 2);
      }
   }

   private String formatFull(long totalSeconds) {
      return formatCompact(totalSeconds, 2);
   }

   private String formatCompact(long totalSeconds, int maxUnits) {
      if (totalSeconds <= 0) return "0s";
      long days = TimeUnit.SECONDS.toDays(totalSeconds);
      long hours = TimeUnit.SECONDS.toHours(totalSeconds) - TimeUnit.DAYS.toHours(days);
      long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalSeconds));
      long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalSeconds));

      String[][] units = {
            {String.valueOf(days), "d"},
            {String.valueOf(hours), "h"},
            {String.valueOf(minutes), "m"},
            {String.valueOf(seconds), "s"}
      };

      StringBuilder sb = new StringBuilder();
      int added = 0;
      for (String[] u : units) {
         long val = Long.parseLong(u[0]);
         if (val > 0) {
            if (added > 0) sb.append(" ");
            sb.append(val).append(u[1]);
            added++;
            if (added >= maxUnits) break;
         }
      }
      if (sb.length() == 0) return "0s";
      return sb.toString();
   }

   private Long getAJLeaderboardsPlaytimeSeconds(Player player) {
      Plugin plugin = Bukkit.getPluginManager().getPlugin("AJLeaderboards");
      if (plugin == null) return null;
      try {
         Method[] methods = plugin.getClass().getMethods();
         for (Method m : methods) {
            String name = m.getName().toLowerCase();
            if ((name.contains("play") && name.contains("time")) || name.contains("playtime")) {
               Class<?>[] params = m.getParameterTypes();
               if (params.length == 1 &&
                     (params[0].equals(Player.class) || params[0].equals(UUID.class) || params[0].equals(String.class))) {
                  Object arg;
                  if (params[0].equals(Player.class)) arg = player;
                  else if (params[0].equals(UUID.class)) arg = player.getUniqueId();
                  else arg = player.getUniqueId().toString();

                  Object res = m.invoke(plugin, arg);
                  if (res instanceof Number) {
                     long value = ((Number) res).longValue();
                     if (value > 10_000_000L) {
                        return value / 20L;
                     }
                     if (value > 1_000_000L && value < 10_000_000L) {
                        return value / 1000L;
                     }
                     return value;
                  }
               }
            }
         }
      } catch (ReflectiveOperationException | IllegalArgumentException | SecurityException ignored) {
      }
      return null;
   }
}
