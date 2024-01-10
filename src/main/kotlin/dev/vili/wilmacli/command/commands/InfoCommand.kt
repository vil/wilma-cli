/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.WilmaCLI
import dev.vili.wilmacli.command.Command
import kotlinx.coroutines.runBlocking

class InfoCommand : Command("Info", arrayOf("i")) {

    override fun exec(args: Array<String>): Boolean {
        // Print info about the program and logged in state
        WilmaCLI.getLogger().log("WilmaCLI v${WilmaCLI.getVersion()} By ${WilmaCLI.getAuthor()} - ${WilmaCLI.server.serverURL}")
        if (WilmaCLI.isLoggedIn()) {
            runBlocking {
                WilmaCLI.getLogger().log("You are logged in as ${WilmaCLI.client.account().payload?.firstName} " +
                        "${WilmaCLI.client.account().payload?.lastName} (${WilmaCLI.client.account().payload?.username}) [${WilmaCLI.client.account().payload?.primusId}]")
                WilmaCLI.getLogger().log("User's last login ${WilmaCLI.client.account().payload?.lastLogin}")
            }
        } else {
            WilmaCLI.getLogger().log("You are not logged in.")
        }
        return true
    }

    override fun getHelp(): String = "Prints info about the program and if you are logged in."
}