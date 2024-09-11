package common.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

fun Serializable.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    ObjectOutputStream(byteArrayOutputStream).use { it.writeObject(this) }
    return byteArrayOutputStream.toByteArray()
}

@Suppress("UNCHECKED_CAST")
fun <T> ByteArray.toSerializable(): T {
    val byteArrayInputStream = ByteArrayInputStream(this)
    ObjectInputStream(byteArrayInputStream).use { return it.readObject() as T }
}
