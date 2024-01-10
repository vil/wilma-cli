/*
 * Copyright (c) 2024. Vili and contributors.
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
            WilmaCLI.getLogger().error("You need to be logged in to use this command.")
            return false
        }

        if (args.isNotEmpty()) {
            WilmaCLI.getLogger().error("Invalid arguments. The 'schedule' command doesn't take any arguments.")
            return false
        }

        val client = WilmaCLI.client
        return try {
            val schedule = runBlocking { client.schedule() }
            val days = schedule.days
            val terms = schedule.terms

            for (day in days) {
                for (term in terms) {
                    WilmaCLI.getLogger().log("Day: $day (${day.day})")
                    WilmaCLI.getLogger().log("Term: $term (${term.name})")
                    WilmaCLI.getLogger().log("Date: ${term.start} - ${term.end}")
                }
            }
            true
        } catch (e: Exception) {
            WilmaCLI.getLogger().error("Failed to fetch the schedule: ${e.message}")
            false
        }
    }

    override fun needsLogin(): Boolean = true

    override fun getHelp(): String = """
        Prints your schedule.
        
        <schedule>
    """.trimIndent()
}
