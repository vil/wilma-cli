/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import kotlinx.coroutines.runBlocking
import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI
import java.util.*

class ServerCommand : Command("Server", arrayOf("login", "l")) {

    override fun exec(args: Array<String>): Boolean {
        when (args.getOrNull(0)?.lowercase(Locale.getDefault())) {
            "set" -> {
                WilmaCLI.getLogger().log("Enter the server URL.")
                val serverURL = readln()
                WilmaCLI.getConfigManager().setConfig("server", serverURL)
                WilmaCLI.server.serverURL = serverURL
                WilmaCLI.getLogger().log("Set server URL to $serverURL.")
                return true
            }
            "connect" -> {
                WilmaCLI.getLogger().log("Set server URL to ${WilmaCLI.server.serverURL}.")
                WilmaCLI.getLogger().log("Please enter your username ->")
                val username = readln()

                WilmaCLI.getLogger().log("Please enter your password ->")
                val password = readln()

                WilmaCLI.getLogger().log("Logging in...")
                try {
                    runBlocking {
                        WilmaCLI.client.signInToWilma(WilmaCLI.server, username, password)
                        WilmaCLI.getLogger().logDebug(WilmaCLI.client.roles().payload.toString())
                        WilmaCLI.loggedIn = true
                    }
                    WilmaCLI.getLogger().log("Logged in successfully as $username.")
                } catch (e: Exception) {
                    WilmaCLI.getLogger().logError("Login failed: ${e.message}")
                }
                return true
            }
            "disconnect" -> {
                WilmaCLI.getLogger().log("Logging out...")
                // TODO("Not yet implemented")
                return true
            }
            "status" -> {
                WilmaCLI.getLogger().log("You are currently ${if (WilmaCLI.isLoggedIn()) "logged in" else "not logged in"} to ${WilmaCLI.server.serverURL}.")
                return true
            }
            else -> return false
        }
    }

    override fun getHelp(): String = """
        Connects to a Wilma server.
        
        <server|s> [set|connect|disconnect|status]
        
        This command is used to connect to a Wilma server. You can also disconnect from the server or check the connection status.
    """.trimIndent()
}
