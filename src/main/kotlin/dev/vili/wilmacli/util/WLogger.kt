package dev.vili.wilmacli.util

class WLogger {
    private val reset = "\u001B[0m"
    private val red = "\u001B[31m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"

    fun log(message: String) {
        println("$blue$message$reset")
    }

    fun logError(message: String) {
        println("$red[ERROR] $message$reset")
    }

    fun logWarning(message: String) {
        println("$yellow[WARNING] $message$reset")
    }

    fun logInfo(message: String) {
        println("$blue[INFO] $message$reset")
    }
}

