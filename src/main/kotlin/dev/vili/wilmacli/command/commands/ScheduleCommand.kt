package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI
import kotlinx.coroutines.runBlocking

class ScheduleCommand : Command("Schedule", arrayOf("schedule", "s")) {

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
        
        <schedule|s>
    """.trimIndent()
}