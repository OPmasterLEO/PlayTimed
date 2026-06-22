package net.opmasterleo.playtimed;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.opmasterleo.playtimed.nms.StatisticHandler;

public class PlayTimedExpansion extends PlaceholderExpansion {

    private final Main plugin;

    public PlayTimedExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playtime";
    }

    @Override
    public @NotNull String getAuthor() {
        return "OPmasterLEO";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || !player.isOnline()) {
            return "0";
        }

        if (params.equalsIgnoreCase("timed") || params.equalsIgnoreCase("playtime")) {
            int ticks = StatisticHandler.getPlayTime(player.getPlayer());
            long seconds = ticks / 20L;
            long days = seconds / 86400;
            long hours = (seconds % 86400) / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;

            if (days > 0) {
                return days + "d " + hours + "h";
            } else if (hours > 0) {
                return hours + "h " + minutes + "m";
            } else if (minutes > 0) {
                return minutes + "m " + secs + "s";
            } else {
                return secs + "s";
            }
        }

        return null;
    }
}