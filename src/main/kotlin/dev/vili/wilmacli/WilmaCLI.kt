/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli

import dev.vili.wilmacli.command.CommandManager
import dev.vili.wilmacli.util.ConfigManager
import dev.vili.wilmacli.util.WLogger
import org.openwilma.kotlin.OpenWilma
import org.openwilma.kotlin.classes.WilmaServer
import kotlin.system.exitProcess

class WilmaCLI {
    companion object {
        private val LOGGER = WLogger()
        private const val VERSION = "0.1.1a"
        private val wilmaClient = OpenWilma()
        private lateinit var wilmaServer: WilmaServer
        private val commandManager = CommandManager()
        private val configManager = ConfigManager()
        var terminate = false
        var loggedIn = false
        var debugOn = false

        @JvmStatic
        fun main(args: Array<String>) {
            commandManager.init()
            configManager.init()
            initChecks()

            LOGGER.logDebug("Config and command managers initialized.")
            try {
                LOGGER.log("Welcome to WilmaCLI v${VERSION}!")
                LOGGER.log("Type 'help' for help.")
                while (!terminate) {
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
         * Returns the config manager.
         *
         * @return The config manager.
         */
        fun getConfigManager(): ConfigManager {
            return configManager
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
         * If the user is quitting the application.
         *
         * @return is the user quitting the application.
         */
        fun isQuitting(): Boolean {
            return terminate
        }

        /**
         * Quits the application.
         */
        fun quit() {
            if (isQuitting()) {
                LOGGER.logWarning("Quitting...")
                exitProcess(0)
            }
        }

        /**
         * Performs checks.
         */
        private fun initChecks() {
            val server = configManager.getConfig("server")
            LOGGER.logDebug("Server: $server")
            wilmaServer = if (server != null) {
                WilmaServer(server as String)
            } else {
                LOGGER.logWarning("No server set. Using default server.")
                WilmaServer("https://espoondemo.inschool.fi")
            }
            val debug = configManager.getConfig("debug").toString().toBoolean()
            debugOn = debug
        }
    }
}
