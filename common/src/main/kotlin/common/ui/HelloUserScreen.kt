package common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.domain.User

@Composable
fun HelloUserScreen(user: User) {
    Column(modifier = Modifier) {
        Text(text = "Hello ${user.name} ${user.surname}!", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "You are ${user.age} years old.", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Your token is ${user.token}.", fontSize = 24.sp)
    }
}

@Preview
@Composable
private fun PreviewHelloUserScreen() {
    val user = User("John", "Doe", 30, "token")
    HelloUserScreen(user)
}
