/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command

import dev.vili.wilmacli.command.commands.*

class CommandManager {
    private val commands = linkedSetOf<Command>()

    /**
     * Initializes the command manager by adding all the commands.
     */
    fun init() {
        val allCommands = listOf(
            HelpCommand(),
            QuitCommand(),
            ServerCommand(),
            TimetableCommand(),
            ExamCommand(),
            InfoCommand(),
            ScheduleCommand(),
            CoursesCommand()
        )

        commands.addAll(allCommands)
    }

    /**
     * Runs a command with the given arguments.
     *
     * @param cmdInput The command to run
     * @param args The arguments to pass to the command
     * @return True if the command was invalid, false if it was valid
     */
    fun runCommand(cmdInput: String, args: Array<String>): Boolean {
        val command = getCommand(cmdInput, ignoreCase = true, aliases = true) ?: return true

        if (!command.exec(args)) {
            println(command.getHelp())
        }
        return false
    }

    /**
     * Gets a command by name.
     *
     * @param name The name of the command
     * @param ignoreCase Whether to ignore case when comparing the name
     * @param aliases Whether to check the aliases of the command
     * @return The command if found, null if not
     * @see Command
     */
    fun getCommand(name: String, ignoreCase: Boolean, aliases: Boolean): Command? {
        return commands.find { command ->
            command.getName().equals(name, ignoreCase) ||
                    (aliases && command.getAliases().any { it.equals(name, ignoreCase) })
        }
    }

    /**
     * Gets all the commands.
     *
     * @return A list of all the commands
     * @see Command
     */
    fun getCommands(): List<Command> {
        return commands.toList()
    }
}
