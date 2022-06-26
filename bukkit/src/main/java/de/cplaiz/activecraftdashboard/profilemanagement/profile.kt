package de.cplaiz.activecraftdashboard.profilemanagement

import de.cplaiz.activecraftcore.playermanagement.Profile

fun Profile.setOp(op: Boolean) {
    offlinePlayer.isOp = op
}

