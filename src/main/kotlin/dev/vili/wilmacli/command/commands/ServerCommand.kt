package dev.vili.wilmacli.command.commands

import kotlinx.coroutines.runBlocking
import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI
import java.util.*

class ServerCommand : Command("Server", arrayOf("login", "l")) {

    override fun exec(args: Array<String>): Boolean {
        when (args.getOrNull(0)?.lowercase(Locale.getDefault())) {
            "connect" -> {
                WilmaCLI.getLogger().log("Set server URL to ${WilmaCLI.server.serverURL}.")
                WilmaCLI.getLogger().log("Please enter your username.")
                val username = readln()

                WilmaCLI.getLogger().log("Now enter your password.")
                val password = readln()

                WilmaCLI.getLogger().log("Logging in...")
                try {
                    runBlocking {
                        WilmaCLI.client.signInToWilma(WilmaCLI.server, username, password)
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
        
        <server|s> [connect|disconnect|status]
        
        This command is used to connect to a Wilma server. You can also disconnect from the server or check the connection status.
    """.trimIndent()
}