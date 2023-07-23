/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import dev.vili.wilmacli.command.Command
import dev.vili.wilmacli.WilmaCLI
import org.openwilma.kotlin.utils.LocalDateGSONAdapter
import org.openwilma.kotlin.utils.LocalDateTimeGSONAdapter
import org.openwilma.kotlin.utils.LocalTimeGSONAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ExamCommand : Command("Exam", arrayOf("exams", "e")) {
    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .registerTypeAdapter(LocalDate::class.java, LocalDateGSONAdapter())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeGSONAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeGSONAdapter())
        .create()

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().logError("You need to be logged in to use this command.")
            return false
        }

        return try {
            when (args.getOrNull(0)?.toLowerCase()) {
                "past" -> {
                    val exams = runBlocking { WilmaCLI.client.pastExams() }
                    WilmaCLI.getLogger().log(gson.toJson(exams))
                    true
                }
                "upcoming" -> {
                    val exams = runBlocking { WilmaCLI.client.upcomingExams() }
                    WilmaCLI.getLogger().log(gson.toJson(exams))
                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            WilmaCLI.getLogger().logError(e.message.toString())
            false
        }
    }

    override fun needsLogin(): Boolean = true
    override fun getHelp(): String = """
        Returns upcoming/past exams 
    
        <exam> [upcoming/past]
    """.trimIndent()
}
