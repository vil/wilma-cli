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

class MessagesCommand : Command("Messages", arrayOf("message", "messages", "msg")) {


    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().error("You need to be logged in to use this command.")
            return false
        }

        if (args.isNotEmpty()) {
            WilmaCLI.getLogger().error("Invalid arguments. The 'messages' command doesn't take any arguments.")
            return false
        }

        val client = WilmaCLI.client

        return try {
             TODO("Not yet implemented.")
        } catch (e: Exception) {
            WilmaCLI.getLogger().error("Something went wrong: ${e.message}")
            return false
        }
    }

    override fun needsLogin(): Boolean {
        return WilmaCLI.loggedIn;
    }

    override fun getHelp(): String = """
        Prints the recent messages.
        
        <message>
    """.trimIndent()

}