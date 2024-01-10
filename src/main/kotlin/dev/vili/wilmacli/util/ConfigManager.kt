/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.wilmacli.util

import com.google.gson.Gson
import java.io.File

class ConfigManager {
    private val configFile = File("config/config.json")
    private val gson = Gson()

    /**
     * Initializes the config manager by creating the config file if it doesn't exist.
     *
     * @see configFile
     */
    fun init() {
        if (!configExists()) {
            configFile.parentFile.mkdirs()
            configFile.createNewFile()
            configFile.writeText("{}")  // Write an empty JSON object to the config file
        }
    }


    /**
     * Gets a config value by key.
     *
     * @param key The key of the config value
     * @return The config value
     * @see configFile
     */
    fun getConfig(key: String): Any? {
        val config = gson.fromJson(configFile.readText(), Map::class.java)
        return config[key]
    }

    /**
     * Sets a config value by key.
     *
     * @param key The key of the config value
     * @param value The value to set
     * @see configFile
     */
    fun setConfig(key: String, value: Any) {
        val config = gson.fromJson(configFile.readText(), Map::class.java).toMutableMap()
        config[key] = value
        configFile.writeText(gson.toJson(config))
    }

    /**
     * Deletes the config file.
     *
     * @see configFile
     */
    fun deleteConfig() {
        configFile.delete()
    }

    /**
     * Checks if the config file exists.
     *
     * @return Does the config file exist
     * @see configFile
     */
    fun configExists(): Boolean {
        return configFile.exists()
    }

    /**
     * Gets the config file.
     *
     * @return The config file
     * @see configFile
     */
    fun getConfigFile(): File {
        return configFile
    }
}