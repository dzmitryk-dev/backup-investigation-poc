package common.domain

data class User(
    val name: String,
    val surname: String,
    val age: Int,
    val token: String
) {
    companion object {
        val EMPTY = User("", "", 0, "")
    }
}
