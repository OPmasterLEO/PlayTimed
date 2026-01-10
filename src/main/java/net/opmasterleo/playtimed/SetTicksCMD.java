package net.opmasterleo.playtimed;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.opmasterleo.playtimed.nms.StatisticHandler;

public class SetTicksCMD implements CommandExecutor {
   
   private final Main plugin;
   public SetTicksCMD(Main plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (!sender.hasPermission("playtimed.admin")) {
         sender.sendMessage(Component.text("You don't have permission to use this command!")
               .color(NamedTextColor.RED));
         return true;
      }

      if (args.length < 2) {
         sender.sendMessage(Component.text("Usage: /setticks <player> <ticks>")
               .color(NamedTextColor.RED));
         return true;
      }

      String targetName = args[0];
      String ticksString = args[1];
      int ticks;
      try {
         ticks = Integer.parseInt(ticksString);
         if (ticks < 0) {
            sender.sendMessage(Component.text("Ticks value must be non-negative!")
                  .color(NamedTextColor.RED));
            return true;
         }
      } catch (NumberFormatException e) {
         sender.sendMessage(Component.text("Invalid ticks value: " + ticksString)
               .color(NamedTextColor.RED));
         return true;
      }

      plugin.runAsync(() -> {
         Player target = Bukkit.getPlayer(targetName);
         
         if (target == null || !target.isOnline()) {
            sender.sendMessage(Component.text("Player '" + targetName + "' is not online!")
                  .color(NamedTextColor.RED));
            return null;
         }

         plugin.runSync(() -> {
            try {
               StatisticHandler.setPlayTime(target, ticks);
               
               long seconds = ticks / 20L;
               long hours = seconds / 3600;
               long minutes = (seconds % 3600) / 60;
               long secs = seconds % 60;
               
               String timeFormatted = String.format("%dh %dm %ds", hours, minutes, secs);
               
               sender.sendMessage(Component.text("Successfully set playtime for ")
                     .color(NamedTextColor.GREEN)
                     .append(Component.text(target.getName()).color(NamedTextColor.YELLOW))
                     .append(Component.text(" to ").color(NamedTextColor.GREEN))
                     .append(Component.text(ticks + " ticks").color(NamedTextColor.YELLOW))
                     .append(Component.text(" (" + timeFormatted + ")").color(NamedTextColor.GRAY)));

               target.sendMessage(Component.text("Your playtime has been set to ")
                     .color(NamedTextColor.YELLOW)
                     .append(Component.text(timeFormatted).color(NamedTextColor.GREEN)));
               
               plugin.getLogger().info(String.format("%s set playtime for %s to %d ticks", 
                     sender.getName(), target.getName(), ticks));
               
            } catch (IllegalArgumentException | IllegalStateException e) {
               sender.sendMessage(Component.text("Error setting playtime: " + e.getMessage())
                     .color(NamedTextColor.RED));
               plugin.getLogger().warning(String.format("Error in /setticks command: %s", e.getMessage()));
            }
            return null;
         });
         
         return null;
      });

      return true;
   }
}
