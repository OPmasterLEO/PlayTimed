# PlayTimed

Simple Paper/Folia plugin that exposes player playtime from Minecraft statistics.

## What it provides

- PlaceholderAPI placeholder:
  - `%playtime_timed%`
  - `%playtime_playtime%`
- Public Java API for other plugins (for example, leaderboard plugins) to read playtime values.

Playtime is read from Minecraft's built-in statistic (`PLAY_TIME` / `PLAY_ONE_MINUTE`) and returned as ticks or seconds.

---

## Using the API from another plugin

### 1) Add dependency

Add PlayTimed as a `compileOnly` dependency in your plugin.

If you build from local jars, place the PlayTimed jar in your server and development dependencies as you normally do.

### 2) Get API instance

PlayTimed registers its API in Bukkit `ServicesManager`.

```java
import net.opmasterleo.playtimed.api.PlayTimedApi;

PlayTimedApi api = PlayTimedApi.get();
if (api == null) {
    // PlayTimed is not installed or not enabled yet
    return;
}
```

You can also resolve manually:

```java
import net.opmasterleo.playtimed.api.PlayTimedApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

RegisteredServiceProvider<PlayTimedApi> provider =
        Bukkit.getServicesManager().getRegistration(PlayTimedApi.class);
PlayTimedApi api = provider == null ? null : provider.getProvider();
```

### 3) Read playtime values

```java
import java.util.UUID;
import org.bukkit.OfflinePlayer;

OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
UUID playerId = offlinePlayer.getUniqueId();

long ticksFromPlayer = api.getPlaytimeTicks(offlinePlayer);
long ticksFromUuid = api.getPlaytimeTicks(playerId);

long secondsFromPlayer = api.getPlaytimeSeconds(offlinePlayer);
long secondsFromUuid = api.getPlaytimeSeconds(playerId);
```

### 4) Format playtime

```java
String formattedA = api.formatPlaytime(ticksFromPlayer);  // e.g. "2h 15m"
String formattedB = api.getFormattedPlaytime(offlinePlayer);
```

Current output format:

- `Xd Yh` for 1+ days
- `Xh Ym` for 1+ hours
- `Xm Ys` for 1+ minutes
- `Xs` for under 1 minute

---

## API reference

`net.opmasterleo.playtimed.api.PlayTimedApi`

- `static PlayTimedApi get()`
- `long getPlaytimeTicks(OfflinePlayer player)`
- `long getPlaytimeTicks(UUID playerId)`
- `long getPlaytimeSeconds(OfflinePlayer player)`
- `long getPlaytimeSeconds(UUID playerId)`
- `String formatPlaytime(long ticks)`
- `String getFormattedPlaytime(OfflinePlayer player)`

---

## PlaceholderAPI

If PlaceholderAPI is installed, PlayTimed auto-registers placeholders:

- `%playtime_timed%`
- `%playtime_playtime%`

Both return the same formatted playtime string.
