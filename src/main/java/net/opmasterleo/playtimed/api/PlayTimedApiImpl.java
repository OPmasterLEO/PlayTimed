package net.opmasterleo.playtimed.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;

import net.opmasterleo.playtimed.nms.StatisticHandler;

public class PlayTimedApiImpl implements PlayTimedApi {

    @Override
    public long getPlaytimeTicks(@NotNull OfflinePlayer player) {
        if (player.isOnline() && player.getPlayer() != null) {
            return StatisticHandler.getPlayTime(player.getPlayer());
        }

        try {
            return player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        } catch (Exception ignored) {
            return 0L;
        }
    }

    @Override
    public @NotNull String formatPlaytime(long ticks) {
        long seconds = Math.max(0, ticks) / 20L;
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
        }
        return secs + "s";
    }
}
