/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI

class QuitCommand : Command("Quit", arrayOf("q", "exit")) {

    override fun exec(args: Array<String>): Boolean {
        WilmaCLI.terminate = true

        if (WilmaCLI.isQuitting())
            WilmaCLI.quit()
        return true
    }

    override fun needsLogin(): Boolean {
        return false
    }

    override fun getHelp(): String = "Quits the program."
}