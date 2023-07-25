/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.util

import dev.vili.wilmacli.WilmaCLI

class WLogger {
    // ANSI color codes
    private val reset = "\u001B[0m"
    private val red = "\u001B[31m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val purple = "\u001B[35m"

    /**
     * Logs a message to the console.
     *
     * @param message The message to log.
     */
    fun log(message: String) {
        println("$blue$message$reset")
    }

    /**
     * Logs an error to the console.
     *
     * @param message The message to log.
     */
    fun logError(message: String) {
        println("$red[ERROR] $message$reset")
    }

    /**
     * Logs a warning to the console.
     *
     * @param message The message to log.
     */
    fun logWarning(message: String) {
        println("$yellow[WARNING] $message$reset")
    }

    /**
     * Logs an info message to the console.
     *
     * @param message The message to log.
     */
    fun logInfo(message: String) {
        println("$blue[INFO] $message$reset")
    }

    /**
     * Logs a debug message to the console.
     *
     * @param message The message to log.
     */
    fun logDebug(message: String) {
        if (WilmaCLI.debugOn) println("$purple[DEBUG] $message$reset")
    }
}

