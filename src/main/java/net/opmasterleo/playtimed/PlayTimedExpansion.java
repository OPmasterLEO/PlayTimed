package net.opmasterleo.playtimed;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.opmasterleo.playtimed.api.PlayTimedApi;

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
        if (player == null) {
            return "0";
        }

        if (params.equalsIgnoreCase("timed") || params.equalsIgnoreCase("playtime")) {
            PlayTimedApi api = plugin.getApi();
            return api.formatPlaytime(api.getPlaytimeTicks(player));
        }

        return null;
    }
}