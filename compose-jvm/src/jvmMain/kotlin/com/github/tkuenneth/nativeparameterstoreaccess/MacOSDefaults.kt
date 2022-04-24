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
 * Provides access to the macOS Defaults database.
 *
 * @author Thomas Kuenneth
 */
object MacOSDefaults {
    /**
     * Gets an entry from the Defaults database.
     *
     * @param key the key, for example `AppleInterfaceStyle`
     * @return the result or an empty string
     */
    fun getDefaultsEntry(key: String?): String {
        val stderr = StringBuilder()
        return getDefaultsEntry(key, stderr)
    }

    /**
     * Gets an entry from the Defaults database.
     *
     * @param key the key, for example `AppleInterfaceStyle`
     * @param stderr may contain error messages
     * @return the result or an empty string
     */
    fun getDefaultsEntry(
        key: String?,
        stderr: StringBuilder?
    ): String {
        var result = ""
        if (NativeParameterStoreAccess.IS_MACOS) {
            val stdin = StringBuilder()
            val cmd = String.format("defaults read -g %s", key)
            if (NativeParameterStoreAccess.execute(stdin, stderr!!, cmd)) {
                result = stdin.toString().trim { it <= ' ' }
            }
        }
        return result
    }
}