package net.opmasterleo.playtimed.api;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayTimedApi {

    /**
     * Resolve this API from Bukkit's services manager.
     *
     * @return registered API instance, or {@code null} when PlayTimed is unavailable
     */
    static @Nullable PlayTimedApi get() {
        var provider = Bukkit.getServicesManager().getRegistration(PlayTimedApi.class);
        return provider == null ? null : provider.getProvider();
    }

    /**
     * Gets a player's total playtime in ticks.
     *
     * @param player player to check
     * @return playtime in ticks
     */
    long getPlaytimeTicks(@NotNull OfflinePlayer player);

    /**
     * Gets a player's total playtime in ticks.
     *
     * @param playerId player UUID to check
     * @return playtime in ticks
     */
    default long getPlaytimeTicks(@NotNull UUID playerId) {
        return getPlaytimeTicks(Bukkit.getOfflinePlayer(playerId));
    }

    /**
     * Gets a player's total playtime in seconds.
     *
     * @param player player to check
     * @return playtime in seconds
     */
    default long getPlaytimeSeconds(@NotNull OfflinePlayer player) {
        return getPlaytimeTicks(player) / 20L;
    }

    /**
     * Gets a player's total playtime in seconds.
     *
     * @param playerId player UUID to check
     * @return playtime in seconds
     */
    default long getPlaytimeSeconds(@NotNull UUID playerId) {
        return getPlaytimeTicks(playerId) / 20L;
    }

    /**
     * Formats raw playtime ticks in a compact string.
     *
     * @param ticks playtime in ticks
     * @return formatted string like {@code 3h 12m}
     */
    @NotNull String formatPlaytime(long ticks);

    /**
     * Gets and formats a player's playtime.
     *
     * @param player player to check
     * @return formatted playtime
     */
    default @NotNull String getFormattedPlaytime(@NotNull OfflinePlayer player) {
        return formatPlaytime(getPlaytimeTicks(player));
    }
}
