/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.WilmaCLI
import dev.vili.wilmacli.command.Command
import kotlinx.coroutines.runBlocking
import org.openwilma.kotlin.classes.announcements.Announcement

class AnnouncementsCommand : Command("Announcements", arrayOf("announcements")) {

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().logError("You need to be logged in to use this command.")
            return false
        }

        return try {
            runBlocking {
                val announcements = WilmaCLI.client.announcements()
                displayAnnouncements(announcements)
            }
            true
        } catch (e: Exception) {
            WilmaCLI.getLogger().logError("Failed to fetch the announcements: ${e.message}")
            false
        }
    }

    private fun displayAnnouncements(announcements: List<Announcement>) {
        if (announcements.isEmpty()) {
            WilmaCLI.getLogger().log("No announcements currently.")
            return
        }

        for (announcement in announcements) {
            WilmaCLI.getLogger().log("Announcement: ${announcement.subject} (${announcement.id}) by ${announcement.authorName} " +
                    "(${announcement.authorId}) " + "[${announcement.authorType}]")
            WilmaCLI.getLogger().log("Description: ${announcement.description}")
            WilmaCLI.getLogger().log("Date: ${announcement.timestamp} \n\n")
        }
    }

    override fun needsLogin(): Boolean = true

    override fun getHelp(): String = """
        Prints your current announcements.
        
        <announcements>
    """.trimIndent()
}