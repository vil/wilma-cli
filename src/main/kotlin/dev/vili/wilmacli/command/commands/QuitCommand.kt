package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI

class QuitCommand : Command("Quit", arrayOf("q", "exit")) {

    override fun exec(args: Array<String>): Boolean {
        WilmaCLI.running = true

        if (WilmaCLI.isQuitting())
            WilmaCLI.quit()
        return true
    }

    override fun needsLogin(): Boolean {
        return false
    }

    override fun getHelp(): String = "Quits the program."
}