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
