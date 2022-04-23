package com.baseio.kmm.resources

actual fun resourceLoader(resName: String): ByteArray {
    val langStream = ClassLoader.getSystemResourceAsStream(resName)
    return langStream?.readBytes() ?: byteArrayOf()
}