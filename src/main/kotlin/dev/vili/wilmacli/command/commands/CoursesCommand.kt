/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command.commands

import dev.vili.wilmacli.WilmaCLI
import dev.vili.wilmacli.command.Command
import kotlinx.coroutines.runBlocking
import org.openwilma.kotlin.classes.courses.WilmaCourse
import org.openwilma.kotlin.classes.responses.WilmaAPIResponse
import org.openwilma.kotlin.enums.CourseTimeRange
import java.util.*

class CoursesCommand : Command("Courses", arrayOf("course", "c")) {

    override fun exec(args: Array<String>): Boolean {
        if (needsLogin() && !WilmaCLI.isLoggedIn()) {
            WilmaCLI.getLogger().error("You need to be logged in to use this command.")
            return false
        }

        if (args.isNotEmpty()) {
            val timeRange = when (args[0].lowercase(Locale.getDefault())) {
                "current" -> CourseTimeRange.CURRENT
                "past" -> CourseTimeRange.PAST
                else -> {
                    WilmaCLI.getLogger().error("Invalid arguments. Available options: 'current', 'past'.")
                    return false
                }
            }
            return try {
                runBlocking {
                    val courses = WilmaCLI.client.courses(timeRange, false)
                    displayCourses(courses)
                }
                true
            } catch (e: Exception) {
                WilmaCLI.getLogger().error("Failed to fetch the courses: ${e.message}")
                false
            }
        } else {
            WilmaCLI.getLogger().error("Invalid arguments. The 'courses' command requires 'current' or 'past' as arguments.")
            return false
        }
    }

    private fun displayCourses(payload: WilmaAPIResponse<List<WilmaCourse>>) {
        val courses = payload.payload ?: return
        // Example: Loop through the courses and print each course's information (name, teacher, etc.)
        if (courses.isEmpty()) {
            WilmaCLI.getLogger().log("No courses found.")
            return
        }
        for (course in courses) {
            WilmaCLI.getLogger().log("Course: ${course.name} (${course.id})")
            WilmaCLI.getLogger().log("Description: ${course.additionalInfo?.description ?: "No description."}")
            WilmaCLI.getLogger().log("Teacher(s): ${course.teachers}")
            WilmaCLI.getLogger().log("Start and end date: ${course.startDate} - ${course.endDate} \n\n")
        }
    }

    override fun needsLogin(): Boolean = true

    override fun getHelp(): String = """
        Prints your courses.
        
        <courses|course> [current|past]
    """.trimIndent()
}
