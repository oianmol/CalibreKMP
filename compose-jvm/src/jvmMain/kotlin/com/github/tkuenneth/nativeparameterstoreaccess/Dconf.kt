/*
 * MIT License
 *
 * Copyright (c) 2020 Thomas Kuenneth
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.tkuenneth.nativeparameterstoreaccess

/**
 * Provides access to dconf.
 *
 * @author Thomas Kuenneth
 */
object Dconf {
    /**
     * Is `true` if the operating system is Linux and dconf is
     * present, otherwise `false`
     */
    val HAS_DCONF = hasDconf()

    /**
     * Gets an entry from dconf.
     *
     * @param key the key, for example
     * `/org/gnome/desktop/interface/gtk-theme`
     * @return the result or an empty string
     */
    fun getDconfEntry(key: String?): String {
        val stderr = StringBuilder()
        return getDconfEntry(key, stderr)
    }

    /**
     * Gets an entry from dconf.
     *
     * @param key the key, for example
     * `/org/gnome/desktop/interface/gtk-theme`
     * @param stderr may contain error messages
     * @return the result or an empty string
     */
    fun getDconfEntry(
        key: String?,
        stderr: StringBuilder?
    ): String {
        var result = ""
        if (NativeParameterStoreAccess.IS_LINUX) {
            val stdin = StringBuilder()
            val cmd = String.format("dconf read %s", key)
            if (NativeParameterStoreAccess.execute(stdin, stderr!!, cmd)) {
                result = stdin.toString().trim { it <= ' ' }
            }
        }
        return result
    }

    private fun hasDconf(): Boolean {
        var result = false
        if (NativeParameterStoreAccess.IS_LINUX) {
            val stdin = StringBuilder()
            val stderr = StringBuilder()
            val cmd = "dconf list /"
            if (NativeParameterStoreAccess.execute(stdin, stderr, cmd)) {
                result = stdin.length > 0 && stderr.length == 0
            }
        }
        return result
    }
}