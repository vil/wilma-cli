package dev.vili.wilmacli.command

abstract class Command(name: String, aliases: Array<String>) {
    private val name: String
    private val aliases: Array<String>

    /**
     * Commands default to needing the client to be logged in first
     */
    open fun needsLogin(): Boolean {
        return true
    }

    init {
        this.name = name
        this.aliases = aliases
    }

    /**
     * @return did the command finish successfully
     */
    abstract fun exec(args: Array<String>): Boolean

    /**
     * @return help
     */
    abstract fun getHelp(): String

    fun getName(): String {
        return name
    }

    fun getAliases(): Array<String> {
        return aliases
    }
}