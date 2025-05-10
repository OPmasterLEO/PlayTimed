package com.frxnklxrd.playtimed;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayTimePAPI extends PlaceholderExpansion {
   @NotNull
   public String getIdentifier() {
      return "playtime";
   }

   @NotNull
   public String getAuthor() {
      return "FrxnkLxrd";
   }

   @NotNull
   public String getVersion() {
      return "1.0";
   }

   public String onPlaceholderRequest(Player player, String identifier) {
      if (identifier.contains("timed")) {
         long segundos = (long)(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);
         int dias = (int)(segundos / 86400L);
         segundos %= 86400L;
         int horas = (int)segundos / 3600;
         segundos %= 3600L;
         int minutos = (int)segundos / 60;
         segundos %= 60L;
         StringBuilder builder = new StringBuilder();
         if (dias > 0) {
            builder.append(dias).append("d");
            if (horas > 0) {
               builder.append(" ").append(horas).append("h");
               return builder.toString();
            } else if (minutos > 0) {
               builder.append(" ").append(minutos).append("m");
               return builder.toString();
            } else if (segundos > 0L) {
               builder.append(" ").append(segundos).append("s");
               return builder.toString();
            } else {
               return builder.toString();
            }
         } else if (horas > 0) {
            if (minutos == 0) {
               builder.append(horas).append("h");
               if (segundos > 0L) {
                  builder.append(" ").append(segundos).append("s");
               }

               return builder.toString();
            } else {
               return horas + "h " + minutos + "m";
            }
         } else if (minutos > 0) {
            if (segundos == 0L) {
               builder.append(minutos).append("m");
               return builder.toString();
            } else {
               return minutos + "m " + segundos + "s";
            }
         } else {
            return segundos + "s";
         }
      } else {
         return null;
      }
   }
}
