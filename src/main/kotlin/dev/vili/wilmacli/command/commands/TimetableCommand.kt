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
import org.openwilma.kotlin.enums.LessonNoteRange
import org.openwilma.kotlin.utils.LocalDateGSONAdapter
import org.openwilma.kotlin.utils.LocalDateTimeGSONAdapter
import org.openwilma.kotlin.utils.LocalTimeGSONAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimetableCommand : Command("Timetable", arrayOf()) {
    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .registerTypeAdapter(LocalDate::class.java, LocalDateGSONAdapter())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeGSONAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeGSONAdapter())
        .create()

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().error("You need to be logged in to use this command.")
            return false
        }

        if (args.isEmpty()) return false

        return when (args[0].lowercase(Locale.getDefault())) {
            "default" -> {
                runBlocking {
                    val notes = WilmaCLI.client.lessonNotes(LessonNoteRange.DEFAULT)
                    WilmaCLI.getLogger().log(gson.toJson(notes))
                }
                true
            }
            "custom" -> {
                if (args.size < 3) return false

                val (startDate, endDate) = parseDates(args[1], args[2]) ?: return false
                runBlocking {
                    val notes = WilmaCLI.client.lessonNotes(LessonNoteRange.CUSTOM, start = startDate, end = endDate)
                    notes.forEach { note ->
                        WilmaCLI.getLogger().log("Course: ${note.courseName} (${note.courseCode}) by ${note.authorName} (${note.authorCodeName})")
                    }
                }
                true
            }
            "year" -> {
                // Handle year case here
                true
            }
            else -> false
        }
    }

    private fun parseDates(vararg dateStrings: String): Pair<LocalDate, LocalDate>? {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return try {
            val startDate = LocalDate.parse(dateStrings[0], formatter)
            val endDate = LocalDate.parse(dateStrings[1], formatter)
            startDate to endDate
        } catch (e: Exception) {
            null
        }
    }

    override fun needsLogin(): Boolean = true
    override fun getHelp(): String = """
        Returns the timetable
            
        <timetable> [default|year]
        <timetable> [custom] <dd/MM/yyyy> <dd/MM/yyyy>       
    """.trimIndent()
}
