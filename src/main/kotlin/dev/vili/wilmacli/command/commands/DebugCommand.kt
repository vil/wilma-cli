/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.WilmaCLI
import dev.vili.wilmacli.command.Command

class DebugCommand : Command("Debug", arrayOf("debug")) {

    override fun exec(args: Array<String>): Boolean {
        if (args.isEmpty()) {
            println("Debug mode is ${if (WilmaCLI.debugOn) "on" else "off"}.")
            return true
        }

        return when (args[0].lowercase()) {
            "on" -> {
                WilmaCLI.debugOn = true
                WilmaCLI.getLogger().logDebug("Debug mode is now on.")
                WilmaCLI.getConfigManager().setConfig("debug", "true")
                true
            }

            "off" -> {
                WilmaCLI.debugOn = false
                WilmaCLI.getLogger().logDebug("Debug mode is now off.")
                WilmaCLI.getConfigManager().setConfig("debug", "false")
                true
            }

            else -> false
        }
    }

    override fun getHelp(): String = """
        Debug command.
        
        <debug> [on|off]
    """.trimIndent()
}