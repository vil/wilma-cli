/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.command.Command

class AnnouncementsCommand : Command("Announcements", arrayOf("announcements")) {

    override fun exec(args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }


    override fun needsLogin(): Boolean = true

    override fun getHelp(): String = """
        Prints your announcements.
        
        <announcements>
    """.trimIndent()
}