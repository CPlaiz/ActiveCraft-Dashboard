package de.cplaiz.activecraftdashboard.utils

import de.cplaiz.activecraftcore.playermanagement.Profile
import de.cplaiz.activecraftcore.utils.config.Effect
import de.cplaiz.activecraftcore.utils.config.Warn
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.potion.PotionEffectType

fun Profile.toJson() = """
{
  "uuid": "$uuid",
  "name": "$name",
  "nickname": "$nickname",
  "lastOnline": "$lastOnline",
  "colornick": "$colorNick",
  "flyspeed": $flyspeed,
  "warns": $warns,
  "bans": $bans,
  "mutes": $mutes,
  "ipBans": $ipBans,
  "times_joined": $times_joined,
  "afk": $isAfk,
  "godmode": $isGodmode,
  "isMuted": $isMuted,
  "isForcemuted": $isForcemuted,
  "isDefaultmuted": $isDefaultmuted,
  "vanished": $isVanished,
  "receiveLogs": ${canReceiveLog()},
  "bypassLockdown": ${canBypassLockdown()},
  "editSigns": $isEditSign,
  "tags": $tags,
  "prefix": "$prefix",
  "receiveSocialspy": ${canReceiveSocialspy()},
  "playtimeMinutes": $playtimeMinutes,
  "playtimeHours": $playtimeHours,
  "lastLocations": ${worldLocMapToJson(lastLocations)},
  "homeList": ${strLocMapToJson(homeList)},
  "warnList": ${warnlistToJson(warnList)},
  "effects": ${effectMapToJson(effects)}
}
"""

fun effectMapToJson(effectMap: HashMap<PotionEffectType, Effect>): String {
    val json = StringBuilder()
    json.append("{")
    effectMap.entries.forEachIndexed { index, entry ->
        run {
            json.append("\"${entry.key.name}\":")
            json.append("{\"amplifier\":${entry.value.amplifier},")
            json.append("\"active\":${entry.value.active}}")
            if (index != effectMap.size-1) json.append(",")
        }
    };
    json.append("}")
    return json.toString()
}

fun worldLocMapToJson(locMap: Map<World, Location>): String {
    return strLocMapToJson(locMap.mapKeys { it.key.name })
}

fun strLocMapToJson(locMap: Map<String, Location>): String {
    val json = StringBuilder()
    json.append("{")
    val filteredLocMap = locMap.filterValues { it != null }
    filteredLocMap.entries.forEachIndexed { index, entry ->
        run {
            json.append("\"${entry.key}\":")
            json.append("{\"x\":${entry.value.x},")
            json.append("\"y\":${entry.value.y},")
            json.append("\"z\":${entry.value.z}}")
            if (index != filteredLocMap.size-1) json.append(",")
        }
    };
    json.append("}")
    return json.toString()
}

fun warnlistToJson(warnList: HashMap<String, Warn>): String {
    val json = StringBuilder()
    json.append("{")
    warnList.entries.forEachIndexed { index, entry ->
        run {
            json.append("\"${entry.key}\":")
            json.append("{\"reason\":\"${entry.value.reason}\",")
            json.append("\"created\":\"${entry.value.created}\",")
            json.append("\"source\":\"${entry.value.source}\"}")
            if (index != warnList.size-1) json.append(",")
        }
    };
    json.append("}")
    return json.toString()
}