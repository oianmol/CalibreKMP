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
 * Provides access to the Windows Registry.
 *
 * @author Thomas Kuenneth
 */
object WindowsRegistry {
    /**
     * Gets an entry from the Windows registry.
     *
     * @param key the key, for example
     * `HKCU\Software\Microsoft\Windows\CurrentVersion\Themes\Personalize`
     * @param value the value, for example `AppsUseLightTheme`
     * @param type the type, for example `REG_DWORD`
     * @return the result or an empty string
     */
    fun getWindowsRegistryEntry(
        key: String?,
        value: String?,
        type: REG_TYPE
    ): String {
        val stderr = StringBuilder()
        return getWindowsRegistryEntry(key, value, type, stderr)
    }

    /**
     * Gets an entry from the Windows registry.
     *
     * @param key the key, for example
     * `HKCU\Software\Microsoft\Windows\CurrentVersion\Themes\Personalize`
     * @param value the value, for example `AppsUseLightTheme`
     * @param type the type, for example `REG_DWORD`
     * @param stderr may contain error messages
     * @return the result or an empty string
     */
    fun getWindowsRegistryEntry(
        key: String?,
        value: String?,
        type: REG_TYPE,
        stderr: StringBuilder?
    ): String {
        var result = ""
        if (NativeParameterStoreAccess.IS_WINDOWS) {
            val stdin = StringBuilder()
            val cmd = String.format("reg query \"%s\" /v %s", key, value)
            if (NativeParameterStoreAccess.execute(stdin, stderr!!, cmd)) {
                val temp = stdin.toString()
                val stringType = type.toString()
                val pos = temp.indexOf(stringType)
                if (pos >= 0) {
                    result = temp.substring(pos + stringType.length).trim { it <= ' ' }
                }
            }
        }
        return result
    }

    /**
     * Gets a `REG_DWORD` from the Windows registry.
     * `NumberFormatException` may be thrown if the result cannot be
     * obtained, or if it is not a number.
     *
     * @param key the key, for example
     * `HKCU\Software\Microsoft\Windows\CurrentVersion\Themes\Personalize`
     * @param value the value, for example `AppsUseLightTheme`
     * @return the result
     */
    fun getWindowsRegistryEntry(key: String?, value: String?): Int {
        val result = getWindowsRegistryEntry(key, value, REG_TYPE.REG_DWORD)
        return Integer.decode(result)
    }

    /**
     * Writes a `REG_DWORD` into the Windows registry.
     *
     * @param key the key, for example
     * `HKCU\Software\Microsoft\Windows\CurrentVersion\Themes\Personalize`
     * @param value the value, for example `AppsUseLightTheme`
     * @param data the data to be written
     * @return if successful `true`, otherwise `false`
     */
    fun setWindowsRegistryEntry(
        key: String?,
        value: String?,
        data: Int
    ): Boolean {
        val stderr = StringBuilder()
        return setWindowsRegistryEntry(
            key, value,
            Integer.toHexString(data),
            REG_TYPE.REG_DWORD, stderr
        )
    }

    /**
     * Writes a `REG_DWORD` into the Windows registry.
     *
     * @param key the key, for example
     * `HKCU\Software\Microsoft\Windows\CurrentVersion\Themes\Personalize`
     * @param value the value, for example `AppsUseLightTheme`
     * @param data the data to be written
     * @param type the type, for example `REG_DWORD`
     * @param stderr may contain error messages
     * @return if successful `true`, otherwise `false`
     */
    fun setWindowsRegistryEntry(
        key: String?,
        value: String?,
        data: String?,
        type: REG_TYPE,
        stderr: StringBuilder?
    ): Boolean {
        var result = false
        if (NativeParameterStoreAccess.IS_WINDOWS) {
            val stdin = StringBuilder()
            val cmd = String.format(
                "reg add \"%s\" /v %s /t %s /d %s /f",
                key, value, type.toString(), data
            )
            if (NativeParameterStoreAccess.execute(stdin, stderr!!, cmd)) {
                result = true
            }
        }
        return result
    }

    /**
     * Registry types
     */
    enum class REG_TYPE {
        REG_BINARY, REG_DWORD, REG_EXPAND_SZ, REG_MULTI_SZ, REG_SZ
    }
}