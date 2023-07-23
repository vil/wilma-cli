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