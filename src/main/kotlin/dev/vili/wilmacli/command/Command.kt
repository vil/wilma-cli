/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.command

abstract class Command(name: String, aliases: Array<String>) {
    private val name: String
    private val aliases: Array<String>

    /**
     * Checks if the command needs the user to be logged in
     *
     * @return does the command need the user to be logged in
     */
    open fun needsLogin(): Boolean {
        return true
    }

    init {
        this.name = name
        this.aliases = aliases
    }

    /**
     * Executes the command
     *
     * @return success
     */
    abstract fun exec(args: Array<String>): Boolean

    /**
     * Get help about the command (e.g., what it does, what arguments it takes, etc.)
     *
     * @return help
     */
    abstract fun getHelp(): String

    /**
     * Get the name of the command
     *
     * @return name
     */
    fun getName(): String {
        return name
    }

    /**
     * Get the aliases of the command
     *
     * @return aliases
     */
    fun getAliases(): Array<String> {
        return aliases
    }
}