package com.frxnklxrd.playtimed;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTicksCMD implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!sender.hasPermission("playtimed.admin")) {
         return false;
      }
      // Argument check for safety and 1.21.4 compatibility
      if (args.length < 2) {
         return false;
      }
      Player target = Bukkit.getPlayer(args[0]);
      if (target != null && target.isOnline()) {
         try {
            int ticks = Integer.parseInt(args[1]);
            target.setStatistic(Statistic.PLAY_ONE_MINUTE, ticks);
         } catch (NumberFormatException e) {
            // Do nothing, silent failure
         }
         return true;
      } else {
         return false;
      }
   }
}
