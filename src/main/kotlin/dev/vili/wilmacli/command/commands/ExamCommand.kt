/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import kotlinx.coroutines.runBlocking
import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI

class ExamCommand : Command("Exam", arrayOf("exams", "e")) {

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().error("You need to be logged in to use this command.")
            return false
        }

        return try {
            when (args.getOrNull(0)?.lowercase()) {
                "past" -> {
                    val exams = runBlocking { WilmaCLI.client.pastExams() }

                    if (exams.isEmpty()) {
                        WilmaCLI.getLogger().log("No past exams.")
                    }
                    else {
                        for (i in exams) {
                            WilmaCLI.getLogger().log("Subject: ${i.subject}")
                            WilmaCLI.getLogger().log("Course: ${i.courseName} (${i.courseCode})")
                            WilmaCLI.getLogger().log("Grade: ${i.grade}")
                            WilmaCLI.getLogger().log("Date: ${i.timestamp.toString()}")
                            WilmaCLI.getLogger().log("Description: ${i.additionalInfo}")
                            WilmaCLI.getLogger().log("Teachers: ${i.teachers} \n\n")
                        }
                    }
                    true
                }
                "upcoming" -> {
                    val exams = runBlocking { WilmaCLI.client.upcomingExams() }

                    if (exams.isEmpty()) {
                        WilmaCLI.getLogger().log("No upcoming exams.")
                    }
                    else {
                        for (i in exams) {
                            WilmaCLI.getLogger().log("Subject: ${i.subject}")
                            WilmaCLI.getLogger().log("Course: ${i.courseName} (${i.courseCode})")
                            WilmaCLI.getLogger().log("Date: ${i.timestamp.toString()}")
                            WilmaCLI.getLogger().log("Description: ${i.additionalInfo}")
                            WilmaCLI.getLogger().log("Teachers: ${i.teachers} \n\n")
                        }
                    }
                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            WilmaCLI.getLogger().error(e.message.toString())
            false
        }
    }

    override fun needsLogin(): Boolean = true
    override fun getHelp(): String = """
        Returns upcoming/past exams 
    
        <exam/exams> [upcoming/past]
    """.trimIndent()
}
