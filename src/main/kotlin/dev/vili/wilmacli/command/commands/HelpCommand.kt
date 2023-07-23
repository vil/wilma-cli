/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI

class HelpCommand : Command("Help", arrayOf("h")) {

    override fun exec(args: Array<String>): Boolean {
        if (args.isEmpty()) {
            val commandNames = WilmaCLI.getCommandManager().getCommands().joinToString("\n") { it.getName() }
            WilmaCLI.getLogger().log("Available commands:\n$commandNames")
            return true
        }

        val command = WilmaCLI.getCommandManager().getCommand(args[0], true, true) ?: return false
        WilmaCLI.getLogger().log(command.getHelp())

        return true
    }

    override fun needsLogin(): Boolean = false

    override fun getHelp(): String = """
        Prints help about the program or a specific command.
        
        <help|h> [command]
        
        This message also shows up when you run a command wrong.
    """.trimIndent()
}
