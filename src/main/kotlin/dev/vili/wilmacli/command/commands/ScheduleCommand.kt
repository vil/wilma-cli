/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI
import kotlinx.coroutines.runBlocking

class ScheduleCommand : Command("Schedule", arrayOf("schedule")) {

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().logError("You need to be logged in to use this command.")
            return false
        }

        if (args.isNotEmpty()) {
            WilmaCLI.getLogger().logError("Invalid arguments. The 'schedule' command doesn't take any arguments.")
            return false
        }

        val client = WilmaCLI.client
        return try {
            runBlocking {
                val schedule = client.schedule().days
                // TODO: ("Display the schedule in a formatted manner (e.g., date, time, subject, etc.")
                WilmaCLI.getLogger().log("Your schedule: $schedule")
            }
            true
        } catch (e: Exception) {
            WilmaCLI.getLogger().logError("Failed to fetch the schedule: ${e.message}")
            false
        }
    }

    override fun needsLogin(): Boolean = true

    override fun getHelp(): String = """
        Prints your schedule.
        
        <schedule>
    """.trimIndent()
}
