package net.opmasterleo.playtimed.nms;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;

public class StatisticHandler {

    public static int getPlayTime(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        return nmsPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
    }

    public static void setPlayTime(Player player, int ticks) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.getStats().setValue(nmsPlayer, Stats.CUSTOM.get(Stats.PLAY_TIME), ticks);
    }

    public static void incrementPlayTime(Player player, int ticks) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.getStats().increment(nmsPlayer, Stats.CUSTOM.get(Stats.PLAY_TIME), ticks);
    }
}
