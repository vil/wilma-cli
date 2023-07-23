package dev.vili.wilmacli

import dev.vili.wilmacli.command.CommandManager
import dev.vili.wilmacli.util.WLogger
import org.openwilma.kotlin.OpenWilma
import org.openwilma.kotlin.classes.WilmaServer
import kotlin.system.exitProcess

class WilmaCLI {
    companion object {
        private val LOGGER = WLogger()
        private const val VERSION = "0.1.0a"
        private val wilmaClient = OpenWilma()
        private val wilmaServer = WilmaServer("") // TODO ("Make this configurable via a config file.")
        private val commandManager = CommandManager()
        var running = false
        var loggedIn = false

        @JvmStatic
        fun main(args: Array<String>) {
            commandManager.init()
            try {
                LOGGER.log("Welcome to WilmaCLI v${VERSION}!")
                LOGGER.log("Type 'help' for help.")
                while (!running) {
                    printPrompt()
                    val input = readln()
                    val splitInput = input.split(" ")

                    if (splitInput[0].isNotEmpty() && commandManager.runCommand(splitInput[0], splitInput.drop(1).toTypedArray())) {
                        LOGGER.logError("Invalid command. Type 'help' for help.")
                    }
                }
            } catch (e: RuntimeException) {
                LOGGER.logError("An error occurred: ${e.message}")
                return
            }
        }

        private fun printPrompt() {
            LOGGER.log("~ $ ->")
        }

        val client: OpenWilma
            get() = wilmaClient

        val server: WilmaServer
            get() = wilmaServer

        /**
         * Returns the command manager.
         *
         * @return The command manager.
         */
        fun getCommandManager(): CommandManager {
            return commandManager
        }

        /**
         * Returns the logger.
         *
         * @return The logger.
         */
        fun getLogger(): WLogger {
            return LOGGER
        }

        /**
         * Returns the version of WilmaCLI.
         *
         * @return The version of WilmaCLI.
         */
        fun getVersion(): String {
            return VERSION
        }

        /**
         * Returns if the user is logged in or not.
         *
         * @return status of the user's login.
         */
        fun isLoggedIn(): Boolean {
            return loggedIn
        }

        /**
         * Returns whether the user is logged in or not.
         *
         * @return Whether the user is logged in or not.
         */
        fun isQuitting(): Boolean {
            return running
        }

        /**
         * Quits the application.
         */
        fun quit() {
            if (running) {
                LOGGER.logWarning("Quitting...")
                exitProcess(0)
            }
        }
    }
}
