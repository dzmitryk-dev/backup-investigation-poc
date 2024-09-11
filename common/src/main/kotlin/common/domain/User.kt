package common.domain

import java.io.Serializable

data class User(
    val name: String,
    val surname: String,
    val age: Int,
    val token: String
) : Serializable {
    companion object {
        val EMPTY = User("", "", 0, "")
    }
}
